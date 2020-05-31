package com.github.com.eldonhipolito.dms.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jcajce.io.CipherInputStream;
import org.bouncycastle.jcajce.io.CipherOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.com.eldonhipolito.dms.service.EncryptionService;
import com.github.com.eldonhipolito.dms.service.FileEncryptionService;

@Service(value = "FileEncryptionService")
public class FileEncryptionServiceImpl implements FileEncryptionService {

	private final EncryptionService aesEncryptionService;

	private final SecureRandom random;

	private static final int TAG_SIZE = 128;

	@Autowired
	public FileEncryptionServiceImpl(@Qualifier("AESEncryptionService") EncryptionService aesEncryptionService,
			SecureRandom random) {
		super();
		this.aesEncryptionService = aesEncryptionService;
		this.random = random;
	}

	@Override
	public byte[] decrypt(byte[] encrypted, byte[] key) throws InvalidKeyException, InvalidAlgorithmParameterException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return this.aesEncryptionService.decrypt(encrypted, key);
	}

	@Override
	public byte[] encrypt(byte[] raw, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		return this.aesEncryptionService.encrypt(raw, key);
	}

	@Override
	public String generateRandomKey() throws NoSuchAlgorithmException {
		return this.aesEncryptionService.generateRandomKey();
	}

	@Override
	public byte[] decrypt(String fileName, byte[] key)
			throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {

		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		final byte[] nonce = new byte[12];

		try (FileInputStream fs = new FileInputStream(fileName)) {
			fs.read(nonce);
			GCMParameterSpec spec = new GCMParameterSpec(TAG_SIZE, nonce);
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), spec);

			try (CipherInputStream cipherIn = new CipherInputStream(fs, cipher)) {

				// cipherIn.skipNBytes(16);
				return cipherIn.readAllBytes();

			}
		}

	}

	// public static final byte[] nonce = new byte[12];

	@Override
	public void encryptAndStore(String fileName, byte[] content, byte[] key) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, FileNotFoundException, IOException {
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

		final byte[] nonce = new byte[12];
		random.nextBytes(nonce);
		GCMParameterSpec spec = new GCMParameterSpec(TAG_SIZE, nonce);
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), spec);

		// try (FileOutputStream nonceFile = new FileOutputStream(fileName)) {
		System.out.println("putangina Nonce for encryption : " + new String(nonce));
		// nonceFile.write(Hex.encode(nonce));
		// }

		try (FileOutputStream fileOut = new FileOutputStream(new File(fileName));
				CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {
			System.out.println(Base64.getEncoder().encode(nonce).length);
			fileOut.write(nonce);
			cipherOut.write(content);
		}

	}

}
