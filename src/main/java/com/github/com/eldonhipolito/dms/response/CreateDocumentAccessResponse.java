package com.github.com.eldonhipolito.dms.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
@Getter
public class CreateDocumentAccessResponse extends GenericResponse {

	private String code;

	public CreateDocumentAccessResponse(String code, boolean success, String result) {
		super(success, result);
		this.code = code;
	}

}
