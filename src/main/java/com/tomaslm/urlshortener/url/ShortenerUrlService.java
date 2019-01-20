package com.tomaslm.urlshortener.url;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortenerUrlService {
	Logger logger = LoggerFactory.getLogger(ShortUrlController.class);

	@Autowired
	private ShortUrlMappingRepository shortUrlMappingRepository;

	public ShortUrlMapping createOrUseExisting(String realUrl) {

		Optional<ShortUrlMapping> existingMappingWithUrl = shortUrlMappingRepository.findByRealUrl(realUrl);
		if (existingMappingWithUrl.isPresent()) {
			logger.info("No need creation for {}, there is already a exiting mapping");

			return existingMappingWithUrl.get();
		}

		ShortUrlMapping shortUrlMapping = new ShortUrlMapping(realUrl);
		shortUrlMapping = shortUrlMappingRepository.save(shortUrlMapping);

		logger.info("Mapping created for real url {}, resulting in shortened path ({})");

		return shortUrlMapping;
	}

	public Optional<String> findRealUrl(String shortenedPath) {
		Optional<ShortUrlMapping> shortUrlMapping = shortUrlMappingRepository.findByShortenedPath(shortenedPath);
		if (shortUrlMapping.isPresent()) {
			logger.info("Found real url using shortened path ({}), realUrl ({})", shortenedPath,
					shortUrlMapping.get().getRealUrl());
		} else {
			logger.warn("Couldnt find real url using shortened path ({})", shortenedPath);
		}

		return shortUrlMapping.map(ShortUrlMapping::getRealUrl);
	}

}
