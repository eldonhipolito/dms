package com.github.com.eldonhipolito.dms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.com.eldonhipolito.dms.response.GenericResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("users")
public class UserController {

	public UserController() {
		// TODO Auto-generated constructor stub
	}

	@PostMapping
	public ResponseEntity<GenericResponse> register() {

	}
}
