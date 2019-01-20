package com.tomaslm.urlshortener.url;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.URL;

@Entity
public class ShortUrlMapping {

	@Id
	private Long id;

	@URL
	private String realUrl;

	private String shortenedPath;

	@CreationTimestamp
	private ZonedDateTime createdAt;

	private ZonedDateTime lastUsedAt;

	// private ZonedDateTime expirationDate;

	public String getRealUrl() {
		return realUrl;
	}

	public String getShortenedPath() {
		return shortenedPath;
	}

	public ShortUrlMapping(String realUrl) {
		this.realUrl = realUrl;
	}

}
