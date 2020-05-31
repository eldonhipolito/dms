package com.github.com.eldonhipolito.dms.core;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class RoleAssignmentPK implements Serializable {
 
    private long userId;
    private long roleId;
 
}