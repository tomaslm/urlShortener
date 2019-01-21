package com.tomaslm.urlshortener.redirect;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tomaslm.urlshortener.url.ShortenerUrlService;

@RestController
@RequestMapping("short")
public class RedirectRequestsController {
	Logger logger = LoggerFactory.getLogger(RedirectRequestsController.class);

	@Autowired
	private ShortenerUrlService shortenerUrlService;

	@GetMapping("/{shortenedPath}")
	@PostMapping("/{shortenedPath}")
	@PutMapping("/{shortenedPath}")
	@PatchMapping("/{shortenedPath}")
	@DeleteMapping("/{shortenedPath}")
	public void redirectRequest(HttpServletResponse httpServletResponse,
			@PathVariable("shortenedPath") String shortenedPath) {

		Optional<String> realUrl = shortenerUrlService.findRealUrl(shortenedPath);
		if (!realUrl.isPresent()) {
			logger.error("Coudnt find mapping for shortened path ({})", shortenedPath);

			httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
		} else {
			String url = realUrl.get();
			if (!url.toLowerCase().contains("http")) {
				url = "http://" + url;
			}
			logger.info("Mapping found for shortened path ({}), redirecting to ({})", shortenedPath, url);
			httpServletResponse.setHeader("Location", url);
		}

		httpServletResponse.setStatus(HttpStatus.PERMANENT_REDIRECT.value());

	}

}
