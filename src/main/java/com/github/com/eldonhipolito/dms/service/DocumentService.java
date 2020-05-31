package com.github.com.eldonhipolito.dms.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.github.com.eldonhipolito.dms.exception.UncheckedException;
import com.github.com.eldonhipolito.dms.request.CreateDocumentRequest;
import com.github.com.eldonhipolito.dms.request.DocumentValidationRequest;

public interface DocumentService {

	String createDocument(CreateDocumentRequest createDocumentRequest) throws UncheckedException, IOException;

	boolean isDocumentValid(DocumentValidationRequest request) throws UncheckedException;

}
