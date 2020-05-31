package com.github.com.eldonhipolito.dms.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoleAssignmentRequest {

	private Long[] userIds;
	
	private Long[] roleIds;
	
}
