package com.github.com.eldonhipolito.dms.service;

public interface HashingStrategy {

	String hash(String msg);

	boolean isEqualToRawText(String raw, String hashed);

}
