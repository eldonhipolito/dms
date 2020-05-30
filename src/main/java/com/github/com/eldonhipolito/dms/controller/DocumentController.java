package com.github.com.eldonhipolito.dms.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.com.eldonhipolito.dms.exception.UncheckedException;
import com.github.com.eldonhipolito.dms.request.CreateDocumentRequest;
import com.github.com.eldonhipolito.dms.response.DocumentCreationResponse;
import com.github.com.eldonhipolito.dms.service.DocumentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("documents")
public class DocumentController {

	private final DocumentService documentService;

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

		return new ResponseEntity<DocumentCreationResponse>(response, HttpStatus.OK);
	}

}
