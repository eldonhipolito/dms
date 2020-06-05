package com.github.com.eldonhipolito.dms.core;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "tbl_role")
@ToString
public class Role {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String name;
    
//    @ManyToMany
//    @JoinTable(
//        name = "roles_privileges", 
//        joinColumns = @JoinColumn(
//          name = "role_id", referencedColumnName = "id"), 
//        inverseJoinColumns = @JoinColumn(
//          name = "privilege_id", referencedColumnName = "id"))
//    private Collection<Privilege> privileges; 
    
}
