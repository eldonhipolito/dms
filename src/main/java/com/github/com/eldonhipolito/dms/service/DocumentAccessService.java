package com.github.com.eldonhipolito.dms.service;

import com.github.com.eldonhipolito.dms.request.CreateDocumentAccessRequest;

public interface DocumentAccessService {

	String grantAccess(CreateDocumentAccessRequest request);
}
