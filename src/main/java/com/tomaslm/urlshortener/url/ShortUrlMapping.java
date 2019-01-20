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
	
//	private ZonedDateTime expirationDate;	
	
}
