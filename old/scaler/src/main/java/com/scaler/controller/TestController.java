package com.scaler.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.scaler.entity.User;
import com.scaler.service.UserService;

@Controller
public class TestController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/auth")
	@ResponseBody
	public Object auth(Authentication authentication) {
		return authentication;
	}
	
	@GetMapping("/user")
	@ResponseBody
	public Object user(Authentication authentication) {
		User theUser = authentication == null ? null : userService.findByName(authentication.getName());
		return theUser;
	}
	
//	@RequestMapping("/receive")
//	@ResponseBody
//	public Object receive(Authentication authentication, @ModelAttribute JSONObject json) {
//		System.out.println(json);
//		
//		return json;
//	}
}
