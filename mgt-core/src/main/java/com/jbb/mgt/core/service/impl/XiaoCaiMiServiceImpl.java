package com.jbb.mgt.core.service.impl;

import java.security.PrivateKey;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jbb.mgt.core.dao.UserDao;
import com.jbb.mgt.core.domain.JointUserLoginData;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.service.XiaoCaiMiService;
import com.jbb.mgt.server.core.util.AESUtil;
import com.jbb.mgt.server.core.util.RSAUtil;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Service("XiaoCaiMiService")
@Slf4j
public class XiaoCaiMiServiceImpl implements XiaoCaiMiService {

    @Autowired
    UserDao userDao;

    @Override
    public boolean checkPhoneNumberMd5ExistUser(String mobilePhone) {
        return userDao.checkPhoneNumberMd5ExistUser(mobilePhone);
    }

    @Override
    public JointUserLoginData jointUserLogin(String entryKey, String entryData) throws Exception {

        String privateKeyStr = PropertyManager.getProperty("jbb.pf.joint.user.login.RSA.privateKeyStr");
        JointUserLoginData jointUserLogin = new JointUserLoginData();

        // 解密
        // 将Base64编码后的私钥转换成PrivateKey对象
        PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
        // 公钥加密AES秘钥后的内容(Base64编码)，进行Base64解码
        byte[] publicEncrypt2 = RSAUtil.base642Byte(entryKey);
        // 用私钥解密,得到aesKey
        byte[] aesKeyStrBytes = RSAUtil.privateDecrypt(publicEncrypt2, privateKey);
        // 解密后的aesKey
        String aesKeyStr2 = new String(aesKeyStrBytes);

        // 将Base64编码后的AES秘钥转换成SecretKey对象
        SecretKey aesKey2 = AESUtil.loadKeyAES(aesKeyStr2);
        // AES秘钥加密后的内容(Base64编码)，进行Base64解码
        byte[] encryptAES2 = AESUtil.base642Byte(entryData);
        // 用AES秘钥解密实际的内容
        byte[] decryptAES = AESUtil.decryptAES(encryptAES2, aesKey2);
        log.debug(">JointUserLoginData AES data{}"+new String(decryptAES));
        // 获取手机号码
        JSONObject json = JSON.parseObject(new String(decryptAES));
        String phone = json.getString("phone");
        String channelCode = json.getString("channelCode");

        if (StringUtil.isEmpty(phone) || phone.length() != 11) {
            throw new Exception("手机号不正确");
        }
        if (StringUtil.isEmpty(channelCode)) {
            throw new Exception("渠道不能为空");
        }
        List<User> users = userDao.selectUserByPhoneNumber(phone);
        if (users.size() > 0) {
            jointUserLogin.setCode(302);
            return jointUserLogin;
        } else {
            jointUserLogin.setCode(305);
            jointUserLogin.setPhone(phone);
            jointUserLogin.setChannelCode(channelCode);;
        }
        return jointUserLogin;
    }
}
