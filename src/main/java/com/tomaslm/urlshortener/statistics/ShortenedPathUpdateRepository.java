package com.tomaslm.urlshortener.statistics;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ShortenedPathUpdateRepository extends CrudRepository<ShortenedPathUpdate, Long>  {

	public List<ShortenedPathUpdate> findByDateBetween(ZonedDateTime startDate, ZonedDateTime endDate);

	public List<ShortenedPathUpdate> findByDateBetweenAndUpdateType(ZonedDateTime startDate, ZonedDateTime endDate, UpdateShortenedPathType updateType);
}
