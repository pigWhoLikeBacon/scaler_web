package com.scaler.security.browser;

import com.scaler.entity.User;
import com.scaler.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Configuration
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userService.findByName(username);
		if (user == null) {
			throw new UsernameNotFoundException("Username not found!");
		}

		return new org.springframework.security.core.userdetails.User(
				user.getName(), user.getPassword(),
				true, true , true, !user.isLocked(),
				AuthorityUtils.commaSeparatedStringToAuthorityList("user"));
	}
}
