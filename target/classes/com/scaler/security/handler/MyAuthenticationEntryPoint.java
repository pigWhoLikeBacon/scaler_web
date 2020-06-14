package com.scaler.security.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Autowired
    private ObjectMapper mapper;
	
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
    	
    	response.setStatus(HttpStatus.UNAUTHORIZED.value());
    	response.setContentType("application/json;charset=utf-8");
    	response.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		json.put("error", "Not logged in!");
		response.getWriter().write(mapper.writeValueAsString(json));
		
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
    }

}