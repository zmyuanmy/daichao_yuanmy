package com.jbb.mgt.changjiepay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jbb.mgt.changjiepay.contants.ChangJiePayConstants;
import com.jbb.mgt.changjiepay.service.ChangJieQuickPayService;
import com.jbb.mgt.changjiepay.util.ChangJieUtil;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.HeLiPayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("ChangJieQuickPayService")
public class ChangJieQuickPayServiceImpl implements ChangJieQuickPayService {

    private static Logger logger = LoggerFactory.getLogger(ChangJieQuickPayServiceImpl.class);

    private static String charset = "UTF-8";

    @Override
    public void bizApiAuthReq(String userId, String orderId, String idcard, String cardNo, String phone,
        String userName, String cardCvn2, String cardExprDt) {
        logger.debug(">bizApiAuthReq , userId={}, orderId={}, idcard={}, cardNo={}, phone={}, userName={}" + userId
            + orderId + idcard + cardNo + phone + userName);

        String publicKey = PropertyManager.getProperty("jbb.pay.changjie.public.key");
        String privateKey = PropertyManager.getProperty("jbb.pay.changjie.private.key");
        String customerNumber = PropertyManager.getProperty("jbb.pay.changjie.customer.number");
        Map<String, String> origMap = new HashMap<String, String>();
        origMap = ChangJieUtil.setCommonMap(origMap);
        // 2.1 鉴权绑卡 api 业务参数
        origMap.put("Service", ChangJiePayConstants.SERVICE_NMG_BIZ_API_AUTH_REQ);// 鉴权绑卡的接口名(商户采集方式)；银行采集方式更换接口名称为：nmg_canal_api_auth_req
        origMap.put("TrxId", orderId);// 订单号
        origMap.put("ExpiredTime", "90m");// 订单有效期
        origMap.put("MerUserId", userId);// 用户标识
        origMap.put("MerchantNo", customerNumber);// 商戶号
        origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡;）
        origMap.put("BkAcctNo", ChangJieUtil.encrypt(cardNo, publicKey, charset));// 卡号
        origMap.put("IDTp", "01");// 证件类型 （目前只支持身份证 01：身份证）
        origMap.put("IDNo", ChangJieUtil.encrypt(idcard, publicKey, charset));// 证件号
        origMap.put("CstmrNm", ChangJieUtil.encrypt(userName, publicKey, charset));// 持卡人姓名
        origMap.put("MobNo", ChangJieUtil.encrypt(phone, publicKey, charset));// 银行预留手机号
        origMap.put("SmsFlag", "1");
        origMap.put("Extension", "");
        String rspString = ChangJieUtil.gatewayPost(origMap, charset, privateKey);
        if (rspString == null) {
            logger.info("<bizApiAuthReq, return with error");
            throw new HeLiPayException();
        }
        checkRetCode(rspString);
        logger.debug("<bizApiAuthReq");
    }

    @Override
    public void apiAuthSms(String orderId, String msgCode) {
        logger.debug(">apiAuthSms , orderId={}, msgCode={}" + orderId + msgCode);

        String privateKey = PropertyManager.getProperty("jbb.pay.changjie.private.key");
        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap = ChangJieUtil.setCommonMap(origMap);
        origMap.put("Service", ChangJiePayConstants.SERVICE_NMG_API_AUTH_SMS);// 鉴权绑卡确认的接口名
        // 2.1 鉴权绑卡 业务参数
        String trxId = HeliUtil.generateOrderId();
        origMap.put("TrxId", trxId);// 订单号
        origMap.put("OriAuthTrxId", orderId);// 原鉴权绑卡订单号
        origMap.put("SmsCode", msgCode);// 鉴权短信验证码
        String rspString = ChangJieUtil.gatewayPost(origMap, charset, privateKey);
        if (rspString == null) {
            logger.info("<bizApiAuthReq, return with error");
            throw new HeLiPayException();
        }
        checkRetCode(rspString);
        logger.debug("<apiAuthSms");
    }

