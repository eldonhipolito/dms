package com.github.com.eldonhipolito.dms.core;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "tbl_user")
@ToString
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String username;

	private String passwordHash;

	private String email;

	@OneToMany(mappedBy = "user")
	private List<Document> ownedDocuments;

	@OneToMany(mappedBy = "user")
	private List<DocumentSignature> documentSignatures;

	public User(Long id, String username, String passwordHash, String email) {
		super();
		this.id = id;
		this.username = username;
		this.passwordHash = passwordHash;
		this.email = email;
	}

}
