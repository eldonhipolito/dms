package com.github.com.eldonhipolito.dms.service.impl;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.com.eldonhipolito.dms.core.DocumentAccess;
import com.github.com.eldonhipolito.dms.core.User;
import com.github.com.eldonhipolito.dms.repository.DocumentAccessRepository;
import com.github.com.eldonhipolito.dms.repository.DocumentRepository;
import com.github.com.eldonhipolito.dms.repository.UserRepository;
import com.github.com.eldonhipolito.dms.request.CreateDocumentAccessRequest;
import com.github.com.eldonhipolito.dms.service.DocumentAccessService;
import com.github.com.eldonhipolito.dms.service.EncryptionService;
import com.github.com.eldonhipolito.dms.service.HashingStrategy;

class DocumentAccessServiceImplTest {

  private DocumentRepository documentRepository;

  private UserRepository userRepository;

  private DocumentAccessRepository documentAccessRepository;

  private Random randomizer;

  private HashingStrategy bCryptHashingStrategy;

  private HashingStrategy sha256HashingStrategy;

  private EncryptionService encryptionService;

  private DocumentAccessService documentAccessService;

  @BeforeEach
  void setUp() throws Exception {
    documentRepository = Mockito.mock(DocumentRepository.class);
    userRepository = Mockito.mock(UserRepository.class);
    documentAccessRepository = Mockito.mock(DocumentAccessRepository.class);
    randomizer = new Random();
    bCryptHashingStrategy = Mockito.mock(HashingStrategy.class);
    sha256HashingStrategy = Mockito.mock(HashingStrategy.class);
    encryptionService = Mockito.mock(EncryptionService.class);
    documentAccessService =
        new DocumentAccessServiceImpl(
            documentRepository,
            userRepository,
            documentAccessRepository,
            bCryptHashingStrategy,
            encryptionService,
            sha256HashingStrategy);
  }

  // @Test
  void testSuccess() {
    User owner = new User(1L, "eldon", "password", "eldon@gmail.com");
    Mockito.when(userRepository.findUserByUsername("eldon")).thenReturn(owner);
    DocumentAccess da = new DocumentAccess(0L, owner, null, "someKey", 1, "");
    Mockito.when(documentAccessRepository.save(Mockito.any())).thenReturn(da);
    CreateDocumentAccessRequest request =
        new CreateDocumentAccessRequest(1, "password", "eldon", "abby");
    documentAccessService.grantAccess(request);
  }
}
