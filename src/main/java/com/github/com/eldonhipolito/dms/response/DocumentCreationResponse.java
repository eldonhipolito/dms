package com.github.com.eldonhipolito.dms.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DocumentCreationResponse extends GenericResponse {

	private String hash;

	public DocumentCreationResponse(String hash, boolean success, String result) {
		super(success, result);
		this.hash = hash;
	}

}
