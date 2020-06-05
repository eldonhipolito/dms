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
public class DocumentResponse {

  private int id;
  private String title;
  private String hash;
  private int numberSignatoriesRequired;
}
