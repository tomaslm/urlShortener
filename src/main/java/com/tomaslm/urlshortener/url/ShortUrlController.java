package com.tomaslm.urlshortener.url;

import org.hibernate.validator.constraints.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("shortUrl")
public class ShortUrlController {
	

    Logger logger = LoggerFactory.getLogger(ShortUrlController.class);

	@Autowired
	private ShortenerUrlService shortenerUrlService;

	@PostMapping("create")
	public String createShortUrl(@URL String realUrl) {
		ShortUrlMapping shortUrlMapping = shortenerUrlService.createOrUseExisting(realUrl);
		return shortUrlMapping.getShortenedPath();
	}

	// @DeleteMapping Delete old ones? Delete least referenced ones?
	// public void createShortUrl(@RequestBody List<ShortUrlMapping>
	// shortUrlMapping) {
	// shortenerUrlService.createOrUpdate();
	// }
}
