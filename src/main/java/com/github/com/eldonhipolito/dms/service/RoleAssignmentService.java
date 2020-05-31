package com.github.com.eldonhipolito.dms.service;

import com.github.com.eldonhipolito.dms.core.RoleAssignment;
import com.github.com.eldonhipolito.dms.request.RoleAssignmentRequest;

public interface RoleAssignmentService {

	public void addRoleAssignment(RoleAssignmentRequest request);
	
	public RoleAssignment findByUserIdAndRoleId(Long userId, Long roleId);
	
}
