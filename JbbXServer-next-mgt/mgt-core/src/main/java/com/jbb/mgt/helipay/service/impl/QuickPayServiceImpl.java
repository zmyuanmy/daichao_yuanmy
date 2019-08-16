package com.jbb.mgt.helipay.service.impl;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.ErrorManager;

import com.jbb.mgt.core.dao.XjlUserOrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.helipay.service.QuickPayService;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.mgt.helipay.vo.AgreementPayBindCardValidateCodeRspVo;
import com.jbb.mgt.helipay.vo.AgreementPayBindCardValidateCodeVo;
import com.jbb.mgt.helipay.vo.BankCardUnbindRspVo;
import com.jbb.mgt.helipay.vo.BankCardUnbindVo;
import com.jbb.mgt.helipay.vo.BankCardbindListRspVo;
import com.jbb.mgt.helipay.vo.BankCardbindListVo;
import com.jbb.mgt.helipay.vo.QuickPayBindCardRspVo;
import com.jbb.mgt.helipay.vo.QuickPayBindCardVo;
import com.jbb.mgt.helipay.vo.QuickPayBindPayRspVo;
import com.jbb.mgt.helipay.vo.QuickPayBindPayValidateCodeRspVo;
import com.jbb.mgt.helipay.vo.QuickPayBindPayValidateCodeVo;
import com.jbb.mgt.helipay.vo.QuickPayBindPayVo;
import com.jbb.mgt.helipay.vo.QuickPayConfirmPayRspVo;
import com.jbb.mgt.helipay.vo.QuickPayConfirmPayVo;
import com.jbb.mgt.helipay.vo.QuickPayCreateOrderRspVo;
import com.jbb.mgt.helipay.vo.QuickPayCreateOrderVo;
import com.jbb.mgt.helipay.vo.QuickPayQueryRspVo;
import com.jbb.mgt.helipay.vo.QuickPayQueryVo;
import com.jbb.mgt.helipay.vo.QuickPaySendValidateCodeRspVo;
import com.jbb.mgt.helipay.vo.QuickPaySendValidateCodeVo;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.HeLiPayException;
import com.jbb.server.common.util.AES;
import com.jbb.server.common.util.RSA;
import com.jbb.server.common.util.StringUtil;

@Service("QuickPayService")
public class QuickPayServiceImpl implements QuickPayService {
    private static Logger logger = LoggerFactory.getLogger(QuickPayServiceImpl.class);

    @Autowired
    private XjlUserOrderDao xjlUserOrderDao;

    @Override
    public void quickPayCreateOrder(String userId, String orderId, String cardName, String idcard, String cardNo,
        String phone, String amount, String orderIp, String terminalId) throws Exception {
        logger.debug(">quickPayCreateOrder, orderId={},  userId = {} , idcard={}, username = {} , cardNo={} ", orderId,
            userId, idcard, cardName, cardNo);

//        String aesKey = StringUtil.randomAlphaNum(16);
        String aesKey = "HotSVP28JRPD4IM2";
        logger.debug("aesKey：" + aesKey);
        PrivateKey privateKey = RSA.getPrivateKey();
        PublicKey cerPubKey = RSA.getCerPublicKey();

        String serverCallbackUrl = PropertyManager.getProperty("jbb.pay.heli.callback.url");

        // 加密aesKey
        String encryptionKey = RSA.encryptToBase64(aesKey, cerPubKey, RSA.KEY_ALGORITHM);

        // 加密
        String phoneE = AES.encryptWithBase64(phone.getBytes(), aesKey.getBytes());
        String idcardE = AES.encryptWithBase64(idcard.getBytes(), aesKey.getBytes());
        String cardNoE = AES.encryptWithBase64(cardNo.getBytes(), aesKey.getBytes());
        //
        QuickPayCreateOrderVo vo = new QuickPayCreateOrderVo(userId, orderId, cardName, idcardE, cardNoE, phoneE, amount,
            orderIp, terminalId, serverCallbackUrl, encryptionKey);
        // 获取签名
        String sign = RSA.sign(vo.getSigned(), privateKey);
        logger.info("signed：" + vo.getSigned());
        logger.info("sign：" + sign);
        vo.setSign(sign);

        // 1. 调用接口
        QuickPayCreateOrderRspVo rspVo = (QuickPayCreateOrderRspVo)HeliUtil.post(vo.getRequestFormStr(),
            QuickPayCreateOrderRspVo.class, vo.getP1_bizType());

        if (rspVo == null) {
            logger.info("<quickPayCreateOrder, return with error");
            throw new HeLiPayException();
        }

        // 2. 验证签名
        if (!RSA.verifySign(rspVo.getSigned(), rspVo.getSign(), cerPubKey)) {
            logger.info("<quickPayCreateOrder, return vo sign not equal");
            throw new HeLiPayException("jbb.xjl.error.exception.hlb.signError", "zh");
        }

        // 3. 处理逻辑
        checkRetCode(rspVo.getRt2_retCode(),rspVo.getRt3_retMsg(),rspVo.toString());

        logger.debug("<quickPayCreateOrder");

    }

