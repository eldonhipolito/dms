package com.github.com.eldonhipolito.dms.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ListDocumentResponse extends GenericResponse {

  private List<DocumentResponse> documents;

  public ListDocumentResponse(boolean success, String result, List<DocumentResponse> documents) {
    super(success, result);
    this.documents = documents;
  }
}
