package com.github.com.eldonhipolito.dms.core;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_document")
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String documentHash;

	private String title;

	private String description;

	private String filePath;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User documentOwner;

	private int numberSignatoriesRequired;

	@OneToMany(mappedBy = "document")
	private List<DocumentSignature> documentSignatures;

	public Document(long id, String documentHash, String title, String description, String filePath, User documentOwner,
			int numberSignatoriesRequired) {
		this.id = id;
		this.documentHash = documentHash;
		this.title = title;
		this.description = description;
		this.filePath = filePath;
		this.documentOwner = documentOwner;
	}

}
