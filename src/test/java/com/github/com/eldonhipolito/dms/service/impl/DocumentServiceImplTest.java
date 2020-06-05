package com.github.com.eldonhipolito.dms.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import com.github.com.eldonhipolito.dms.config.ApplicationConfig;
import com.github.com.eldonhipolito.dms.core.Document;
import com.github.com.eldonhipolito.dms.core.DocumentAccess;
import com.github.com.eldonhipolito.dms.core.User;
import com.github.com.eldonhipolito.dms.exception.UncheckedException;
import com.github.com.eldonhipolito.dms.repository.DocumentAccessRepository;
import com.github.com.eldonhipolito.dms.repository.DocumentRepository;
import com.github.com.eldonhipolito.dms.repository.UserRepository;
import com.github.com.eldonhipolito.dms.request.CreateDocumentRequest;
import com.github.com.eldonhipolito.dms.service.DocumentService;
import com.github.com.eldonhipolito.dms.service.FileEncryptionService;
import com.github.com.eldonhipolito.dms.service.FileHashingStrategy;

class DocumentServiceImplTest {

  private DocumentRepository documentRepository;

  private DocumentService documentService;

  private UserRepository userRepository;

  private FileHashingStrategy hashingStrategy;

  private FileEncryptionService fileEncryptionService;

  private DocumentAccessRepository documentAccessRepository;

  private ApplicationConfig applicationConfig;

  @BeforeEach
  void setUp() throws Exception {
    documentRepository = Mockito.mock(DocumentRepository.class);
    userRepository = Mockito.mock(UserRepository.class);
    hashingStrategy = Mockito.mock(FileHashingStrategy.class);
    fileEncryptionService = Mockito.mock(FileEncryptionService.class);
    documentAccessRepository = Mockito.mock(DocumentAccessRepository.class);
    applicationConfig = Mockito.mock(ApplicationConfig.class);
    documentService =
        new DocumentServiceImpl(
            documentRepository,
            hashingStrategy,
            userRepository,
            fileEncryptionService,
            documentAccessRepository,
            applicationConfig);
  }

  @Test
  void testSuccess()
      throws UncheckedException, IOException, NoSuchAlgorithmException, InvalidKeyException,
          NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
          BadPaddingException {
    User owner = new User(1L, "eldon", "password", "eldon@gmail.com");
    Mockito.when(userRepository.findUserByUsername("eldon")).thenReturn(owner);
    Mockito.when(applicationConfig.getFileStoreLocation())
        .thenReturn("C:\\Users\\Admin\\Desktop\\storage\\");
    DocumentAccess da = new DocumentAccess(0L, owner, null, "someKey", 1, "");
    Mockito.when(documentAccessRepository.save(Mockito.any())).thenReturn(da);
    Mockito.when(fileEncryptionService.generateRandomKey()).thenReturn("someKey");
    Mockito.when(fileEncryptionService.encrypt(Mockito.any(), Mockito.any()))
        .thenReturn("someKey".getBytes());

    Mockito.when(hashingStrategy.hash(Mockito.anyString())).thenReturn("ABCDEF");
    Mockito.when(hashingStrategy.hash(Mockito.any(byte[].class))).thenReturn("ABCDEF");
    CreateDocumentRequest req =
        new CreateDocumentRequest(
            "Doc1",
            "First document",
            3,
            new MultipartFile() {

              @Override
              public void transferTo(File dest) throws IOException, IllegalStateException {
                // TODO Auto-generated method stub

              }

              @Override
              public boolean isEmpty() {
                // TODO Auto-generated method stub
                return false;
              }

              @Override
              public long getSize() {
                // TODO Auto-generated method stub
                return 0;
              }

              @Override
              public String getOriginalFilename() {
                // TODO Auto-generated method stub
                return "lovely.txt";
              }

              @Override
              public String getName() {
                // TODO Auto-generated method stub
                return "lovely.txt";
              }

              @Override
              public InputStream getInputStream() throws IOException {
                // TODO Auto-generated method stub
                return null;
              }

              @Override
              public String getContentType() {
                // TODO Auto-generated method stub
                return null;
              }

              @Override
              public byte[] getBytes() throws IOException {
                // TODO Auto-generated method stub
                return new byte[] {1, 2, 3, 4};
              }
            },
            "eldon",
            "password");
    Document doc =
        new Document(
            1L,
            "ABCDEF",
            "Doc1",
            "First document",
            applicationConfig.getFileStoreLocation() + "ABCDEF",
            owner,
            3);

    Mockito.when(documentRepository.save(Mockito.any())).thenReturn(doc);
    String result = documentService.createDocument(req);

    assertThat(result).isNotBlank();
  }
}
