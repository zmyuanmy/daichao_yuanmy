package com.jbb.mgt.server.core.util;

import java.security.MessageDigest;
import java.util.Random;

public class MD5 {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };


	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin) {
		String resultString = null;

		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {

		}
		return resultString;
	}
	public static void main(String[] args) {
		/*String arr[] = {"60%","55%","50%","65%","45%"};
        int len = arr.length;
		Random random = new Random();
		int arrIndex = random.nextInt(len-1);
		String num = arr[arrIndex];
		String percentage=num;*/
		System.out.println(MD5Encode(""));
		System.out.println(PasswordUtil.passwordHash(MD5Encode("")));
	}
	

}