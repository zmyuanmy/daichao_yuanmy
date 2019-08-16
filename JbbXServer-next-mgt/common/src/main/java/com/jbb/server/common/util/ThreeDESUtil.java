package com.jbb.server.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.jbb.server.common.PropertyManager;

public class ThreeDESUtil {

	public static final String CHAR_ENCODING = "UTF-8";

	public static final String DES_ALGORITHM = "DESede/CBC/PKCS5Padding";

	/**
	 * 加密
	 * 
	 * @param data
	 *            需要加密的内容
	 * @param key
	 *            加密密码
	 * @return
	 */
	public static byte[] encrypt(byte[] data, byte[] key, byte[] ivbyte) {

		if (key.length != 24) {
			throw new RuntimeException("Invalid DES key length (must be 24 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, "DESede");
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "DESede");
			Cipher cipher = Cipher.getInstance(DES_ALGORITHM);// 创建密码器
			IvParameterSpec ips = new IvParameterSpec(ivbyte);
			cipher.init(Cipher.ENCRYPT_MODE, seckey, ips);// 初始化
			byte[] result = cipher.doFinal(data);
			return result; // 加密
		} catch (Exception e) {
			throw new RuntimeException("encrypt fail!", e);
		}
	}

	public static String encryptWithBase64(byte[] data, byte[] key, byte[] ivbyte) {

		if (key.length != 24) {
			throw new RuntimeException("Invalid AES key length (must be 24 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, "DESede");
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "DESede");
			Cipher cipher = Cipher.getInstance(DES_ALGORITHM);// 创建密码器
			IvParameterSpec ips = new IvParameterSpec(ivbyte);
			cipher.init(Cipher.ENCRYPT_MODE, seckey, ips);// 初始化
			byte[] result = cipher.doFinal(data);
			return CodecUtil.toBase64String(result); // 加密并base64
		} catch (Exception e) {
			throw new RuntimeException("encrypt fail!", e);
		}
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密内容
	 * @param key
	 *            解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] data, byte[] key, byte[] ivbyte) {

		if (key.length != 24) {
			throw new RuntimeException("Invalid DES key length (must be 24 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, "DESede");
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "DESede");
			Cipher cipher = Cipher.getInstance(DES_ALGORITHM);// 创建密码器
			IvParameterSpec ips = new IvParameterSpec(ivbyte);
			cipher.init(Cipher.DECRYPT_MODE, seckey, ips);// 初始化

			byte[] result = cipher.doFinal(data);
			return result; // 加密
		} catch (Exception e) {
			throw new RuntimeException("decrypt fail!", e);
		}
	}

	public static String decryptWithBas64(String data, byte[] key, byte[] ivbyte) {

		if (key.length != 24) {
			throw new RuntimeException("Invalid AES key length (must be 24 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, "DESede");
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "DESede");
			Cipher cipher = Cipher.getInstance(DES_ALGORITHM);// 创建密码器
			IvParameterSpec ips = new IvParameterSpec(ivbyte);
			cipher.init(Cipher.DECRYPT_MODE, seckey, ips);// 初始化
			byte[] dataB = CodecUtil.fromBase64(data); // base 64
			byte[] result = cipher.doFinal(dataB); // 解密
			return new String(result);
		} catch (Exception e) {
			throw new RuntimeException("decrypt fail!", e);
		}
	}

	public static void main(String args[]) throws Exception {
		String aesKey = PropertyManager.getProperty("app.3des.key");
		String str = "to do list";
		byte[] iv = "12345678".getBytes();
		byte[] s1 = ThreeDESUtil.encrypt(str.getBytes(), aesKey.getBytes(), iv);
		byte[] s2 = ThreeDESUtil.decrypt(s1, aesKey.getBytes(), iv);
		
		System.out.println("s=" + new String(s2));

		String str1 = ThreeDESUtil.encryptWithBase64(str.getBytes(), aesKey.getBytes(), iv);
		String str2 = ThreeDESUtil.decryptWithBas64(str1, aesKey.getBytes(), iv);
		System.out.println(str1);
		System.out.println(str2);

	}

}
