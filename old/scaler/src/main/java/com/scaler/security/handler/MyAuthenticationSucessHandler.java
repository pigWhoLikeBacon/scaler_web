package com.scaler.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationSucessHandler implements AuthenticationSuccessHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
    private ObjectMapper mapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=utf-8");
        
        JSONObject json = new JSONObject();
		json.put("success", "LoginSuccess");
        response.getWriter().write(mapper.writeValueAsString(json));
		
//		SavedRequest savedRequest = requestCache.getRequest(request, response);
//		redirectStrategy.sendRedirect(request, response, savedRequest == null ? "index.html" : savedRequest.getRedirectUrl());
		
		// redirectStrategy.sendRedirect(request, response, "/index");
	}
}
