package com.github.com.eldonhipolito.dms.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.github.com.eldonhipolito.dms.request.CreateUserRequest;
import com.github.com.eldonhipolito.dms.response.AuthenticationResponse;

public interface UserService extends UserDetailsService {

	public AuthenticationResponse authenticate(String username, String password);

	public int createUser(CreateUserRequest createUserRequest);

}
