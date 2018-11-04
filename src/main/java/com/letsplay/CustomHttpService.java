package com.letsplay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.http.HttpService;

public class CustomHttpService implements HttpService {
	
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
    private HttpServletResponse response;
	
	@Override
	public HttpServletRequest getCurrentRequest() {
		return request;
	}

	@Override
	public HttpServletResponse getCurrentResponse() {
		return response;
	}

}
