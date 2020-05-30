package com.github.com.eldonhipolito.dms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.com.eldonhipolito.dms.request.CreateUserRequest;
import com.github.com.eldonhipolito.dms.response.GenericResponse;
import com.github.com.eldonhipolito.dms.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<GenericResponse> register(@RequestBody CreateUserRequest createUserRequest) {
		log.debug("[USER] - register({})", createUserRequest.getUsername());

		GenericResponse response;
		try {
			userService.createUser(createUserRequest);
			response = new GenericResponse(true, "Successfully created user.");
		} catch (Exception e) {
			log.error("Error while creating user : {}", e);
			response = new GenericResponse(false, "Error creating user. Please try again");
		}

		return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);

	}
}
