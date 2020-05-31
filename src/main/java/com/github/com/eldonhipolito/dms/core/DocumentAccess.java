package com.github.com.eldonhipolito.dms.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "tbl_documentaccess")
public class DocumentAccess {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User viewer;

	@ManyToOne
	@JoinColumn(name = "document_id", nullable = false)
	private Document document;
	private String encryptedKey;
	private int version;
	private String tempCodeHash;

}
