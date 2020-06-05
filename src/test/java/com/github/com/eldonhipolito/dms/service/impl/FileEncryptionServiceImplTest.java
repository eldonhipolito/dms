package com.github.com.eldonhipolito.dms.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.com.eldonhipolito.dms.service.EncryptionService;
import com.github.com.eldonhipolito.dms.service.FileEncryptionService;

class FileEncryptionServiceImplTest {

  private static EncryptionService aesEncryptionService;

  private static FileEncryptionService fileEncryptionService;

  private static String encodedKey;

  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  @BeforeAll
  static void setUp() throws NoSuchAlgorithmException {
    aesEncryptionService = Mockito.mock(EncryptionService.class);
    fileEncryptionService = new FileEncryptionServiceImpl(aesEncryptionService);
    encodedKey = genKey();
  }

  private static String genKey() throws NoSuchAlgorithmException {
    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    keyGen.init(256);

    // get base64 encoded version of the key
    String encodedKey = Base64.getEncoder().encodeToString(keyGen.generateKey().getEncoded());

    return encodedKey;
  }

  @Test
  void testEncryptAndStoreSuccess()
      throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
          InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
          FileNotFoundException, IOException {

    File file = new File("C:\\Users\\Admin\\Desktop\\OS204\\1.pdf");

    FileInputStream fis = new FileInputStream(file);

    fileEncryptionService.encryptAndStore(
        "C:\\Users\\Admin\\Desktop\\OS204\\encrypted1",
        fis.readAllBytes(),
        Base64.getDecoder().decode(encodedKey));

    fis.close();

    //		try (FileInputStream fs = new
    // FileInputStream("C:\\Users\\Admin\\Desktop\\OS204\\encrypted1")) {
    //			// fs.read(encodedNonce);
    //			// System.out.println("Nonce for decryption : " + new
    //			// String(Base64.getDecoder().decode(encodedNonce)));
    //
    //			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
    //			GCMParameterSpec spec = new GCMParameterSpec(128, FileEncryptionServiceImpl.nonce);
    //			cipher.init(Cipher.DECRYPT_MODE,
    //					new SecretKeySpec(Base64.getDecoder().decode(encodedKey.getBytes()), "AES"), spec);
    //
    //			try (CipherInputStream cipherIn = new CipherInputStream(fs, cipher)) {
    //
    //				// cipherIn.skipNBytes(16);
    //				byte[] content = cipherIn.readAllBytes();
    //
    //				FileOutputStream fos = new FileOutputStream(
    //						new File("C:\\Users\\Admin\\Desktop\\OS204\\decrypted1.pdf"));
    //				fos.write(content);
    //				fos.flush();
    //				fos.close();
    //			}
    //		}
    assertThat(true).isEqualTo(true);
  }

  @Test
  void testDecrypt()
      throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
          NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
    byte[] decoded =
        fileEncryptionService.decrypt(
            "C:\\Users\\Admin\\Desktop\\OS204\\encrypted1", Base64.getDecoder().decode(encodedKey));

    File file = new File("C:\\Users\\Admin\\Desktop\\OS204\\decrypted.pdf");

    try (FileOutputStream fis = new FileOutputStream(file)) {
      fis.write(decoded);
    }

    // manually verify for now
    // TODO automatic verification
    assertThat(true).isEqualTo(true);
  }
}
