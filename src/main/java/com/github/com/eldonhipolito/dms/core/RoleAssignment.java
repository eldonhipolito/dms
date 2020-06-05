package com.github.com.eldonhipolito.dms.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_roleassignment")
@ToString
@IdClass(RoleAssignmentPK.class)
public class RoleAssignment {

	@Id
	@Column(name = "user_id")
	private Long userId;
	
	@Id
	@Column(name = "role_id")
	private Long roleId;
	
}
