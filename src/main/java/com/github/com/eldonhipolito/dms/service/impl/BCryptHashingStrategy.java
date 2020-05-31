package com.github.com.eldonhipolito.dms.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.com.eldonhipolito.dms.service.HashingStrategy;

@Service(value = "BCryptHashingStrategy")
public class BCryptHashingStrategy implements HashingStrategy {

	private final PasswordEncoder bcryptPasswordEncoder;

	public BCryptHashingStrategy() {
		this.bcryptPasswordEncoder = new BCryptPasswordEncoder();
	}

	public BCryptHashingStrategy(PasswordEncoder bcryptPasswordEncoder) {
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}

	@Override
	public String hash(String msg) {
		return this.bcryptPasswordEncoder.encode(msg);

	}

	@Override
	public boolean isEqualToRawText(String raw, String hashed) {
		return this.bcryptPasswordEncoder.matches(raw, hashed);

	}

}
