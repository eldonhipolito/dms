package com.github.com.eldonhipolito.dms.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.com.eldonhipolito.dms.config.ApplicationConfig;
import com.github.com.eldonhipolito.dms.core.Document;
import com.github.com.eldonhipolito.dms.core.DocumentAccess;
import com.github.com.eldonhipolito.dms.core.User;
import com.github.com.eldonhipolito.dms.exception.UncheckedException;
import com.github.com.eldonhipolito.dms.repository.DocumentAccessRepository;
import com.github.com.eldonhipolito.dms.repository.DocumentRepository;
import com.github.com.eldonhipolito.dms.repository.UserRepository;
import com.github.com.eldonhipolito.dms.request.CreateDocumentRequest;
import com.github.com.eldonhipolito.dms.request.DocumentValidationRequest;
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

	private final DocumentAccessRepository documentAccessRepository;

	private final ApplicationConfig applicationConfig;

	@Autowired
	public DocumentServiceImpl(DocumentRepository documentRepository,
			@Qualifier("SHA256HashingStrategy") FileHashingStrategy sha256HashingStrategy,
			UserRepository userRepository,
			@Qualifier("FileEncryptionService") FileEncryptionService fileEncryptionService,
			DocumentAccessRepository documentAccessRepository, ApplicationConfig applicationConfig) {
		super();
		this.documentRepository = documentRepository;
		this.sha256HashingStrategy = sha256HashingStrategy;
		this.userRepository = userRepository;
		this.fileEncryptionService = fileEncryptionService;
		this.documentAccessRepository = documentAccessRepository;
		this.applicationConfig = applicationConfig;
	}

	@Override
	public String createDocument(CreateDocumentRequest createDocumentRequest) throws UncheckedException, IOException {
		log.info("Creating document - Title : {}", createDocumentRequest.getTitle());

		User user = this.userRepository.findUserByUsername(createDocumentRequest.getCreator());

		if (user == null) {
			throw new UncheckedException("Invalid document creator");
		}

		String hash = this.sha256HashingStrategy.hash(createDocumentRequest.getFile().getBytes());

		String fileName = applicationConfig.getFileStoreLocation() + hash;
		Document doc = new Document(0L, hash, createDocumentRequest.getTitle(), createDocumentRequest.getDescription(),
				fileName, user, createDocumentRequest.getNumberSignatoriesRequired());

		doc = documentRepository.save(doc);

		if (doc.getId().intValue() < 1) {
			throw new UncheckedException("Unable to save document");
		}

		String encodedKey;
		try {
			encodedKey = fileEncryptionService.generateRandomKey();
			byte[] decodedKey = Base64.getDecoder().decode(encodedKey.getBytes());
			fileEncryptionService.encryptAndStore(fileName, createDocumentRequest.getFile().getBytes(), decodedKey);

			byte[] encryptedKey = fileEncryptionService.encrypt(decodedKey,
					Hex.decode(this.sha256HashingStrategy.hash(createDocumentRequest.getPassword().getBytes())));

			DocumentAccess docAccess = new DocumentAccess(0L, doc.getDocumentOwner(), doc,
					Base64.getEncoder().encodeToString(encryptedKey), 1, "");

			docAccess = documentAccessRepository.save(docAccess);

		} catch (Exception e) {
			throw new UncheckedException(e);
		}

		return hash;
	}

	@Override
	public boolean isDocumentValid(DocumentValidationRequest request) throws UncheckedException {
		log.info("[DOCUMENT] isDocumentValid({})", request.getDocumentId());

		Optional<Document> doc = this.documentRepository.findById((long) request.getDocumentId());

		if (doc.isEmpty()) {
			throw new UncheckedException("Document not found");
		}

		try {
			return sha256HashingStrategy.isEqualToRawFile(request.getFile().getBytes(), doc.get().getDocumentHash());
		} catch (IOException e) {
			throw new UncheckedException(e);
		}

	}

}
