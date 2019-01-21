package com.tomaslm.urlshortener.statistics;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaslm.urlshortener.url.ShortUrlMapping;
import com.tomaslm.urlshortener.url.ShortUrlMappingRepository;

@Service
public class StatisticsService {

	@Autowired
	private ShortenedPathUpdateRepository shortenedPathUpdateRepository;
	@Autowired
	private ShortenedPathRedirectionRepository shortenedPathRedirectionRepository;

	@Autowired
	private ShortUrlMappingRepository shortUrlMappingRepository;

	public List<ShortenedPathUpdate> findPathUpdatesByDateAndType(ZonedDateTime startDate, ZonedDateTime endDate,
			UpdateShortenedPathType updateShortenedPathType) {

		return shortenedPathUpdateRepository.findByDateBetweenAndUpdateType(startDate, endDate,
				updateShortenedPathType);
	}

	public List<ShortenedPathUpdate> findPathUpdatesByDate(ZonedDateTime startDate, ZonedDateTime endDate) {
		return shortenedPathUpdateRepository.findByDateBetween(startDate, endDate);
	}

	public List<ShortenedPathRedirection> findPathRedirectionsByDate(ZonedDateTime startDate, ZonedDateTime endDate) {
		return shortenedPathRedirectionRepository.findByDateBetween(startDate, endDate);
	}

	public long getShortUrlMappingRepositoryCount() {
		return shortUrlMappingRepository.count();
	}

	public void createMapping(ShortUrlMapping shortUrlMapping) {
		shortenedPathUpdateRepository.save(new ShortenedPathUpdate(shortUrlMapping, UpdateShortenedPathType.CREATE));
	}

	public void updateLastUsedAt(ShortUrlMapping shortUrlMapping) {
		shortenedPathUpdateRepository.save(new ShortenedPathUpdate(shortUrlMapping, UpdateShortenedPathType.UPDATE_LAST_USED_AT));
	}

	public void saveRedirection(ShortUrlMapping shortUrlMapping) {
		shortenedPathRedirectionRepository.save(new ShortenedPathRedirection(shortUrlMapping));
	}

	public void saveForcedDeletedMapping(ShortUrlMapping shortUrlMapping) {
		shortenedPathUpdateRepository
				.save(new ShortenedPathUpdate(shortUrlMapping, UpdateShortenedPathType.FORCED_DELETE));
	}

	public void saveSchedulerDeletedMappings(List<ShortUrlMapping> deletedList) {
		List<ShortenedPathUpdate> mappedList = deletedList.stream().map(
				shortUrlMapping -> new ShortenedPathUpdate(shortUrlMapping, UpdateShortenedPathType.SCHEDULER_DELETE))
				.collect(Collectors.toList());

		shortenedPathUpdateRepository.saveAll(mappedList);
	}

}
