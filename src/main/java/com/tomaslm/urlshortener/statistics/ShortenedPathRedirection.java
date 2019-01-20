package com.tomaslm.urlshortener.statistics;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import com.tomaslm.urlshortener.url.ShortUrlMapping;

@Entity
public class ShortenedPathRedirection {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String requestPath;
	private String redirectedPath;

	@CreationTimestamp
	private ZonedDateTime date;

	public ShortenedPathRedirection(ShortUrlMapping shortUrlMapping) {
		super();
		this.requestPath = shortUrlMapping.getShortenedPath();
		this.redirectedPath = shortUrlMapping.getRealUrl();
	}

	public ShortenedPathRedirection() {

	}

	public ZonedDateTime getDate() {
		return date;
	}

	public String getRedirectedPath() {
		return redirectedPath;
	}

	public String getRequestPath() {
		return requestPath;
	}

}
