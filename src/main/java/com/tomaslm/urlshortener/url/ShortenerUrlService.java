package com.tomaslm.urlshortener.url;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ShortenerUrlService {
	Logger logger = LoggerFactory.getLogger(ShortenerUrlService.class);

	@Autowired
	private ShortUrlMappingRepository shortUrlMappingRepository;

	@Autowired
	private RandomStringGeneratorService randomStringGeneratorService;

	@Value("${shortenedpath.max_random_unique_string_generation_attempts}")
	private int maxRandomUniqueStringGenerationAttemps;

	public ShortUrlMapping createOrUseExisting(String realUrl) {

		Optional<ShortUrlMapping> existingMappingWithUrl = shortUrlMappingRepository.findByRealUrl(realUrl);
		if (existingMappingWithUrl.isPresent()) {
			logger.info("No need creation for {}, there is already a exiting mapping", realUrl);

			return existingMappingWithUrl.get();
		}

		ShortUrlMapping shortUrlMapping = new ShortUrlMapping(realUrl, generateRandomUniqueString());
		shortUrlMapping = shortUrlMappingRepository.save(shortUrlMapping);

		logger.info("Mapping created for real url ({}), resulting in shortened path ({})", realUrl, shortUrlMapping.getShortenedPath());

		return shortUrlMapping;
	}

	private String generateRandomUniqueString() {
		String randomString;
		int attempt = 0;
		do {
			if (attempt > maxRandomUniqueStringGenerationAttemps) {
				String errorMessage = String.format("Unique string generation attempts exceded maximum of {}",
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
			logger.info("Found real url using shortened path ({}), realUrl ({})", shortenedPath,
					shortUrlMapping.get().getRealUrl());
		} else {
			logger.warn("Couldnt find real url using shortened path ({})", shortenedPath);
		}

		return shortUrlMapping.map(ShortUrlMapping::getRealUrl);
	}

}
