package com.scaler.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.scaler.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByName(String name);
	
	@Modifying
	@Query("UPDATE FROM User user SET user.failNumber = (user.failNumber + 1) WHERE user.name = ?1")
	void failNumberAdd(String name);
	
	@Modifying
	@Query("UPDATE FROM User user SET user.failNumber = 0 WHERE user.name = ?1")
	void ResetFailNumber(String name);
	
	@Modifying
	@Query("UPDATE FROM User user SET user.isLocked = 1, user.releaseTime = ?1 WHERE user.name = ?2")
	void lock(Date releaseTime, String name);
	
	@Modifying
	@Query("UPDATE FROM User user SET user.isLocked = 0, user.releaseTime = NULL, user.failNumber = 0 WHERE user.name = ?1")
	void unLock(String name);
}
