package com.tomaslm.urlshortener.statistics;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortenedPathRedirectionRepository extends CrudRepository<ShortenedPathRedirection, Long>{

	List<ShortenedPathRedirection> findByDateBetween(ZonedDateTime startDate, ZonedDateTime endDate);
}
