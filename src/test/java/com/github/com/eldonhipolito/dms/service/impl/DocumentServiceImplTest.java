package com.github.com.eldonhipolito.dms.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import com.github.com.eldonhipolito.dms.exception.UncheckedException;
import com.github.com.eldonhipolito.dms.repository.DocumentRepository;
import com.github.com.eldonhipolito.dms.repository.UserRepository;
import com.github.com.eldonhipolito.dms.request.CreateDocumentRequest;
import com.github.com.eldonhipolito.dms.service.DocumentService;
import com.github.com.eldonhipolito.dms.service.FileHashingStrategy;

class DocumentServiceImplTest {

	private DocumentRepository documentRepository;

	private FileHashingStrategy hashingStrategy;

	private DocumentService documentService;

	private UserRepository userRepository;

	@BeforeEach
	void setUp() throws Exception {
		documentRepository = Mockito.mock(DocumentRepository.class);
		userRepository = Mockito.mock(UserRepository.class);
		hashingStrategy = Mockito.mock(FileHashingStrategy.class);
		documentService = new DocumentServiceImpl(documentRepository, hashingStrategy, userRepository);
	}

	@Test
	void testSuccess() throws UncheckedException, IOException {

		CreateDocumentRequest req = new CreateDocumentRequest("Doc1", "First document", 3, new MultipartFile() {

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
				return new byte[] { 1, 2, 3, 4 };
			}
		}, "eldon");
		String result = documentService.createDocument(req);
		assertThat(result).isNotBlank();
	}

}
