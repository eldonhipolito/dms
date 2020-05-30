package com.github.com.eldonhipolito.dms.service.impl;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.com.eldonhipolito.dms.core.User;
import com.github.com.eldonhipolito.dms.repository.UserRepository;
import com.github.com.eldonhipolito.dms.request.CreateUserRequest;
import com.github.com.eldonhipolito.dms.service.HashingStrategy;
import com.github.com.eldonhipolito.dms.service.UserService;

public class UserServiceImplTest {

	private UserRepository userRepository;

	private UserService userService;

	private HashingStrategy hashingStrategy;

	@BeforeEach
	void initUseCase() {
		userRepository = Mockito.mock(UserRepository.class);
		hashingStrategy = Mockito.mock(HashingStrategy.class);
		userService = new UserServiceImpl(hashingStrategy, userRepository);
	}

	@Test
	public void testCreateUserSuccess() {

		User user = new User(1L, "eldon", "password", "eldon@gmail.com");
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

		int result = userService.createUser(new CreateUserRequest("eldon", "password", "eldon@gmail.com"));

		assertThat(result).isEqualTo(1);
	}

	@Test
	public void testCreateUserFail() {

		User user = new User(2L, "eldon", "password", "eldon@gmail.com");
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(null);

		assertThatThrownBy(() -> userService.createUser(new CreateUserRequest("eldon", "password", "eldon@gmail.com")))
				.isInstanceOf(NullPointerException.class);

	}

}
