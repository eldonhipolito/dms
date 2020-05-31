package com.github.com.eldonhipolito.dms.service;

import com.github.com.eldonhipolito.dms.exception.UncheckedException;
import com.github.com.eldonhipolito.dms.request.CreateDocumentSignatureRequest;

public interface DocumentSignatureService {

	int createDocumentSignature(CreateDocumentSignatureRequest createDocumentSignatureRequest)
			throws UncheckedException;

	// TODO figure out method signature
	// Will be mocked for now
	String signDocument() throws UncheckedException;

}
