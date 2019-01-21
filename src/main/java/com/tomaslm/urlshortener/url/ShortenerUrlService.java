package com.tomaslm.urlshortener.url;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tomaslm.urlshortener.statistics.StatisticsService;

@Service
public class ShortenerUrlService {
	Logger logger = LoggerFactory.getLogger(ShortenerUrlService.class);

	@Autowired
	private ShortUrlMappingRepository shortUrlMappingRepository;

	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	private RandomStringGeneratorService randomStringGeneratorService;

	@Value("${shortenedpath.max_random_unique_string_generation_attempts}")
	private int maxRandomUniqueStringGenerationAttemps;

	@Value("${shortenedpath.days_to_expire_mapping}")
	private int daysToExpireMapping;

	public ShortUrlMapping createOrUseExisting(String realUrl) {

		Optional<ShortUrlMapping> existingMappingWithUrl = shortUrlMappingRepository.findByRealUrl(realUrl);
		if (existingMappingWithUrl.isPresent()) {
			logger.info("No need creation for {}, there is already a exiting mapping", realUrl);
			return existingMappingWithUrl.get();
		}

		ShortUrlMapping shortUrlMapping = new ShortUrlMapping(realUrl, generateRandomUniqueString());
		shortUrlMapping = shortUrlMappingRepository.save(shortUrlMapping);
		statisticsService.createMapping(shortUrlMapping);

		logger.info("Mapping created for real url ({}), resulting in shortened path ({})", realUrl,
				shortUrlMapping.getShortenedPath());

		return shortUrlMapping;
	}

	private void updateLastUsedAt(ShortUrlMapping shortUrlMapping) {
		statisticsService.updateLastUsedAt(shortUrlMapping);
		shortUrlMappingRepository.updateLastUsedAtToNow(shortUrlMapping.getId());
		logger.info("Last used at updated to now at mapping ({}) to ({})", shortUrlMapping.getShortenedPath(),
				shortUrlMapping.getRealUrl());
	}

	private String generateRandomUniqueString() {
		String randomString;
		int attempt = 0;
		do {
			if (attempt > maxRandomUniqueStringGenerationAttemps) {
				String errorMessage = String.format("Unique string generation attempts exceded maximum of %d",
						maxRandomUniqueStringGenerationAttemps);
				logger.error(errorMessage);
				throw new IllegalStateException(errorMessage);
			}
			randomString = randomStringGeneratorService.generateRandomString();
			attempt++;
			logger.debug("Random string generated ({}), attempt ({})", randomString, attempt);
		} while (shortUrlMappingRepository.findByShortenedPath(randomString).isPresent());

		return randomString;
	}

	public Optional<String> findRealUrl(String shortenedPath) {
		Optional<ShortUrlMapping> shortUrlMapping = shortUrlMappingRepository.findByShortenedPath(shortenedPath);
		if (shortUrlMapping.isPresent()) {
			ShortUrlMapping mapping = shortUrlMapping.get();
			logger.info("Found real url using shortened path ({}), realUrl ({})", shortenedPath, mapping.getRealUrl());
			updateLastUsedAt(mapping);
			statisticsService.saveRedirection(mapping);
		} else {
			logger.warn("Couldnt find real url using shortened path ({})", shortenedPath);
		}

		return shortUrlMapping.map(ShortUrlMapping::getRealUrl);
	}

	public void deleteOldMappings() {
		ZonedDateTime dateLimit = ZonedDateTime.now().minusDays(daysToExpireMapping);

		long countBefore = statisticsService.getShortUrlMappingRepositoryCount();
		shortUrlMappingRepository.deleteByLastUsedAtBefore(dateLimit);
		long countAfter = statisticsService.getShortUrlMappingRepositoryCount();
		logger.info("Deleted all mappings that are before {}, {} registers were deleted", dateLimit,
				countBefore - countAfter);

	}

	public boolean deleteMappingForced(String realUrl) {
		Optional<ShortUrlMapping> shortUrlMapping = shortUrlMappingRepository.findByRealUrl(realUrl);

		if (shortUrlMapping.isPresent()) {
			ShortUrlMapping mapping = shortUrlMapping.get();
			logger.info("Force deleting mapping ({}) to ({})", mapping.getShortenedPath(), mapping.getRealUrl());
			shortUrlMappingRepository.delete(mapping);

			statisticsService.saveForcedDeletedMapping(mapping);
		}
		
		return shortUrlMapping.isPresent();
	}

}
