package com.tomaslm.urlshortener.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.tomaslm.urlshortener.url.ShortenerUrlService;

public class PeriodicCleanOldRegistersScheduler {

	@Autowired
	private ShortenerUrlService shortenerUrlService;

	/**
	 * Runs every day, to clean old registers
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void periodicClean() {
		shortenerUrlService.deleteOldMappings();
	}
}
