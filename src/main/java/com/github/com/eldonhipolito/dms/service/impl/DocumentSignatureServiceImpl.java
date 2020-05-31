package com.github.com.eldonhipolito.dms.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.com.eldonhipolito.dms.core.Document;
import com.github.com.eldonhipolito.dms.core.DocumentSignature;
import com.github.com.eldonhipolito.dms.core.User;
import com.github.com.eldonhipolito.dms.exception.UncheckedException;
import com.github.com.eldonhipolito.dms.repository.DocumentRepository;
import com.github.com.eldonhipolito.dms.repository.DocumentSignatureRepository;
import com.github.com.eldonhipolito.dms.repository.UserRepository;
import com.github.com.eldonhipolito.dms.request.CreateDocumentSignatureRequest;
import com.github.com.eldonhipolito.dms.service.DocumentSignatureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentSignatureServiceImpl implements DocumentSignatureService {

	private final DocumentSignatureRepository documentSignatureRepository;

	private final DocumentRepository documentRepository;

	private final UserRepository userRepository;

	@Override
	public String signDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createDocumentSignature(CreateDocumentSignatureRequest createDocumentSignatureRequest) {
		log.info("[DOCUMENT] -  createDocumentSignature({})", createDocumentSignatureRequest);
		Optional<Document> doc = this.documentRepository
				.findById((long) createDocumentSignatureRequest.getDocumentId());

		if (doc.isEmpty()) {
			throw new UncheckedException("Document not found");
		}

		User user = this.userRepository.findUserByUsername(createDocumentSignatureRequest.getUsername());

		if (user == null) {
			throw new UncheckedException("User not found");
		}

		DocumentSignature dc = new DocumentSignature(0L, doc.get(), user, doc.get().getDocumentHash(), "");

		dc = documentSignatureRepository.save(dc);
		

		return dc.getId().intValue();
	}

}