    @Override
    public void quickPaySendValidateCode(String orderId, String phone) throws Exception {
        logger.debug(">quickPayCreateOrder, orderId = {} , phone={} ", orderId, phone);

        //String aesKey = StringUtil.randomAlphaNum(16);
        String aesKey = "HotSVP28JRPD4IM2";
        logger.debug("aesKey：" + aesKey);

        PrivateKey privateKey = RSA.getPrivateKey();
        PublicKey cerPubKey = RSA.getCerPublicKey();

        // 加密aesKey
        String encryptionKey = RSA.encryptToBase64(aesKey, cerPubKey, RSA.KEY_ALGORITHM);
        // 加密phone
        String phoneE = AES.encryptWithBase64(phone.getBytes(), aesKey.getBytes());
        QuickPaySendValidateCodeVo vo = new QuickPaySendValidateCodeVo(orderId, phoneE, encryptionKey);
        // 获取签名
        String sign = RSA.sign(vo.getSigned(), privateKey);
        logger.info("signed：" + vo.getSigned());
        logger.info("sign：" + sign);
        vo.setSign(sign);

        // 1. 调用接口
        QuickPaySendValidateCodeRspVo rspVo =
            (QuickPaySendValidateCodeRspVo)HeliUtil.post(vo.getRequestFormStr(), QuickPaySendValidateCodeRspVo.class, vo.getP1_bizType());

        if (rspVo == null) {
            logger.info("<quickPayCreateOrder, return with error");
            throw new HeLiPayException();
        }
        // 2. 验证签名
        if (!RSA.verifySign(rspVo.getSigned(), rspVo.getSign(), cerPubKey)) {
            throw new HeLiPayException("jbb.xjl.error.exception.hlb.signError", "zh");
        }

        // 3. 处理逻辑
        checkRetCode(rspVo.getRt2_retCode(),rspVo.getRt3_retMsg(),rspVo.toString());

        logger.debug("<quickPayCreateOrder");

    }

    @Override
    public void quickPayConfirmPay(String orderId, String msgCode, String ipAddress) throws Exception {
        logger.debug(">quickPayConfirmPay,orderId={},  msgCode = {} , ipAddress={} ", orderId, msgCode, ipAddress);

        String aesKey = StringUtil.randomAlphaNum(16);
        logger.debug("aesKey：" + aesKey);
        PrivateKey privateKey = RSA.getPrivateKey();
        PublicKey cerPubKey = RSA.getCerPublicKey();

        // 加密aesKey
        String encryptionKey = RSA.encryptToBase64(aesKey, cerPubKey, RSA.KEY_ALGORITHM);
        // 加密msgCode
        String msgCodeE = AES.encryptWithBase64(msgCode.getBytes(), aesKey.getBytes());

        QuickPayConfirmPayVo vo = new QuickPayConfirmPayVo(orderId, msgCodeE, ipAddress, encryptionKey);
        // 获取签名
        String sign = RSA.sign(vo.getSigned(), privateKey);
        logger.info("sign：" + sign);
        vo.setSign(sign);

        // 1. 调用接口
        QuickPayConfirmPayRspVo rspVo =
            (QuickPayConfirmPayRspVo)HeliUtil.post(vo.getRequestFormStr(), QuickPayConfirmPayRspVo.class, vo.getP1_bizType());

        if (rspVo == null) {
            logger.info("<quickPayConfirmPay, return with error ");
            throw new HeLiPayException();
        }

        // 2. 验证签名
        if (!RSA.verifySign(rspVo.getSigned(), rspVo.getSign(), cerPubKey)) {
            logger.info("<quickPayConfirmPay, return vo sign not equal");
            throw new HeLiPayException("jbb.xjl.error.exception.hlb.signError", "zh");
        }

        // 3. 处理逻辑
        if(!rspVo.getRt2_retCode().equals("0000")){
            logger.info("<HeLiPayResponse, Response = "+rspVo.toString());
            if(rspVo.getRt2_retCode().equals("8015")){
                throw new HeLiPayException("验证码错误或过期。");
            }else{
                throw new HeLiPayException("还款支付失败，请检查银行卡。");
            }
        }

        //调用成功之后，将返回的状态保存起来
        xjlUserOrderDao.updateXjlUserOrder(orderId, rspVo.getRt9_orderStatus());
        logger.debug("<quickPayConfirmPay");

    }

