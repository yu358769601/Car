package com.rongzhiheng.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	public static String encrypt(String key, String iv, String cleartext)
			throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
//		Cipher ecipher = Cipher.getInstance("AES/CBC/NoPadding");
		Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		ecipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
//		return Base64_3des.encode(ecipher.doFinal(padRight(cleartext,
//				((int) Math.ceil(cleartext.length() / 16.0)) * 16).getBytes()));
//		return Base64.encodeBytes(ecipher.doFinal(cleartext.getBytes()));
		return Base64.encodeBytes(ecipher.doFinal(cleartext.getBytes())).replace("+", "%2B");
	}

	public static String decrypt(String key, String iv, String encrypted)
			throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
		Cipher ecipher = Cipher.getInstance("AES/CBC/NoPadding");
//		Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		ecipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		return new String(ecipher.doFinal(Base64.decode(encrypted)));
	}


}
