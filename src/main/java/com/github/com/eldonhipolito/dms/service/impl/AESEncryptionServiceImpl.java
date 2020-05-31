package com.github.com.eldonhipolito.dms.service.impl;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.Arrays;
import org.springframework.stereotype.Service;

import com.github.com.eldonhipolito.dms.service.EncryptionService;

@Service(value = "AESEncryptionService")
public class AESEncryptionServiceImpl implements EncryptionService {

	private static final int KEY_SIZE = 256;

	private static final int TAG_SIZE = 128;

	@Override
	public byte[] encrypt(byte[] raw, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecureRandom random = SecureRandom.getInstanceStrong();

		// Encrypt
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		final byte[] nonce = new byte[12];
		random.nextBytes(nonce);
		GCMParameterSpec spec = new GCMParameterSpec(TAG_SIZE, nonce);
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), spec);

		byte[] cipherText = cipher.doFinal(raw);

		return Arrays.concatenate(nonce, cipherText);

	}

	@Override
	public byte[] decrypt(byte[] encrypted, byte[] key) throws InvalidKeyException, InvalidAlgorithmParameterException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

		byte[] nonce = Arrays.copyOfRange(encrypted, 0, 12);

		byte[] newVal = Arrays.copyOfRange(encrypted, 12, encrypted.length);

		GCMParameterSpec spec = new GCMParameterSpec(TAG_SIZE, nonce);
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), spec);

		return cipher.doFinal(encrypted);

	}

	@Override
	public String generateRandomKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(KEY_SIZE);

		// get base64 encoded version of the key
		String encodedKey = Base64.getEncoder().encodeToString(keyGen.generateKey().getEncoded());

		return encodedKey;
	}

}
