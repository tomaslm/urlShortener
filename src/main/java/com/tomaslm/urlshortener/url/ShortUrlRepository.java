package com.tomaslm.urlshortener.url;

import org.springframework.data.repository.CrudRepository;

public interface ShortUrlRepository extends CrudRepository<ShortUrlMapping, Long>{

}
