package com.github.com.eldonhipolito.dms.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class GenericResponse {

	public boolean success;

	private String result;

}
