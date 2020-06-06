package com.github.com.eldonhipolito.dms.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class DocumentResponse extends GenericResponse {

  private int id;
  private String title;
  private String hash;
  private int numberSignatoriesRequired;
  private String description;

  public DocumentResponse(
      boolean success,
      String result,
      int id,
      String title,
      String hash,
      int numberSignatoriesRequired,
      String description) {
    super(success, result);
    this.id = id;
    this.title = title;
    this.hash = hash;
    this.numberSignatoriesRequired = numberSignatoriesRequired;
    this.description = description;
  }

  public DocumentResponse(boolean success, String result) {
    super(success, result);
  }
}
