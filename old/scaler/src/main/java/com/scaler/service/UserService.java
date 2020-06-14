package com.scaler.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.scaler.entity.Day;
import com.scaler.entity.DayPlan;
import com.scaler.entity.Event;
import com.scaler.entity.User;
import com.scaler.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public User findById(Integer id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User findByName(String name) {
		return userRepository.findByName(name);
	}
	
	public JSONObject save(User user) {
		JSONObject json = new JSONObject();
		
		if (user.getName().length() > 30) {
			json.put("error", "user.name beyond 30!");
			return json;
		} else if (user.getPassword().length() > 30) {
			json.put("error", "user.password beyond 30!");
			return json;
		}
		
		for (Day day : user.getDays()) {
			if (day.getLog().length() > 1000) {
				json.put("error", "user.day.log beyond 1000!");
				return json;
			}
			for(Event event : day.getEvents()) {
				if (event.getContent().length() > 1000) {
					json.put("error", "user.day.event.content beyond 1000!");
					return json;
				}
			}
		}
		
		User theUser = findByName(user.getName());
		if (theUser != null) {
			user.setId(theUser.getId());
			user.setLocked(theUser.isLocked());
			user.setFailNumber(theUser.getFailNumber());
			user.setReleaseTime(theUser.getReleaseTime());
		} else {
			user.setLocked(false);
			user.setFailNumber(0);
			user.setReleaseTime(null);
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		theUser = userRepository.save(user);
		
		json.put("error", null);
		json.put("id", theUser.getId());
		json.put("username", theUser.getName());
		return json;
	}
	
	@Transactional
	public User failNumberAdd(String name) {
		userRepository.failNumberAdd(name);
		return findByName(name);
	}
	
	@Transactional
	public User ResetFailNumber(String name) {
		userRepository.ResetFailNumber(name);
		return findByName(name);
	}
	
	@Transactional
	public void lock(Date releaseTime, String name) {
		userRepository.lock(releaseTime, name);
	}
	
	@Transactional
	public void unLock(String name) {
		userRepository.unLock(name);
	}
}
