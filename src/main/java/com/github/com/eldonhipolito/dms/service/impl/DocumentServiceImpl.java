package com.github.com.eldonhipolito.dms.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.com.eldonhipolito.dms.core.Document;
import com.github.com.eldonhipolito.dms.core.User;
import com.github.com.eldonhipolito.dms.exception.UncheckedException;
import com.github.com.eldonhipolito.dms.repository.DocumentRepository;
import com.github.com.eldonhipolito.dms.repository.UserRepository;
import com.github.com.eldonhipolito.dms.request.CreateDocumentRequest;
import com.github.com.eldonhipolito.dms.service.DocumentService;
import com.github.com.eldonhipolito.dms.service.FileEncryptionService;
import com.github.com.eldonhipolito.dms.service.FileHashingStrategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

	private final DocumentRepository documentRepository;

	private final FileHashingStrategy sha256HashingStrategy;

	private final UserRepository userRepository;

	private final FileEncryptionService fileEncryptionService;

	@Autowired
	public DocumentServiceImpl(DocumentRepository documentRepository,
			@Qualifier("SHA256HashingStrategy") FileHashingStrategy sha256HashingStrategy,
			UserRepository userRepository,
			@Qualifier("FileEncryptionService") FileEncryptionService fileEncryptionService) {
		super();
		this.documentRepository = documentRepository;
		this.sha256HashingStrategy = sha256HashingStrategy;
		this.userRepository = userRepository;
		this.fileEncryptionService = fileEncryptionService;
	}

	@Override
	public String createDocument(CreateDocumentRequest createDocumentRequest) throws UncheckedException, IOException {
		log.info("Creating document - Title : {}", createDocumentRequest.getTitle());

		User user = this.userRepository.findUserByUsername(createDocumentRequest.getCreator());

		if (user == null) {
			throw new UncheckedException("Invalid document creator");
		}

		String hash = this.sha256HashingStrategy.hash(createDocumentRequest.getFile().getBytes());

		Document doc = new Document(0L, hash, createDocumentRequest.getTitle(), createDocumentRequest.getDescription(),
				"/" + hash, user, createDocumentRequest.getNumberSignatoriesRequired());

		doc = documentRepository.save(doc);

		if (doc.getId().intValue() < 1) {
			throw new UncheckedException("Unable to save document");
		}

		// TODO Encrypt and store file....

		return hash;
	}

	@Override
	public boolean isDocumentValid(MultipartFile file) throws UncheckedException {
		// TODO Auto-generated method stub
		return false;
	}

}
