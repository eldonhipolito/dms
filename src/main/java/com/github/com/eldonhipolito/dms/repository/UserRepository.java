package com.github.com.eldonhipolito.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.com.eldonhipolito.dms.core.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findUserByUsername(String username);

	public User findUserByUsernameAndPasswordHash(String username, String passwordHash);

}
