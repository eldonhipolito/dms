package com.github.com.eldonhipolito.dms.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class CreateDocumentAccessRequest {

	private final int documentId;

	private String password;

	private String documentOwner;
	private String viewer;

}
