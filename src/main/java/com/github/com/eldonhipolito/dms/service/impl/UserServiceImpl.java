package com.github.com.eldonhipolito.dms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.com.eldonhipolito.dms.core.User;
import com.github.com.eldonhipolito.dms.repository.UserRepository;
import com.github.com.eldonhipolito.dms.request.CreateUserRequest;
import com.github.com.eldonhipolito.dms.response.AuthenticationResponse;
import com.github.com.eldonhipolito.dms.service.HashingStrategy;
import com.github.com.eldonhipolito.dms.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private final HashingStrategy hashingStrategy;

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(HashingStrategy hashingStrategy, UserRepository userRepository) {
		this.hashingStrategy = hashingStrategy;
		this.userRepository = userRepository;
	}

	@Override
	public AuthenticationResponse authenticate(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createUser(CreateUserRequest createUserRequest) {
		log.info("[USER] - Creating user with username : {}", createUserRequest.getUsername());
		User user = new User(0L, createUserRequest.getUsername(),
				this.hashingStrategy.hash(createUserRequest.getPassword()), createUserRequest.getEmail());
		user = this.userRepository.save(user);
	}

}
