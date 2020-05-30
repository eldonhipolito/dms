package com.github.com.eldonhipolito.dms.service;

import com.github.com.eldonhipolito.dms.request.CreateUserRequest;
import com.github.com.eldonhipolito.dms.response.AuthenticationResponse;

public interface UserService {

	public AuthenticationResponse authenticate(String username, String password);

	public void createUser(CreateUserRequest createUserRequest);

}
