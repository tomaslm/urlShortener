package com.tomaslm.urlshortener.url;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ShortUrlMappingRepository extends CrudRepository<ShortUrlMapping, Long> {

	Optional<ShortUrlMapping> findByShortenedPath(String shortenedPath);

	Optional<ShortUrlMapping> findByRealUrl(String realUrl);

	@Transactional
	@Modifying
	@Query("UPDATE ShortUrlMapping s SET s.lastUsedAt = now() WHERE id = :id")
	void updateLastUsedAtToNow(@Param("id") Long id);

	boolean deleteByLastUsedAtBefore(ZonedDateTime date);
}
