package com.jbb.mgt.helipay.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.jbb.mgt.helipay.constants.HeliPayConstants;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;

/**
 * 首次支付 请求参数 实体类
 *
 * @author wyq
 * @date 2018/8/29 19:18
 */
public class QuickPayCreateOrderVo {
    private String P1_bizType;
    private String P2_customerNumber;
    private String P3_userId;
    private String P4_orderId;
    private String P5_timestamp;
    private String P6_payerName;
    private String P7_idCardType;
    private String P8_idCardNo;
    private String P9_cardNo;
    private String P10_year;
    private String P11_month;
    private String P12_cvv2;
    private String P13_phone;
    private String P14_currency;
    private String P15_orderAmount;
    private String P16_goodsName;
    private String P17_goodsDesc;
    private String P18_terminalType;
    private String P19_terminalId;
    private String P20_orderIp;
    private String P21_period;
    private String P22_periodUnit;
    private String P23_serverCallbackUrl;
    private String P24_isEncrypt;
    private String signatureType;
    private String encryptionKey;
    private String sign;

    public QuickPayCreateOrderVo() {
        super();
    }

    public QuickPayCreateOrderVo(String userId, String orderId, String cardName, String idcard, String cardNo,
        String phone, String amount, String orderIp, String terminalId, String serverCallbackUrl,
        String encryptionKey) {
        P1_bizType = HeliPayConstants.BIZ_TYPE_QUICK_PAY_CREATE_ORDER;
        P2_customerNumber = PropertyManager.getProperty("jbb.pay.heli.customer.number");
        P3_userId = userId;
        P4_orderId = orderId;
        P5_timestamp = HeliUtil.getCurrentTs();;
        P6_payerName = cardName;
        P7_idCardType = "IDCARD";
        P8_idCardNo = idcard;
        P9_cardNo = cardNo;
        P13_phone = phone;
        P14_currency = "CNY";
        P15_orderAmount = amount;
        P16_goodsName = PropertyManager.getProperty("jbb.pay.heli.goods.name");
        P17_goodsDesc = PropertyManager.getProperty("jbb.pay.heli.goods.desc");
        P18_terminalType = "IMEI";
        P19_terminalId = terminalId;
        P20_orderIp = orderIp;
        P23_serverCallbackUrl = serverCallbackUrl;
        P24_isEncrypt = "TRUE";
        this.signatureType = "MD5WITHRSA";
        this.encryptionKey = encryptionKey;

    }

    public String getSigned() {
        StringBuffer sb = new StringBuffer();
        sb.append("&");
        sb.append(this.getP1_bizType() == null ? "" : this.getP1_bizType());
        sb.append("&");
        sb.append(this.getP2_customerNumber() == null ? "" : this.getP2_customerNumber());
        sb.append("&");
        sb.append(this.getP3_userId() == null ? "" : this.getP3_userId());
        sb.append("&");
        sb.append(this.getP4_orderId() == null ? "" : this.getP4_orderId());
        sb.append("&");
        sb.append(this.getP5_timestamp() == null ? "" : this.getP5_timestamp());
        sb.append("&");
        sb.append(this.getP6_payerName() == null ? "" : this.getP6_payerName());
        sb.append("&");
        sb.append(this.getP7_idCardType() == null ? "" : this.getP7_idCardType());
        sb.append("&");
        sb.append(this.getP8_idCardNo() == null ? "" : this.getP8_idCardNo());
        sb.append("&");
        sb.append(this.getP9_cardNo() == null ? "" : this.getP9_cardNo());
        sb.append("&");
        sb.append(this.getP10_year() == null ? "" : this.getP10_year());
        sb.append("&");
        sb.append(this.getP11_month() == null ? "" : this.getP11_month());
        sb.append("&");
        sb.append(this.getP12_cvv2() == null ? "" : this.getP12_cvv2());
        sb.append("&");
        sb.append(this.getP13_phone() == null ? "" : this.getP13_phone());
        sb.append("&");
        sb.append(this.getP14_currency() == null ? "" : this.getP14_currency());
        sb.append("&");
        sb.append(this.getP15_orderAmount() == null ? "" : this.getP15_orderAmount());
        sb.append("&");
        sb.append(this.getP16_goodsName() == null ? "" : this.getP16_goodsName());
        sb.append("&");
        sb.append(this.getP17_goodsDesc() == null ? "" : this.getP17_goodsDesc());
        sb.append("&");
        sb.append(this.getP18_terminalType() == null ? "" : this.getP18_terminalType());
        sb.append("&");
        sb.append(this.getP19_terminalId() == null ? "" : this.getP19_terminalId());
        sb.append("&");
        sb.append(this.getP20_orderIp() == null ? "" : this.getP20_orderIp());
        sb.append("&");
        sb.append(this.getP21_period() == null ? "" : this.getP21_period());
        sb.append("&");
        sb.append(this.getP22_periodUnit() == null ? "" : this.getP22_periodUnit());
        sb.append("&");
        sb.append(this.getP23_serverCallbackUrl() == null ? "" : this.getP23_serverCallbackUrl());
        return sb.toString();
    }
    
