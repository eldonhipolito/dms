package com.github.com.eldonhipolito.dms.request;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateDocumentRequestWrapper {

  private MultipartFile file;

  private String documentString;

  public void setDocument(String document) {
    this.documentString = document;
  }

  public CreateDocumentRequest getDocument() throws JsonMappingException, JsonProcessingException {

    return new ObjectMapper().readValue(documentString, CreateDocumentRequest.class);
  }

  private CreateDocumentRequest document;
}
