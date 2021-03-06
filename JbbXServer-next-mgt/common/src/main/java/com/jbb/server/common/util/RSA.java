package com.jbb.server.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.jbb.server.common.PropertyManager;

public class RSA {
	private static final Logger log = Logger.getLogger(RSA.class);
	/** 指定key的大小 */
	private static int KEYSIZE = 1024;

	public static final String CHAR_ENCODING = "UTF-8";

	public static final String SHA1WITHRSA = "SHA1withRSA";
	public static final String MD5WITHRSA = "MD5withRSA";

	public static final String NOPADDING = "RSA/NONE/NoPadding";
	public static final String RSANONEPKCS1PADDING = "RSA/NONE/PKCS1Padding";
	public static final String RSAECBPKCS1PADDING = "RSA/ECB/PKCS1Padding";
	public static final String PROVIDER = "BC";

	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * @Fields prikey : 私钥
	 */
	private static PrivateKey prikey;

	/**
	 * @Fields pubkey : 公钥
	 */
	private static PublicKey pubkey;

	/**
	 * @Fields pubkey : 合利宝 cer 公钥
	 */
	private static PublicKey heliCerKey;

	static {
		try {

			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			String filePath = PropertyManager.getProperty("jbb.pay.heli.rsa.private.key.path");
			String password = PropertyManager.decryptProperty("jbb.pay.heli.rsa.private.key.password");

			String cerPath = PropertyManager.getProperty("jbb.pay.heli.rsa.cer.path");

			KeyStore ks = KeyStore.getInstance("PKCS12");
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ks.load(fis, password.toCharArray());
			fis.close();
			String keyAlias = ks.aliases().nextElement();

			prikey = (PrivateKey) ks.getKey(keyAlias, password.toCharArray());

			pubkey = ks.getCertificate(keyAlias).getPublicKey();

			CertificateFactory cff = CertificateFactory.getInstance("X.509");
			FileInputStream fis1 = new FileInputStream(cerPath);
			Certificate cf = cff.generateCertificate(fis1);
			heliCerKey = cf.getPublicKey();
			fis1.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	public static String getCerPublicKeyString() {
		return CodecUtil.toBase64String(heliCerKey.getEncoded());
	}

	public static PublicKey getCerPublicKey() {
		return heliCerKey;
	}

	public static String getPublicKeyString() {
		return CodecUtil.toBase64String(pubkey.getEncoded());
	}

	public static String getPrivateKeyString() {
		return CodecUtil.toBase64String(prikey.getEncoded());
	}

	public static PublicKey getPublicKey() {
		return pubkey;
	}

	public static PrivateKey getPrivateKey() {
		return prikey;
	}

	/**
	 * 生成密钥对
	 */
	public static Map<String, String> generateKeyPair() throws Exception {
		/** RSA算法要求有一个可信任的随机数源 */
		SecureRandom sr = new SecureRandom();
		/** 为RSA算法创建一个KeyPairGenerator对象 */
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		/** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
		kpg.initialize(KEYSIZE, sr);
		/** 生成密匙对 */
		KeyPair kp = kpg.generateKeyPair();
		/** 得到公钥 */
		Key publicKey = kp.getPublic();
		byte[] publicKeyBytes = publicKey.getEncoded();

		String pub = CodecUtil.toBase64String(publicKeyBytes);
		/** 得到私钥 */
		Key privateKey = kp.getPrivate();
		byte[] privateKeyBytes = privateKey.getEncoded();
		String pri = CodecUtil.toBase64String(privateKeyBytes);

		Map<String, String> map = new HashMap<String, String>();
		map.put("publicKey", pub);
		map.put("privateKey", pri);
		RSAPublicKey rsp = (RSAPublicKey) kp.getPublic();
		BigInteger bint = rsp.getModulus();
		byte[] b = bint.toByteArray();
		String retValue = CodecUtil.toBase64String(b);
		map.put("modulus", retValue);
		return map;
	}

	/**
	 * 验证签名
	 *
	 * @param data
	 *            数据
	 * @param sign
	 *            签名
	 * @param publicKey
	 *            公钥
	 * @return
	 */
	public static boolean verifySign(byte[] data, byte[] sign, PublicKey publicKey) {
		try {
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initVerify(publicKey);
			signature.update(data);
			boolean result = signature.verify(sign);
			return result;
		} catch (Exception e) {

			throw new RuntimeException("verifySign fail!", e);
		}
	}

	/**
	 * 验证签名
	 *
	 * @param data
	 *            数据
	 * @param sign
	 *            签名
	 * @param pubicKey
	 *            公钥
	 * @return
	 */
	public static boolean verifySign(String data, String sign, PublicKey pubicKey) {
		try {
			byte[] dataByte = data.getBytes("UTF-8");

			byte[] signByte = CodecUtil.fromBase64(sign.getBytes("UTF-8"));
			return verifySign(dataByte, signByte, pubicKey);
		} catch (UnsupportedEncodingException e) {

			throw new RuntimeException("verifySign fail! data[" + data + "] sign[" + sign + "]", e);
		}
	}

	/**
	 * 签名
	 *
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] sign(byte[] data, PrivateKey key) {
		try {
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(key);
			signature.update(data);
			return signature.sign();
		} catch (Exception e) {
			throw new RuntimeException("sign fail!", e);
		}
	}

	/**
	 * 代付签名
	 * 
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws SignatureException
	 */
	public static String sign(String content, String privateKey) {
		String charset = CHAR_ENCODING;
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(CodecUtil.fromBase64(privateKey.getBytes()));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(priKey);
			signature.update(content.getBytes(charset));
			byte[] signed = signature.sign();
			return new String(CodecUtil.toBase64String(signed));
		} catch (Exception e) {
			throw new RuntimeException("sign fail!", e);
		}
	}

	/**
	 * 签名
	 *
	 * @param data
	 * @param key
	 * @return
	 */
	public static String sign(String data, PrivateKey key) {
		try {
			byte[] dataByte = data.getBytes("UTF-8");
			return CodecUtil.toBase64String(sign(dataByte, key));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("sign fail!", e);
		}
	}

	/**
	 * 加密
	 *
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] encrypt(byte[] data, Key key, String padding) {
		try {
			final Cipher cipher = Cipher.getInstance(padding, PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(data);
		} catch (Exception e) {

			throw new RuntimeException("encrypt fail!", e);
		}
	}

	/**
	 * 加密
	 *
	 * @param data
	 * @param key
	 * @return
	 */
	public static String encryptToBase64(String data, Key key, String padding) {
		try {
			return CodecUtil.toBase64String(encrypt(data.getBytes("UTF-8"), key, padding));
		} catch (Exception e) {
			throw new RuntimeException("encrypt fail!", e);
		}
	}

	/**
	 * 解密
	 *
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] decrypt(byte[] data, Key key, String padding) {
		try {
			final Cipher cipher = Cipher.getInstance(padding, PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException("encrypt fail!", e);
		}
	}

	/**
	 * 解密
	 *
	 * @param data
	 * @param key
	 * @return
	 */
	public static String decryptFromBase64(String data, Key key, String padding) {
		try {
			return new String(decrypt(CodecUtil.fromBase64(data.getBytes()), key, padding), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException("encrypt fail!", e);
		}
	}

	public static void createKeyPairs(int size) throws Exception {
		// create the keys
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", PROVIDER);
		generator.initialize(size, new SecureRandom());
		KeyPair pair = generator.generateKeyPair();
		PublicKey pubKey = pair.getPublic();
		PrivateKey privKey = pair.getPrivate();
		byte[] pk = pubKey.getEncoded();
		byte[] privk = privKey.getEncoded();
		String strpk = CodecUtil.toBase64String(pk);
		String strprivk = CodecUtil.toBase64String(privk);
		System.out.println("公钥:" + Arrays.toString(pk));
		System.out.println("私钥:" + Arrays.toString(privk));
		System.out.println("公钥Base64编码:" + strpk);
		System.out.println("私钥Base64编码:" + strprivk);
	}

	public static PublicKey getPublicKey(String base64EncodePublicKey) throws Exception {
		KeyFactory keyf = KeyFactory.getInstance("RSA", PROVIDER);
		X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(CodecUtil.fromBase64(base64EncodePublicKey.getBytes()));
		PublicKey pubkey = keyf.generatePublic(pubX509);
		return pubkey;
	}

	public static PrivateKey getPrivateKey(String base64EncodePrivateKey) throws Exception {
		KeyFactory keyf = KeyFactory.getInstance("RSA", PROVIDER);
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(CodecUtil.fromBase64(base64EncodePrivateKey.getBytes()));
		PrivateKey privkey = keyf.generatePrivate(priPKCS8);
		return privkey;
	}

	public static byte[] encode(String encodeString, Key key, String padding) throws Exception {
		final Cipher cipher = Cipher.getInstance(padding, PROVIDER);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] bytes = encodeString.getBytes("UTF-8");
		byte[] encodedByteArray = new byte[] {};
		for (int i = 0; i < bytes.length; i += 117) {
			byte[] subarray = ArrayUtils.subarray(bytes, i, i + 117);
			byte[] doFinal = cipher.doFinal(subarray);
			encodedByteArray = ArrayUtils.addAll(encodedByteArray, doFinal);
		}
		return encodedByteArray;
	}

	/**
	 * 加密
	 *
	 * @param data
	 * @param key
	 * @return
	 */
	public static String encodeToBase64(String data, Key key, String padding) {
		try {
			return CodecUtil.toBase64String(encode(data, key, padding));
		} catch (Exception e) {
			throw new RuntimeException("encrypt fail!", e);
		}
	}

	public static String decode(byte[] decodeByteArray, Key key, String padding)
			throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, IOException, NoSuchProviderException {
		final Cipher cipher = Cipher.getInstance(padding, PROVIDER);
		cipher.init(Cipher.DECRYPT_MODE, key);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < decodeByteArray.length; i += 128) {
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(decodeByteArray, i, i + 128));
			sb.append(new String(doFinal));
		}
		return sb.toString();
	}

	/**
	 * 解密
	 *
	 * @param data
	 * @param key
	 * @return
	 */
	public static String decodeFromBase64(String data, Key key, String padding) {
		try {
			return new String(decode(CodecUtil.fromBase64(data.getBytes()), key, padding).getBytes(), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException("encrypt fail!", e);
		}
	}

	/**
	 * 得到密钥字符串（经过base64编码）
	 *
	 * @return
	 */
	public static String getKeyString(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
		return CodecUtil.toBase64String(keyBytes);
	}

	public static String getKeyStringByCer(String path) throws Exception {
		CertificateFactory cff = CertificateFactory.getInstance("X.509");
		FileInputStream fis1 = new FileInputStream(path);
		Certificate cf = cff.generateCertificate(fis1);
		PublicKey pk1 = cf.getPublicKey();
		String key = getKeyString(pk1);
		System.out.println("public:\n" + key);
		return key;
	}

	public static PublicKey getPublicKeyByCert(String path) throws Exception {
		CertificateFactory cff = CertificateFactory.getInstance("X.509");
		FileInputStream fis1 = new FileInputStream(path);
		Certificate cf = cff.generateCertificate(fis1);
		PublicKey publicKey = cf.getPublicKey();
		return publicKey;
	}

	public static String getKeyStringByPfx(String strPfx, String strPassword) {
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(strPfx);
			// If the keystore password is empty(""), then we have to set
			// to null, otherwise it won't work!!!
			char[] nPassword = null;
			if ((strPassword == null) || strPassword.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = strPassword.toCharArray();
			}
			ks.load(fis, nPassword);
			fis.close();
			System.out.println("keystore type=" + ks.getType());
			// Now we loop all the aliases, we need the alias to get keys.
			// It seems that this value is the "Friendly name" field in the
			// detals tab <-- Certificate window <-- view <-- Certificate
			// Button <-- Content tab <-- Internet Options <-- Tools menu
			// In MS IE 6.
			Enumeration enumas = ks.aliases();
			String keyAlias = null;
			if (enumas.hasMoreElements())// we are readin just one certificate.
			{
				keyAlias = (String) enumas.nextElement();
				System.out.println("alias=[" + keyAlias + "]");
			}
			// Now once we know the alias, we could get the keys.
			System.out.println("is key entry=" + ks.isKeyEntry(keyAlias));
			PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
			Certificate cert = ks.getCertificate(keyAlias);
			PublicKey pubkey = cert.getPublicKey();

			String basePrikey = RSA.getKeyString(prikey);
			System.out.println("cert class = " + cert.getClass().getName());
			System.out.println("cert = " + cert);
			System.out.println("public key = " + pubkey);
			System.out.println("private key = " + prikey);
			System.out.println("pubkey key = " + RSA.getKeyString(pubkey));
			System.out.println("prikey key = " + RSA.getKeyString(prikey));
			System.out.println("pubkey key length = " + RSA.getKeyString(pubkey).length());
			System.out.println("prikey key length = " + RSA.getKeyString(prikey).length());
			return basePrikey;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static PrivateKey getPrivateKey(String pfxPath, String pfxPassword) {
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(pfxPath);
			// If the keystore password is empty(""), then we have to set
			// to null, otherwise it won't work!!!
			char[] nPassword = null;
			if ((pfxPassword == null) || pfxPassword.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = pfxPassword.toCharArray();
			}
			ks.load(fis, nPassword);
			fis.close();
			Enumeration enumas = ks.aliases();
			String keyAlias = null;
			if (enumas.hasMoreElements())// we are readin just one certificate.
			{
				keyAlias = (String) enumas.nextElement();
			}
			PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
			return prikey;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static PublicKey getPublicKey(String pfxPath, String pfxPassword) {
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(pfxPath);
			// If the keystore password is empty(""), then we have to set
			// to null, otherwise it won't work!!!
			char[] nPassword = null;
			if ((pfxPassword == null) || pfxPassword.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = pfxPassword.toCharArray();
			}
			ks.load(fis, nPassword);
			fis.close();
			Enumeration<String> enumas = ks.aliases();
			String keyAlias = null;
			if (enumas.hasMoreElements()) {
				keyAlias = (String) enumas.nextElement();
			}
			Certificate cert = ks.getCertificate(keyAlias);
			PublicKey pubkey = cert.getPublicKey();
			return pubkey;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String args[]) throws Exception {
		Map<String, String> keys = RSA.generateKeyPair();

		System.out.println("publik: " + keys.get("publicKey"));
		System.out.println("private: " + keys.get("privateKey"));
		String pfxPath = "/Users/VincentTang/ws/jbbmgthome/config/pfx/jbb.pfx";
		String cerPath = "/Users/VincentTang/ws/jbbmgthome/config/pfx/helipay.cer";

		String pfxPathPassword = "jieBB123.";

		String aesKey = "U7GE5esh1InRlMgU";

		PublicKey publicKey = RSA.getPublicKeyByCert(cerPath);
		String encrytionKey = RSA.encodeToBase64(aesKey, publicKey, KEY_ALGORITHM);
		System.out.println(encrytionKey);

	}

}