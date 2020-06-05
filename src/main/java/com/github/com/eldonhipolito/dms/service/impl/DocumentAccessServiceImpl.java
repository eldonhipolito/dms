package com.github.com.eldonhipolito.dms.service.impl;

import java.util.Base64;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import com.github.com.eldonhipolito.dms.core.Document;
import com.github.com.eldonhipolito.dms.core.DocumentAccess;
import com.github.com.eldonhipolito.dms.core.User;
import com.github.com.eldonhipolito.dms.exception.UncheckedException;
import com.github.com.eldonhipolito.dms.repository.DocumentAccessRepository;
import com.github.com.eldonhipolito.dms.repository.DocumentRepository;
import com.github.com.eldonhipolito.dms.repository.UserRepository;
import com.github.com.eldonhipolito.dms.request.CreateDocumentAccessRequest;
import com.github.com.eldonhipolito.dms.service.DocumentAccessService;
import com.github.com.eldonhipolito.dms.service.EncryptionService;
import com.github.com.eldonhipolito.dms.service.HashingStrategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DocumentAccessServiceImpl implements DocumentAccessService {

  private final DocumentRepository documentRepository;

  private final UserRepository userRepository;

  private final DocumentAccessRepository documentAccessRepository;

  private final Random randomizer;

  private final HashingStrategy bCryptHashingStrategy;

  private final HashingStrategy sha256HashingStrategy;

  private final EncryptionService encryptionService;

  public DocumentAccessServiceImpl(
      DocumentRepository documentRepository,
      UserRepository userRepository,
      DocumentAccessRepository documentAccessRepository,
      @Qualifier("BCryptHashingStrategy") HashingStrategy bCryptHashingStrategy,
      @Qualifier("AESEncryptionService") EncryptionService encryptionService,
      @Qualifier("SHA256HashingStrategy") HashingStrategy sha256HashingStrategy) {
    super();
    this.documentRepository = documentRepository;
    this.userRepository = userRepository;
    this.documentAccessRepository = documentAccessRepository;
    this.randomizer = new Random();
    this.bCryptHashingStrategy = bCryptHashingStrategy;
    this.encryptionService = encryptionService;
    this.sha256HashingStrategy = sha256HashingStrategy;
  }

  @Override
  public String grantAccess(CreateDocumentAccessRequest request) {
    log.info("[DOCUMENT] - grantAccess({})", request.getViewer());

    String randomCode = String.format("%06d", randomizer.nextInt(1000000));

    Optional<Document> doc = documentRepository.findById((long) request.getDocumentId());

    if (doc.isEmpty()) {
      throw new UncheckedException("Document not found");
    }

    User viewer = this.userRepository.findUserByUsername(request.getViewer());

    if (viewer == null) {
      throw new UncheckedException("User viewer not found");
    }

    User owner = this.userRepository.findUserByUsername(request.getDocumentOwner());

    if (owner == null) {
      throw new UncheckedException("Document owner not found");
    }

    Optional<DocumentAccess> docAccess =
        this.documentAccessRepository.findByUserIdAndDocumentId(
            owner.getId().intValue(), request.getDocumentId());

    if (docAccess.isEmpty()) {
      throw new UncheckedException("Document access for owner not found");
    }

    String hash = this.bCryptHashingStrategy.hash(randomCode);

    try {
      // TODO fix this when things get better
      byte[] masterKey =
          this.encryptionService.decrypt(
              Base64.getDecoder().decode(docAccess.get().getEncryptedKey().getBytes()),
              Hex.decode(this.sha256HashingStrategy.hash(request.getPassword())));

      String tempEncryptedKey =
          new String(
              Base64.getEncoder()
                  .encode(
                      this.encryptionService.encrypt(
                          masterKey, Hex.decode(this.sha256HashingStrategy.hash(randomCode)))));

      DocumentAccess newDocAccess =
          new DocumentAccess(
              0L,
              viewer,
              doc.get(),
              tempEncryptedKey,
              1,
              this.bCryptHashingStrategy.hash(randomCode));

      newDocAccess = documentAccessRepository.save(newDocAccess);

      if (newDocAccess.getId() < 1) {
        throw new UncheckedException("Unable to save Document Access");
      }
      return randomCode;

    } catch (Exception e) {
      throw new UncheckedException(e);
    }
  }
}
