package com.jbb.mgt.server.core.util;

import com.jbb.server.common.PropertyManager;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Copyright (C), 2018-2021, 深圳小财迷信息科技有限公司
 * FileName: Test
 * Author:   xiaojian
 * Date:     2018/12/25 15:13
 * Description:
 *
 * @since 1.0.0
 */
public class RSAUtilTest {

    public static void main(String[] args) throws Exception{
        KeyPair keyPair = RSAUtil.getKeyPair();
//        String publicKeyStr = RSAUtil.getPublicKey(keyPair);
//        String privateKeyStr = RSAUtil.getPrivateKey(keyPair);
        String publicKeyStr = PropertyManager.getProperty("jbb.pf.joint.user.login.RSA.publicKeyStr");
        String privateKeyStr = PropertyManager.getProperty("jbb.pf.joint.user.login.RSA.privateKeyStr");
        System.out.println("RSA公钥Base64编码:" + publicKeyStr);
        System.out.println("RSA私钥Base64编码:" + privateKeyStr);

        //=================客户端=================
        String phone = "{\"phone\":\"15712341234\",\"channelCode\":\"nYqRLj\"}";
        //将Base64编码后的公钥转换成PublicKey对象
        PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
        // 生成AES秘钥，并Base64编码
        String aesKeyStr = AESUtil.genKeyAES();
        System.out.println("AES秘钥Base64编码:" + aesKeyStr);
        //用公钥加密AES秘钥
        byte[] publicEncrypt = RSAUtil.publicEncrypt(aesKeyStr.getBytes(), publicKey);
        //公钥加密AES秘钥后的内容Base64编码
        String publicEncryptStr = RSAUtil.byte2Base64(publicEncrypt);
        System.out.println("公钥加密AES秘钥并Base64编码的结果：" + publicEncryptStr);

        //将Base64编码后的AES秘钥转换成SecretKey对象
        SecretKey aesKey = AESUtil.loadKeyAES(aesKeyStr);
        //用AES秘钥加密实际的内容
        byte[] encryptAES = AESUtil.encryptAES(phone.getBytes(), aesKey);
        // AES秘钥加密后的内容Base64编码
        String encryptAESStr = AESUtil.byte2Base64(encryptAES);
        System.out.println("AES秘钥加密实际的内容并Base64编码的结果：" + encryptAESStr);


        //##############	网络上传输的内容有Base64编码后的公钥加密AES秘钥的结果 和 Base64编码后的AES秘钥加密实际内容的结果   #################
        //##############	即publicEncryptStr和encryptAESStr	###################

        //===================服务端================
        //将Base64编码后的私钥转换成PrivateKey对象
        PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
        //公钥加密AES秘钥后的内容(Base64编码)，进行Base64解码

        byte[] publicEncrypt2 = RSAUtil.base642Byte(publicEncryptStr);
        //用私钥解密,得到aesKey
        byte[] aesKeyStrBytes = RSAUtil.privateDecrypt(publicEncrypt2, privateKey);
        //解密后的aesKey
        String aesKeyStr2 = new String(aesKeyStrBytes);
        System.out.println("解密后的aesKey(Base64编码): " + aesKeyStr2);

        //将Base64编码后的AES秘钥转换成SecretKey对象
        SecretKey aesKey2 = AESUtil.loadKeyAES(aesKeyStr2);
        //AES秘钥加密后的内容(Base64编码)，进行Base64解码
        byte[] encryptAES2 = AESUtil.base642Byte(encryptAESStr);
        //用AES秘钥解密实际的内容
        byte[] decryptAES = AESUtil.decryptAES(encryptAES2, aesKey2);
        //解密后的实际内容
        System.out.println("解密后的实际内容: " + new String(decryptAES));


    }
}