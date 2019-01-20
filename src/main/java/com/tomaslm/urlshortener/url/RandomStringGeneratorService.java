package com.tomaslm.urlshortener.url;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RandomStringGeneratorService {

	@Value("${shortenedpath.length}")
	private int shortenedPathLength;

	public String generateRandomString() {
		return RandomStringUtils.randomAlphabetic(shortenedPathLength);
	}
}