    @Override
    public void agreementPayBindCardValidateCode(String userId, String orderId, String phone, String cardNo,
        String cardName, String idcard) throws Exception {
        logger.debug(">agreementPayBindCardValidateCode, orderId = {} , phone={} ", orderId, phone);

        String aesKey = StringUtil.randomAlphaNum(16);
        PrivateKey privateKey = RSA.getPrivateKey();
        PublicKey cerPubKey = RSA.getCerPublicKey();

        // 加密aesKey
        String encryptionKey = RSA.encryptToBase64(aesKey, cerPubKey, RSA.KEY_ALGORITHM);
        // 加密phone
        String phoneE = AES.encryptWithBase64(phone.getBytes(), aesKey.getBytes());
        String cardNoE = AES.encryptWithBase64(cardNo.getBytes(), aesKey.getBytes());
        String idcardE = AES.encryptWithBase64(idcard.getBytes(), aesKey.getBytes());

        AgreementPayBindCardValidateCodeVo vo =
            new AgreementPayBindCardValidateCodeVo(userId, orderId, cardNoE, cardName, phoneE, idcardE, encryptionKey);
        // 获取签名
        String sign = RSA.sign(vo.getSigned(), privateKey);

        logger.info("getSigned：" + vo.getSigned());
        logger.info("sign：" + sign);
        vo.setSign(sign);

        // 1. 调用接口
        AgreementPayBindCardValidateCodeRspVo rspVo = (AgreementPayBindCardValidateCodeRspVo)HeliUtil
            .post(vo.getRequestFormStr(), AgreementPayBindCardValidateCodeRspVo.class, vo.getP1_bizType());

        if (rspVo == null) {
            logger.info("<agreementPayBindCardValidateCode, return with error");
            throw new HeLiPayException();
        }

        // 2. 验证签名
        if (!RSA.verifySign(rspVo.getSigned(), rspVo.getSign(), cerPubKey)) {
            logger.info("<agreementPayBindCardValidateCode, return vo sign not equal");
            throw new HeLiPayException("jbb.xjl.error.exception.hlb.signError", "zh");
        }

        // 3. 处理逻辑
        checkRetCode(rspVo.getRt2_retCode(),rspVo.getRt3_retMsg(),rspVo.toString());
        logger.debug("<agreementPayBindCardValidateCode");

    }

    @Override
    public void quickPayBindCard(String orderId, String userId, String idcard, String cardName, String cardNo,
        String msgCode, String phone) throws Exception {

        logger.debug(">quickPayBindCard, orderId={},  userId = {} , idcard={}, username = {} , cardNo={} ", orderId,
            userId, idcard, cardName, cardNo);

        String aesKey = StringUtil.randomAlphaNum(16);
        logger.debug("aesKey：" + aesKey);
        PrivateKey privateKey = RSA.getPrivateKey();
        PublicKey cerPubKey = RSA.getCerPublicKey();

        // 加密aesKey
        String encryptionKey = RSA.encryptToBase64(aesKey, cerPubKey, RSA.KEY_ALGORITHM);
        // 加密phone
        String phoneE = AES.encryptWithBase64(phone.getBytes(), aesKey.getBytes());
        String cardNoE = AES.encryptWithBase64(cardNo.getBytes(), aesKey.getBytes());
        String idcardE = AES.encryptWithBase64(idcard.getBytes(), aesKey.getBytes());
        String msgCodeE = AES.encryptWithBase64(msgCode.getBytes(), aesKey.getBytes());

        QuickPayBindCardVo vo =
            new QuickPayBindCardVo(userId, orderId, cardName, idcardE, cardNoE, phoneE, msgCodeE, encryptionKey);
        // 获取签名
        String sign = RSA.sign(vo.getSigned(), privateKey);
        logger.info("sign：" + sign);
        vo.setSign(sign);

        // 1. 调用接口
        QuickPayBindCardRspVo rspVo = (QuickPayBindCardRspVo)HeliUtil.post(vo.getRequestFormStr(),
            QuickPayBindCardRspVo.class, vo.getP1_bizType());

        if (rspVo == null) {
            logger.info("<quickPayBindCard, return with error");
            throw new HeLiPayException();
        }

        // 2. 验证签名
        if (!RSA.verifySign(rspVo.getSigned(), rspVo.getSign(), cerPubKey)) {
            logger.info("<quickPayBindCard, return vo sign not equal");
            throw new HeLiPayException("jbb.xjl.error.exception.hlb.signError", "zh");
        }

        // 3. 处理逻辑
        checkRetCode(rspVo.getRt2_retCode(),rspVo.getRt3_retMsg(),rspVo.toString());
        if (rspVo.getRt7_bindStatus().equals("FAILED")) {
            throw new HeLiPayException("jbb.xjl.error.exception.hlb.bindError", "zh");
        }
        logger.debug("<quickPayBindCard");

    }