    public String getRequestFormStr() throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        sb.append("P1_bizType=");
        sb.append(this.getP1_bizType() == null ? "" : this.getP1_bizType());
        sb.append("&P2_customerNumber=");
        sb.append(this.getP2_customerNumber() == null ? "" : this.getP2_customerNumber());
        sb.append("&P3_userId=");
        sb.append(this.getP3_userId() == null ? "" : this.getP3_userId());
        sb.append("&P4_orderId=");
        sb.append(this.getP4_orderId() == null ? "" : this.getP4_orderId());
        sb.append("&P5_timestamp=");
        sb.append(this.getP5_timestamp() == null ? "" : this.getP5_timestamp());
        sb.append("&P6_payerName=");
        sb.append(this.getP6_payerName() == null ? "" :  URLEncoder.encode(this.getP6_payerName(),"UTF-8"));
        sb.append("&P7_idCardType=");
        sb.append(this.getP7_idCardType() == null ? "" : this.getP7_idCardType());
        sb.append("&P8_idCardNo=");
        sb.append(this.getP8_idCardNo() == null ? "" : URLEncoder.encode(this.getP8_idCardNo(),"UTF-8"));
        sb.append("&P9_cardNo=");
        sb.append(this.getP9_cardNo() == null ? "" : URLEncoder.encode(this.getP9_cardNo(),"UTF-8"));
        sb.append("&P10_year=");
        sb.append(this.getP10_year() == null ? "" : URLEncoder.encode(this.getP10_year(),"UTF-8"));
        sb.append("&P11_month=");
        sb.append(this.getP11_month() == null ? "" : URLEncoder.encode(this.getP11_month(),"UTF-8"));
        sb.append("&P12_cvv2=");
        sb.append(this.getP12_cvv2() == null ? "" : URLEncoder.encode(this.getP12_cvv2(),"UTF-8"));
        sb.append("&P13_phone=");
        sb.append(this.getP13_phone() == null ? "" : URLEncoder.encode(this.getP13_phone(),"UTF-8"));
        sb.append("&P14_currency=");
        sb.append(this.getP14_currency() == null ? "" : this.getP14_currency());
        sb.append("&P15_orderAmount=");
        sb.append(this.getP15_orderAmount() == null ? "" : URLEncoder.encode(this.getP15_orderAmount(),"UTF-8"));
        sb.append("&P16_goodsName=");
        sb.append(this.getP16_goodsName() == null ? "" : URLEncoder.encode(this.getP16_goodsName(),"UTF-8"));
        sb.append("&P17_goodsDesc=");
        sb.append(this.getP17_goodsDesc() == null ? "" : URLEncoder.encode(this.getP17_goodsDesc(),"UTF-8"));
        sb.append("&P18_terminalType=");
        sb.append(this.getP18_terminalType() == null ? "" : this.getP18_terminalType());
        sb.append("&P19_terminalId=");
        sb.append(this.getP19_terminalId() == null ? "" : URLEncoder.encode(this.getP19_terminalId(),"UTF-8"));
        sb.append("&P20_orderIp=");
        sb.append(this.getP20_orderIp() == null ? "" : URLEncoder.encode(this.getP20_orderIp(),"UTF-8"));
        sb.append("&P21_period=");
        sb.append(this.getP21_period() == null ? "" : this.getP21_period());
        sb.append("&P22_periodUnit=");
        sb.append(this.getP22_periodUnit() == null ? "" : this.getP22_periodUnit());
        sb.append("&P23_serverCallbackUrl=");
        sb.append(this.getP23_serverCallbackUrl() == null ? "" : URLEncoder.encode(this.getP23_serverCallbackUrl(),"UTF-8"));
        sb.append("&P24_isEncrypt=");
        sb.append(this.getP24_isEncrypt() == null ? "" : this.getP24_isEncrypt());
        sb.append("&signatureType=");
        sb.append(this.getSignatureType() == null ? "" : this.getSignatureType());
        sb.append("&encryptionKey=");
        sb.append(this.getEncryptionKey() == null ? "" :  URLEncoder.encode(this.getEncryptionKey(),"UTF-8"));
        sb.append("&sign=");
        sb.append(this.getSign() == null ? "" : URLEncoder.encode(this.getSign(),"UTF-8"));
        return sb.toString();
    }

    public String getP1_bizType() {
        return P1_bizType;
    }

    public void setP1_bizType(String p1_bizType) {
        P1_bizType = p1_bizType;
    }

    public String getP2_customerNumber() {
        return P2_customerNumber;
    }

    public void setP2_customerNumber(String p2_customerNumber) {
        P2_customerNumber = p2_customerNumber;
    }

    public String getP3_userId() {
        return P3_userId;
    }

    public void setP3_userId(String p3_userId) {
        P3_userId = p3_userId;
    }

    public String getP4_orderId() {
        return P4_orderId;
    }

    public void setP4_orderId(String p4_orderId) {
        P4_orderId = p4_orderId;
    }

    public String getP5_timestamp() {
        return P5_timestamp;
    }

    public void setP5_timestamp(String p5_timestamp) {
        P5_timestamp = p5_timestamp;
    }

    public String getP6_payerName() {
        return P6_payerName;
    }

    public void setP6_payerName(String p6_payerName) {
        P6_payerName = p6_payerName;
    }

    public String getP7_idCardType() {
        return P7_idCardType;
    }

    public void setP7_idCardType(String p7_idCardType) {
        P7_idCardType = p7_idCardType;
    }

    public String getP8_idCardNo() {
        return P8_idCardNo;
    }

    public void setP8_idCardNo(String p8_idCardNo) {
        P8_idCardNo = p8_idCardNo;
    }

    public String getP9_cardNo() {
        return P9_cardNo;
    }

    public void setP9_cardNo(String p9_cardNo) {
        P9_cardNo = p9_cardNo;
    }

    public String getP10_year() {
        return P10_year;
    }

    public void setP10_year(String p10_year) {
        P10_year = p10_year;
    }

    public String getP11_month() {
        return P11_month;
    }

    public void setP11_month(String p11_month) {
        P11_month = p11_month;
    }

    public String getP12_cvv2() {
        return P12_cvv2;
    }

    public void setP12_cvv2(String p12_cvv2) {
        P12_cvv2 = p12_cvv2;
    }

    public String getP13_phone() {
        return P13_phone;
    }

    public void setP13_phone(String p13_phone) {
        P13_phone = p13_phone;
    }

    public String getP14_currency() {
        return P14_currency;
    }

    public void setP14_currency(String p14_currency) {
        P14_currency = p14_currency;
    }

    public String getP15_orderAmount() {
        return P15_orderAmount;
    }

    public void setP15_orderAmount(String p15_orderAmount) {
        P15_orderAmount = p15_orderAmount;
    }

    public String getP16_goodsName() {
        return P16_goodsName;
    }

    public void setP16_goodsName(String p16_goodsName) {
        P16_goodsName = p16_goodsName;
    }

    public String getP17_goodsDesc() {
        return P17_goodsDesc;
    }

    public void setP17_goodsDesc(String p17_goodsDesc) {
        P17_goodsDesc = p17_goodsDesc;
    }

    public String getP18_terminalType() {
        return P18_terminalType;
    }

    public void setP18_terminalType(String p18_terminalType) {
        P18_terminalType = p18_terminalType;
    }

    public String getP19_terminalId() {
        return P19_terminalId;
    }

    public void setP19_terminalId(String p19_terminalId) {
        P19_terminalId = p19_terminalId;
    }

    public String getP20_orderIp() {
        return P20_orderIp;
    }

    public void setP20_orderIp(String p20_orderIp) {
        P20_orderIp = p20_orderIp;
    }

    public String getP21_period() {
        return P21_period;
    }

    public void setP21_period(String p21_period) {
        P21_period = p21_period;
    }

    public String getP22_periodUnit() {
        return P22_periodUnit;
    }

    public void setP22_periodUnit(String p22_periodUnit) {
        P22_periodUnit = p22_periodUnit;
    }

    public String getP23_serverCallbackUrl() {
        return P23_serverCallbackUrl;
    }

    public void setP23_serverCallbackUrl(String p23_serverCallbackUrl) {
        P23_serverCallbackUrl = p23_serverCallbackUrl;
    }

    public String getP24_isEncrypt() {
        return P24_isEncrypt;
    }

    public void setP24_isEncrypt(String p24_isEncrypt) {
        P24_isEncrypt = p24_isEncrypt;
    }

    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "QuickPayCreateOrderVo{" + "P1_bizType='" + P1_bizType + '\'' + ", P2_customerNumber='"
            + P2_customerNumber + '\'' + ", P3_userId='" + P3_userId + '\'' + ", P4_orderId='" + P4_orderId + '\''
            + ", P5_timestamp='" + P5_timestamp + '\'' + ", P6_payerName='" + P6_payerName + '\'' + ", P7_idCardType='"
            + P7_idCardType + '\'' + ", P8_idCardNo='" + P8_idCardNo + '\'' + ", P9_cardNo='" + P9_cardNo + '\''
            + ", P10_year='" + P10_year + '\'' + ", P11_month='" + P11_month + '\'' + ", P12_cvv2='" + P12_cvv2 + '\''
            + ", P13_phone='" + P13_phone + '\'' + ", P14_currency='" + P14_currency + '\'' + ", P15_orderAmount='"
            + P15_orderAmount + '\'' + ", P16_goodsName='" + P16_goodsName + '\'' + ", P17_goodsDesc='" + P17_goodsDesc
            + '\'' + ", P18_terminalType='" + P18_terminalType + '\'' + ", P19_terminalId='" + P19_terminalId + '\''
            + ", P20_orderIp='" + P20_orderIp + '\'' + ", P21_period='" + P21_period + '\'' + ", P22_periodUnit='"
            + P22_periodUnit + '\'' + ", P23_serverCallbackUrl='" + P23_serverCallbackUrl + '\'' + ", P24_isEncrypt='"
            + P24_isEncrypt + '\'' + ", signatureType='" + signatureType + '\'' + ", encryptionKey='" + encryptionKey
            + '\'' + ", sign='" + sign + '\'' + '}';
    }
}