    @Override
    public void apiQuickPaymentResend(String orderId) {
        logger.debug(">apiQuickPaymentResend, orderId={}" + orderId);

        String privateKey = PropertyManager.getProperty("jbb.pay.changjie.private.key");
        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap = ChangJieUtil.setCommonMap(origMap);
        // 2.2 业务参数
        origMap.put("Service", ChangJiePayConstants.SERVICE_NMG_API_QUICK_PAYMENT_RESEND);
        String trxId = HeliUtil.generateOrderId();
        origMap.put("TrxId", trxId);// 订单号
        origMap.put("OriTrxId", orderId);// 原业务请求订单号
        origMap.put("TradeType", "auth_order");// 原业务订单类型
        String rspString = ChangJieUtil.gatewayPost(origMap, charset, privateKey);
        if (rspString == null) {
            logger.info("<bizApiAuthReq, return with error");
            throw new HeLiPayException();
        }
        checkRetCode(rspString);
        logger.debug("<apiQuickPaymentResend");
    }

    @Override
    public void apiAuthInfoQry(String userId) {
        logger.debug(">apiAuthInfoQry, userId={}" + userId);

        String privateKey = PropertyManager.getProperty("jbb.pay.changjie.private.key");
        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap = ChangJieUtil.setCommonMap(origMap);
        origMap.put("Service", ChangJiePayConstants.SERVICE_NMG_API_AUTH_INFO_QRY);// 用户鉴权绑卡信息查询接口名
        // 2.2 业务参数
        String orderId = HeliUtil.generateOrderId();
        origMap.put("TrxId", orderId);// 商户网站唯一订单号
        origMap.put("MerUserId", userId); // 用户标识
        origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡）
        String rspString = ChangJieUtil.gatewayPost(origMap, charset, privateKey);
        if (rspString == null) {
            logger.info("<bizApiAuthReq, return with error");
            throw new HeLiPayException();
        }
        checkRetCode(rspString);
        logger.debug("<apiAuthInfoQry");
    }

    @Override
    public void bizApiQuickPayment(String userId, String cardNo, String amount, String orderId) {
        logger.debug(">bizApiQuickPayment, userId={}, cardNo={}" + userId + cardNo);
        String privateKey = PropertyManager.getProperty("jbb.pay.changjie.private.key");
        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        String customerNumber = PropertyManager.getProperty("jbb.pay.changjie.customer.number");
        String goodsName = PropertyManager.getProperty("jbb.pay.changjie.goods.name");

        origMap = ChangJieUtil.setCommonMap(origMap);
        origMap.put("Service", ChangJiePayConstants.SERVICE_NMG_BIZ_API_QUICK_PAYMENT);// 支付的接口名
        // 2.2 业务参数
        origMap.put("TrxId", orderId);// 订单号
        origMap.put("OrdrName", goodsName);// 商品名称
        origMap.put("MerUserId", userId);// 用户标识
        origMap.put("SellerId", customerNumber);// 子账户号
        origMap.put("SubMerchantNo", "");// 子商户号
        origMap.put("ExpiredTime", "40m");// 订单有效期
        origMap.put("CardBegin", cardNo.substring(0, 6));// 卡号前6位
        origMap.put("CardEnd", cardNo.substring(cardNo.length() - 4, cardNo.length()));// 卡号后4位
        origMap.put("TrxAmt", amount);// 交易金额
        origMap.put("TradeType", "11");// 交易类型
        origMap.put("SmsFlag", "1");
        String notifyUrl = PropertyManager.getProperty("jbb.pay.changjie.quickPayCallBack.url");
        origMap.put("NotifyUrl", notifyUrl);
        String rspString = ChangJieUtil.gatewayPost(origMap, charset, privateKey);
        if (rspString == null) {
            logger.info("<bizApiAuthReq, return with error");
            throw new HeLiPayException();
        }
        checkRetCode(rspString);

        logger.debug("<bizApiQuickPayment");
    }

