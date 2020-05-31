package com.github.com.eldonhipolito.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.com.eldonhipolito.dms.core.RoleAssignment;

public interface RoleAssignmentRepository extends JpaRepository<RoleAssignment, Long>{
	
	public RoleAssignment findByUserIdAndRoleId(Long userId, Long roleId);
	
}
