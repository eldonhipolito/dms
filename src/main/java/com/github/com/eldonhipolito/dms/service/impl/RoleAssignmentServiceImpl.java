package com.github.com.eldonhipolito.dms.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.com.eldonhipolito.dms.core.RoleAssignment;
import com.github.com.eldonhipolito.dms.repository.RoleAssignmentRepository;
import com.github.com.eldonhipolito.dms.request.RoleAssignmentRequest;
import com.github.com.eldonhipolito.dms.service.RoleAssignmentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleAssignmentServiceImpl implements RoleAssignmentService {
	
	private final RoleAssignmentRepository roleAssignmentRepository;
	
	@Autowired
	public RoleAssignmentServiceImpl(RoleAssignmentRepository roleAssignmentRepository) {
		super();
		this.roleAssignmentRepository = roleAssignmentRepository;
	}

	@Override
	public void addRoleAssignment(RoleAssignmentRequest request) {
		
		ArrayList<RoleAssignment> assignments = new ArrayList<RoleAssignment>();
		
		for (Long userId : request.getUserIds()) {
			for (Long roleId: request.getRoleIds()) {
				assignments.add(new RoleAssignment(userId, roleId));
			}
		}
		
		this.roleAssignmentRepository.saveAll(assignments);
		
	}

	@Override
	public RoleAssignment findByUserIdAndRoleId(Long userId, Long roleId) {
		return this.roleAssignmentRepository.findByUserIdAndRoleId(userId, roleId);
	}

}
