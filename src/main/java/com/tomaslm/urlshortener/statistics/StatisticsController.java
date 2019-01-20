package com.tomaslm.urlshortener.statistics;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("statistics")
public class StatisticsController {

	@Autowired
	private StatisticsService statisticsService;

	@GetMapping("redirection")
	public List<ShortenedPathRedirection> getRedirectionStatistics(
			@RequestParam("startDate") @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime endDate) {
		return statisticsService.findPathRedirectionsByDate(startDate, endDate);
	}

	@GetMapping("update")
	public List<ShortenedPathUpdate> getUpdateStatistics(
			@RequestParam("startDate") @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime endDate,
			@RequestParam(name = "updateType", required = false) UpdateShortenedPathType updateType) {
		if (Objects.nonNull(updateType)) {
			return statisticsService.findPathUpdatesByDateAndType(startDate, endDate, updateType);
		} else {
			return statisticsService.findPathUpdatesByDate(startDate, endDate);
		}
	}

	@GetMapping("count")
	public Map<String, Long> getShortenedCount() {
		Map<String, Long> response = new HashMap<>();
		response.put("count", statisticsService.getShortUrlMappingRepositoryCount());

		return response;
	}
}
