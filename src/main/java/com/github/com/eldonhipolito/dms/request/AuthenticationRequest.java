package com.github.com.eldonhipolito.dms.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationRequest {

  private String username;

  private String password;
}
