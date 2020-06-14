package com.scaler.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
//    	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
    	
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=utf-8");
        
        JSONObject json = new JSONObject();
		json.put("error", exception.getMessage());
		
		if (exception instanceof BadCredentialsException) {
			
			System.out.println(request.toString());
		}
        
        response.getWriter().write(mapper.writeValueAsString(json));
        
        System.out.println(exception.toString());
    }
}
