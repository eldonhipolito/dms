package com.github.com.eldonhipolito.dms.service;

import com.github.com.eldonhipolito.dms.exception.UncheckedException;

public interface FileHashingStrategy extends HashingStrategy {

	String hash(byte[] file) throws UncheckedException;

	boolean isEqualToRawFile(byte[] raw, String hashed);
}
