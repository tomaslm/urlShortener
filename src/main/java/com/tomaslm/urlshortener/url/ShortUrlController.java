package com.tomaslm.urlshortener.url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tomaslm.urlshortener.statistics.UrlObject;

@RestController("shortUrl")
public class ShortUrlController {
	Logger logger = LoggerFactory.getLogger(ShortUrlController.class);

	@Autowired
	private ShortenerUrlService shortenerUrlService;

	@PostMapping("create")
	public String createShortUrl(@RequestBody UrlObject realUrl) {
		ShortUrlMapping shortUrlMapping = shortenerUrlService.createOrUseExisting(realUrl.getUrl());
		return shortUrlMapping.getShortenedPath();
	}

	@DeleteMapping("delete")
	public void deleteMappingForced(@RequestBody UrlObject realUrl) {
		boolean deleted = shortenerUrlService.deleteMappingForced(realUrl.getUrl());
		if(!deleted) {
			throw new IllegalStateException("Mapping not found");
		}
	}

}
