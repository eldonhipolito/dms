package com.github.com.eldonhipolito.dms.request;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = "file")
public class CreateDocumentRequest {

  private String title;
  private String description;
  private int numberSignatoriesRequired;
  private MultipartFile file;
  private String creator;



  // for milestone 1 password will be used as the key
  private String password;
}