    @Override
    public void quickPayBindPayValidateCode(String bindId, String userId, String orderId, String amount, String phone)
        throws Exception {
        logger.debug(">quickPayBindPayValidateCode, bindId={},  orderId={},  userId = {} , amount={}, phone = {} ",
            bindId, orderId, userId, amount, phone);

        PublicKey cerPubKey = RSA.getCerPublicKey();

        String aesKey = StringUtil.randomAlphaNum(16);
        logger.debug("aesKey：" + aesKey);
        PrivateKey privateKey = RSA.getPrivateKey();
        PublicKey publicKey = RSA.getPublicKey();

        // 加密aesKey
        String encryptionKey = RSA.encryptToBase64(aesKey, publicKey, RSA.KEY_ALGORITHM);
        // 加密phone
        String phoneE = new String(AES.encrypt(phone.getBytes(), aesKey.getBytes()));

        QuickPayBindPayValidateCodeVo vo =
            new QuickPayBindPayValidateCodeVo(bindId, userId, orderId, amount, phoneE, encryptionKey);
        // 获取签名
        String sign = RSA.sign(vo.getSigned(), privateKey);
        logger.info("sign：" + sign);
        vo.setSign(sign);

        // 1. 调用接口
        QuickPayBindPayValidateCodeRspVo rspVo = (QuickPayBindPayValidateCodeRspVo)HeliUtil.post(vo,
            QuickPayBindPayValidateCodeRspVo.class, vo.getP1_bizType());

        if (rspVo == null) {
            logger.info("<quickPayBindPayValidateCode, return with error");
            return;
        }

        // 2. 验证签名
        if (!RSA.verifySign(rspVo.getSigned(), rspVo.getSign(), cerPubKey)) {
            logger.info("<quickPayBindPayValidateCode, return vo sign not equal");
            throw new HeLiPayException("jbb.xjl.error.exception.hlb.signError", "zh");
        }

        // 3. 处理逻辑

        logger.debug("<quickPayBindPayValidateCode");

    }

    @Override
    public void quickPayBindPay(String bindId, String userId, String orderId, String amount, String terminalId,
        String orderIp, String msgCode) throws Exception {
        logger.debug(">quickPayBindPay, bindId={}, orderId={},  userId = {} , amount={}, terminalId = {} , orderIp={} ",
            bindId, orderId, userId, amount, terminalId, orderIp);

        PublicKey cerPubKey = RSA.getCerPublicKey();
        String aesKey = StringUtil.randomAlphaNum(16);
        logger.debug("aesKey：" + aesKey);
        PrivateKey privateKey = RSA.getPrivateKey();
        PublicKey publicKey = RSA.getPublicKey();

        String serverCallbackUrl = PropertyManager.getProperty("jbb.pay.heli.callback.url");

        // 加密aesKey
        String encryptionKey = RSA.encryptToBase64(aesKey, publicKey, RSA.KEY_ALGORITHM);
        // 加密
        String msgCodeE = new String(AES.encrypt(msgCode.getBytes(), aesKey.getBytes()));

        //
        QuickPayBindPayVo vo = new QuickPayBindPayVo(bindId, userId, orderId, amount, terminalId, orderIp, msgCodeE,
            serverCallbackUrl, encryptionKey);
        // 获取签名
        String sign = RSA.sign(vo.getSigned(), privateKey);
        logger.info("sign：" + sign);
        vo.setSign(sign);

        // 1. 调用接口
        QuickPayBindPayRspVo rspVo =
            (QuickPayBindPayRspVo)HeliUtil.post(vo, QuickPayBindPayRspVo.class, vo.getP1_bizType());

        if (rspVo == null) {
            logger.info("<quickPayBindPay, return with error");
            return;
        }

        // 2. 验证签名
        if (!RSA.verifySign(rspVo.getSigned(), rspVo.getSign(), cerPubKey)) {
            logger.info("<quickPayBindPay, return vo sign not equal");
            throw new HeLiPayException("jbb.xjl.error.exception.hlb.signError", "zh");
        }

        // 3. 处理逻辑

        logger.debug("<quickPayBindPay");

    }

