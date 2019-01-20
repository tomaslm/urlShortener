package com.tomaslm.urlshortener.statistics;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import com.tomaslm.urlshortener.url.ShortUrlMapping;

@Entity
public class ShortenedPathUpdate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String requestPath;
	private String redirectedPath;

	@Enumerated(EnumType.STRING)
	private UpdateShortenedPathType updateType;

	@CreationTimestamp
	private ZonedDateTime date;

	public ShortenedPathUpdate(ShortUrlMapping shortUrlMapping, UpdateShortenedPathType shortenedPathUpdate) {
		super();
		this.requestPath = shortUrlMapping.getShortenedPath();
		this.redirectedPath = shortUrlMapping.getRealUrl();
		this.updateType = shortenedPathUpdate;
	}

	public ShortenedPathUpdate() {

	}

	public String getRedirectedPath() {
		return redirectedPath;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public UpdateShortenedPathType getUpdateType() {
		return updateType;
	}
}
