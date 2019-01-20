package com.tomaslm.urlshortener.url;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.URL;

@Entity
public class ShortUrlMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String realUrl;

	@Column(unique = true)
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

	public ShortUrlMapping(String realUrl, String shortenedPath) {
		this.realUrl = realUrl;
		this.shortenedPath = shortenedPath;
	}

	public ShortUrlMapping() {

	}
}