    @Override
    public void apiQuickPaymentSmsConfirm(String orderId, String msgCode) {
        logger.debug(">apiQuickPaymentSmsConfirm , orderId={}, msgCode={}" + orderId + msgCode);
        String privateKey = PropertyManager.getProperty("jbb.pay.changjie.private.key");
        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap = ChangJieUtil.setCommonMap(origMap);
        origMap.put("Service", ChangJiePayConstants.SERVICE_NMG_API_QUICK_PAYMENT_SMSCONFIRM);// 请求的接口名称
        // 2.2 业务参数
        String TrxId = HeliUtil.generateOrderId();
        origMap.put("TrxId", TrxId);// 订单号

        // origMap.put("TrxId", "101149785980144593760");// 订单号
        origMap.put("OriPayTrxId", orderId);// 原有支付请求订单号
        origMap.put("SmsCode", msgCode);// 短信验证码
        String rspString = ChangJieUtil.gatewayPost(origMap, charset, privateKey);
        if (rspString == null) {
            logger.info("<bizApiAuthReq, return with error");
            throw new HeLiPayException();
        }
        checkRetCode(rspString);
        logger.debug("<apiQuickPaymentSmsConfirm");
    }

    @Override
    public void apiQuickPayment(String userId,String idCard,String cardNo,String amount,String orderId,String userName,String phoneNumber) {
        logger.debug(">apiQuickPayment, userId={}, cardNo={}" + userId + cardNo);
        String privateKey = PropertyManager.getProperty("jbb.pay.changjie.private.key");
        String publicKey = PropertyManager.getProperty("jbb.pay.changjie.public.key");

        // 2.1 基本参数
        String customerNumber = PropertyManager.getProperty("jbb.pay.changjie.customer.number");
        String goodsName = PropertyManager.getProperty("jbb.pay.changjie.goods.name");

        // 2.1 基本参数
        Map<String, String> origMap = new HashMap<String, String>();
        origMap = ChangJieUtil.setCommonMap(origMap);
        origMap.put("Service", ChangJiePayConstants.SERVICE_NMG_ZFT_API_QUICK_PAYMENT);// 支付接口名称
        // 2.2 业务参数
        origMap.put("TrxId", orderId);// 订单号
        origMap.put("OrdrName", goodsName);// 商品名称
        origMap.put("MerUserId", userId);// 用户标识
        origMap.put("SellerId", customerNumber);// 子账户号
        origMap.put("SubMerchantNo", customerNumber);// 子商户号
        origMap.put("ExpiredTime", "40m");// 订单有效期
        origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡;）
        origMap.put("BkAcctNo", ChangJieUtil.encrypt(cardNo, publicKey, charset));// 卡号
        origMap.put("IDTp", "01");// 证件类型 （目前只支持身份证 01：身份证）
        origMap.put("IDNo", ChangJieUtil.encrypt(idCard, publicKey, charset));// 证件号
        origMap.put("CstmrNm", ChangJieUtil.encrypt(userName, publicKey, charset));// 持卡人姓名
        origMap.put("MobNo", ChangJieUtil.encrypt(phoneNumber, publicKey, charset));// 银行预留手机号
        //origMap.put("EnsureAmount", "1");// 担保金额
        origMap.put("TrxAmt", amount);// 交易金额
        origMap.put("TradeType", "11");// 交易类型
        origMap.put("SmsFlag", "1");//短信发送标识
        String notifyUrl = PropertyManager.getProperty("jbb.pay.changjie.quickPayCallBack.url");
        origMap.put("NotifyUrl", notifyUrl);
        String rspString = ChangJieUtil.gatewayPost(origMap, charset, privateKey);
        if (rspString == null) {
            logger.info("<bizApiAuthReq, return with error");
            throw new HeLiPayException();
        }
        checkRetCode(rspString);

        logger.debug("<bizApiQuickPayment");
    }

    private void checkRetCode(String rspString) {
        JSONObject jsonObject = JSON.parseObject(rspString);
        String acceptStatus = "";
        if (jsonObject != null) {
            acceptStatus = jsonObject.getString("AcceptStatus");
        }
        logger.info("<HeLiPayResponse, Response = " + rspString);
        if (!acceptStatus.equals("S")) {
            String AppRetMsg = jsonObject.getString("AppRetMsg");
            throw new HeLiPayException("支付接口调用失败 原因:" + AppRetMsg);
        }
    }

}