    @Override
    public void quickPayCallback() {
        // TODO Auto-generated method stub

    }

    @Override
    public void quickPayQuery(String orderId) {
        logger.debug(">quickPayQuery, orderId={} ", orderId);
        String aesKey = StringUtil.randomAlphaNum(16);
        logger.debug("aesKey：" + aesKey);
        PrivateKey privateKey = RSA.getPrivateKey();
        PublicKey cerPubKey = RSA.getCerPublicKey();

        QuickPayQueryVo vo = new QuickPayQueryVo(orderId);
        // 获取签名
        String sign = RSA.sign(vo.getSigned(), privateKey);
        logger.info("sign：" + sign);
        vo.setSign(sign);

        // 1. 调用接口
        QuickPayQueryRspVo rspVo = (QuickPayQueryRspVo)HeliUtil.post(vo, QuickPayQueryRspVo.class, vo.getP1_bizType());

        if (rspVo == null) {
            logger.info("<quickPayQuery, return with error");
            return;
        }

        // 2. 验证签名
        if (!RSA.verifySign(rspVo.getSigned(), rspVo.getSign(), cerPubKey)) {
            logger.info("<quickPayQuery, return vo sign not equal");
            throw new HeLiPayException("jbb.xjl.error.exception.hlb.signError", "zh");
        }

        // 3. 处理逻辑

        logger.debug("<quickPayBindPay");

    }

    @Override
    public void bankCardUnbind(String userId, String bindId, String orderId) throws Exception {
        logger.debug(">bankCardUnbind, bindId={}, orderId={},  userId = {} } ", bindId, orderId, userId);

        String aesKey = StringUtil.randomAlphaNum(16);
        logger.debug("aesKey：" + aesKey);
        PrivateKey privateKey = RSA.getPrivateKey();
        PublicKey cerPubKey = RSA.getCerPublicKey();

        //
        BankCardUnbindVo vo = new BankCardUnbindVo(bindId, userId, orderId);
        // 获取签名
        String sign = RSA.sign(vo.getSigned(), privateKey);
        logger.info("sign：" + sign);
        vo.setSign(sign);

        // 1. 调用接口
        BankCardUnbindRspVo rspVo =
            (BankCardUnbindRspVo)HeliUtil.post(vo, BankCardUnbindRspVo.class, vo.getP1_bizType());

        if (rspVo == null) {
            logger.info("<bankCardUnbind, return with error");
            return;
        }

        // 2. 验证签名
        if (!RSA.verifySign(rspVo.getSigned(), rspVo.getSign(), cerPubKey)) {
            logger.info("<bankCardUnbind, return vo sign not equal");
            throw new HeLiPayException("jbb.xjl.error.exception.hlb.signError", "zh");
        }

        // 3. 处理逻辑

        logger.debug("<bankCardUnbind");

    }

    @Override
    public void bankCardbindList(String userId, String bindId) throws Exception {
        logger.debug(">bankCardbindList, bindId={}, userId = {} } ", bindId, userId);

        String aesKey = StringUtil.randomAlphaNum(16);
        logger.debug("aesKey：" + aesKey);
        PrivateKey privateKey = RSA.getPrivateKey();
        PublicKey cerPubKey = RSA.getCerPublicKey();

        //
        BankCardbindListVo vo = new BankCardbindListVo(bindId, userId);
        // 获取签名
        String sign = RSA.sign(vo.getSigned(), privateKey);
        logger.info("sign：" + sign);
        vo.setSign(sign);

        // 1. 调用接口
        BankCardbindListRspVo rspVo =
            (BankCardbindListRspVo)HeliUtil.post(vo, BankCardbindListRspVo.class, vo.getP1_bizType());

        if (rspVo == null) {
            logger.info("<bankCardbindList, return with error");
            return;
        }

        // 2. 验证签名
        if (!RSA.verifySign(rspVo.getSigned(), rspVo.getSign(), cerPubKey)) {
            logger.info("<bankCardbindList, return vo sign not equal");
            throw new HeLiPayException("jbb.xjl.error.exception.hlb.signError", "zh");
        }

        // 3. 处理逻辑

        logger.debug("<bankCardbindList");

    }

    private void checkRetCode(String retCode,String msg,String response) {
        logger.info("<HeLiPayResponse, Response = "+response);
        if (!retCode.equals("0000")) {
            throw new HeLiPayException(msg);
        }
    }

}
