package com.scaler.security.browser;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.scaler.entity.User;
import com.scaler.service.UserService;

@Component
public class LoginAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	private UserService userService;

	@Value("${myset.MaxFailNumber}")
	private int MaxFailNumber = 3;

	@Value("${myset.lockSecond}")
	private int lockSecond = 3600;

	public LoginAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		super();
		// 这个地方一定要对userDetailsService赋值，不然userDetailsService是null (这个坑有点深)
		setUserDetailsService(userDetailsService);
		setPasswordEncoder(passwordEncoder);
		setHideUserNotFoundExceptions(false);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		try {
			Authentication auth = super.authenticate(authentication);
			// if reach here, means login success, else exception will be thrown
			// reset the user_attempts
			userService.ResetFailNumber(authentication.getName());
			return auth;
		} catch (UsernameNotFoundException e) {
			throw e;
		} catch (BadCredentialsException e) {
			String error = "Wrong password!";
			User user = userService.failNumberAdd(authentication.getName());
			
			if (user.getFailNumber() >= MaxFailNumber) {
				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				calendar.add(calendar.SECOND, lockSecond);
				date = calendar.getTime();
				
				userService.lock(date, authentication.getName());
				
				throw new LockedException(error + " Run out of chances. The account is locked until " + date.toString() + ".");
			} else {
				throw new BadCredentialsException(error + " You have " + (MaxFailNumber - user.getFailNumber()) + " more chances to try.");
			}

		} catch (LockedException e) {
			User user = userService.findByName(authentication.getName());
			Date date = new Date();
			
			if (date.after(user.getReleaseTime())) {
				userService.unLock(authentication.getName());
				Authentication auth = this.authenticate(authentication);
				return auth;
			} else {
				throw new LockedException("The account is locked until " + user.getReleaseTime().toString() + ".");
			}
		}
	}
}
