package com.ylian.common.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by dafan on 2016/11/12 0012.
 */

public class Security {
	/**
	 * @param input
	 * @return
	 */
	public static String encrypt(String input) {
		byte[] crypted = null;
		try {
			String key = "DKN#IK@NFKLS*&FN";
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			crypted = cipher.doFinal(input.getBytes());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		/*return new String(Base64.encodeBase64(crypted));*/
		return new String(Base64.encode(crypted, Base64.DEFAULT));
	}

	/**
	 * @param input
	 * @return
	 */
	public static String decrypt(String input) {
		byte[] output = null;
		try {
			String key = "DKN#IK@NFKLS*&FN";
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			/*output = cipher.doFinal(Base64.decodeBase64(input));*/
			output = cipher.doFinal(Base64.decode(input, Base64.DEFAULT));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return new String(output);
	}
}
