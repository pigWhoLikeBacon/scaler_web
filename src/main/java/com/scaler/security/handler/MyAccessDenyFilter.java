package com.scaler.security.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAccessDenyFilter implements AccessDeniedHandler {
	
	@Autowired
    private ObjectMapper mapper;

	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AccessDeniedException e) throws IOException, ServletException {
//		RetResult retResult = new RetResult(RetCode.NODEFINED.getCode(), "抱歉，您没有访问该接口的权限");
		httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		httpServletResponse.setContentType("application/json;charset=utf-8");
		httpServletResponse.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		json.put("error", "Access denied!");
		httpServletResponse.getWriter().write(mapper.writeValueAsString(json));
	}
}