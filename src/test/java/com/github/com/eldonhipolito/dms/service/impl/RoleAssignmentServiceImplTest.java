package com.github.com.eldonhipolito.dms.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.com.eldonhipolito.dms.core.Role;
import com.github.com.eldonhipolito.dms.core.RoleAssignment;
import com.github.com.eldonhipolito.dms.core.User;
import com.github.com.eldonhipolito.dms.repository.RoleAssignmentRepository;
import com.github.com.eldonhipolito.dms.request.RoleAssignmentRequest;
import com.github.com.eldonhipolito.dms.service.RoleAssignmentService;

public class RoleAssignmentServiceImplTest {

	private RoleAssignmentRepository roleAssignmentRepository;
	
	private RoleAssignmentService roleAssignmentService;
	
	@BeforeEach
	void initUseCase() {
		roleAssignmentRepository = Mockito.mock(RoleAssignmentRepository.class);
		roleAssignmentService = new RoleAssignmentServiceImpl(roleAssignmentRepository);
	}
	
	@Test
	public void addRoleAssignmentSuccess() {
		
		User testUser = new User(1L, "test", "secret", "test@mail.com");
		Role testRole = new Role(1L, "test role");
		RoleAssignment roleAssignment = new RoleAssignment(testUser.getId(), testRole.getId());
		
		Mockito.when(roleAssignmentRepository.save(Mockito.any(RoleAssignment.class)))
			.thenReturn(roleAssignment);
		Mockito.when(
			roleAssignmentRepository
			.findByUserIdAndRoleId(testUser.getId(), testRole.getId()))
			.thenReturn(roleAssignment);
		
		roleAssignmentService.addRoleAssignment(
				new RoleAssignmentRequest(
						new Long[] {testUser.getId()},
						new Long[] {testRole.getId()}
						)
				);
		
		assertThat(
				roleAssignmentService.findByUserIdAndRoleId(testUser.getId(), testRole.getId()))
				.isEqualTo(roleAssignment);
		
	}
	
}
