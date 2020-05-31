package com.github.com.eldonhipolito.dms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.com.eldonhipolito.dms.request.CreateDocumentAccessRequest;
import com.github.com.eldonhipolito.dms.request.CreateDocumentRequest;
import com.github.com.eldonhipolito.dms.request.CreateDocumentSignatureRequest;
import com.github.com.eldonhipolito.dms.response.CreateDocumentAccessResponse;
import com.github.com.eldonhipolito.dms.response.DocumentCreationResponse;
import com.github.com.eldonhipolito.dms.response.GenericResponse;
import com.github.com.eldonhipolito.dms.service.DocumentAccessService;
import com.github.com.eldonhipolito.dms.service.DocumentService;
import com.github.com.eldonhipolito.dms.service.DocumentSignatureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("documents")
public class DocumentController {

	private final DocumentService documentService;

	private final DocumentSignatureService documentSignatureService;

	private final DocumentAccessService documentAccessService;

	@PostMapping("/create")
	public ResponseEntity<DocumentCreationResponse> createDocument(
			@RequestBody CreateDocumentRequest createDocumentRequest) {
		log.trace("[DOCUMENT] - createDocument({})", createDocumentRequest);

		DocumentCreationResponse response;
		try {
			String hash = this.documentService.createDocument(createDocumentRequest);
			response = new DocumentCreationResponse(hash, true, "Document created");
		} catch (Exception e) {
			log.error("Error creating document :", e);
			response = new DocumentCreationResponse("", false, "Error encountered while creating document");

		}

		return new ResponseEntity<DocumentCreationResponse>(response, HttpStatus.CREATED);
	}

	@PostMapping("/documents/{documentId}/signature")
	public ResponseEntity<GenericResponse> createDocumentSignature(
			@RequestBody CreateDocumentSignatureRequest createDocumentSignatureRequest) {
		log.trace("DOCUMENT] - createDocumentSignatureRequest({})", createDocumentSignatureRequest);
		GenericResponse response;

		try {
			int result = this.documentSignatureService.createDocumentSignature(createDocumentSignatureRequest);
			response = new GenericResponse(true, "Successfully created document signature");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response = new GenericResponse(false, "Error creating document signature");
		}

		return new ResponseEntity<GenericResponse>(response, HttpStatus.CREATED);
	}

	@PostMapping("/documents/{documentId}/grantaccess")
	public ResponseEntity<CreateDocumentAccessResponse> grantAccess(@RequestBody CreateDocumentAccessRequest request) {
		log.trace("[DOCUMENT] - grantAccess({})", request.getViewer());

		CreateDocumentAccessResponse response;
		try {

			String code = this.documentAccessService.grantAccess(request);

			response = new CreateDocumentAccessResponse(code, true, "Granted access");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response = new CreateDocumentAccessResponse("", false, "Error granting access to document");
		}

		return new ResponseEntity<CreateDocumentAccessResponse>(response, HttpStatus.OK);

	}

}
