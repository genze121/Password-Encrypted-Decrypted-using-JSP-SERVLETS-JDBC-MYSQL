package com.Helper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordEncryptDecrypt {

	private static SecretKeySpec secretKeySpec;
	private static byte[] key;
	private static final String ALGORITHM = "AES";

	public void prepareKey(String mkey) {

		MessageDigest messageDigest = null;
		try {
			key = mkey.getBytes(StandardCharsets.UTF_8);
			messageDigest = MessageDigest.getInstance("SHA-1");
			key = messageDigest.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKeySpec = new SecretKeySpec(key, ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	public String encode(String encrypt, String secret) {

		try {
			prepareKey(secret);
			Cipher ecipher = Cipher.getInstance(ALGORITHM);
			ecipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			return Base64.getEncoder().encodeToString(ecipher.doFinal(encrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decode(String decrypt, String secret) {
		try {
			prepareKey(secret);
			Cipher dcipher = Cipher.getInstance(ALGORITHM);
			dcipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			return new String(dcipher.doFinal(Base64.getDecoder().decode(decrypt)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
