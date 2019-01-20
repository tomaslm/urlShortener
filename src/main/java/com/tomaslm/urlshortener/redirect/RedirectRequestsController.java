package com.tomaslm.urlshortener.redirect;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.tomaslm.urlshortener.url.ShortenerUrlService;

@RestController
public class RedirectRequestsController {
	
	private ShortenerUrlService shortenerUrlService;
	
	@PostMapping("/redirectPostToPost")
	public ModelAndView redirectPostToPost(HttpServletRequest request) {
		
	    request.setAttribute(
	      View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
	    return new ModelAndView("redirect:/redirectedPostToPost");
	}
	
	@PostMapping("/redirectedPostToPost")
	public ModelAndView redirectedPostToPost() {
	    return new ModelAndView("redirection");
	}
}
