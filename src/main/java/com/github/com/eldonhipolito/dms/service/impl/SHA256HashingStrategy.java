package com.github.com.eldonhipolito.dms.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import com.github.com.eldonhipolito.dms.exception.UncheckedException;
import com.github.com.eldonhipolito.dms.service.FileHashingStrategy;

@Service(value = "SHA256HashingStrategy")
public class SHA256HashingStrategy implements FileHashingStrategy {

	private final MessageDigest messageDigest;

	public SHA256HashingStrategy() throws NoSuchAlgorithmException {
		this.messageDigest = MessageDigest.getInstance("SHA-256");
	}

	public SHA256HashingStrategy(MessageDigest messageDigest) {
		this.messageDigest = messageDigest;
	}

	@Override
	public String hash(String msg) {
		byte[] encodedhash = messageDigest.digest(msg.getBytes(StandardCharsets.UTF_8));
		return bytesToHex(encodedhash);
	}

	private static String bytesToHex(byte[] hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	@Override
	public boolean isEqualToRawText(String raw, String hashed) {
		return this.hash(raw).equalsIgnoreCase(hashed);

	}

	@Override
	public String hash(byte[] file) throws UncheckedException {

		byte[] digest = messageDigest.digest(file);
		return bytesToHex(digest);

	}

}
