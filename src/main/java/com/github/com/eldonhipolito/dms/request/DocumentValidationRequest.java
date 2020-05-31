package com.github.com.eldonhipolito.dms.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class DocumentValidationRequest {

	private final MultipartFile file;

	private int documentId;

}
