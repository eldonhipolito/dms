package com.github.com.eldonhipolito.dms.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CreateDocumentAccessRequest {

  private int documentId;

  private String password;

  private String documentOwner;
  private String viewer;
}
