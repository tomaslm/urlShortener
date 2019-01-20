package com.tomaslm.urlshortener.url;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ShortUrlMappingRepository extends CrudRepository<ShortUrlMapping, Long>{

	Optional<ShortUrlMapping> findByShortenedPath(String shortenedPath);
	
	Optional<ShortUrlMapping> findByRealUrl(String realUrl);
}
