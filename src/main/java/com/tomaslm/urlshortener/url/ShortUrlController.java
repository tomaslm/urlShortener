package com.tomaslm.urlshortener.url;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("shortUrl")
public class ShortUrlController {

	@PostMapping
	public void createShortUrl() {
		
	}
}
