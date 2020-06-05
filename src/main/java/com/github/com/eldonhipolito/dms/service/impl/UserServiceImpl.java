package com.github.com.eldonhipolito.dms.service.impl;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
  public UserServiceImpl(
      @Qualifier("BCryptHashingStrategy") HashingStrategy hashingStrategy,
      UserRepository userRepository) {
    this.hashingStrategy = hashingStrategy;
    this.userRepository = userRepository;
  }

  @Override
  public AuthenticationResponse authenticate(String username, String password) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int createUser(CreateUserRequest createUserRequest) {
    log.info("[USER] - Creating user with username : {}", createUserRequest.getUsername());
    User user =
        new User(
            0L,
            createUserRequest.getUsername(),
            this.hashingStrategy.hash(createUserRequest.getPassword()),
            createUserRequest.getEmail());
    user = this.userRepository.save(user);

    return user.getId().intValue();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User u = userRepository.findUserByUsername(username);
    org.springframework.security.core.userdetails.User user =
        new org.springframework.security.core.userdetails.User(
            username, u.getPasswordHash(), Collections.emptyList());
    return user;
  }
}
