package com.jbb.mgt.boss;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.BossException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.HttpUtil;
import org.slf4j.LoggerFactory;

public class HttpRequestUtil {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    public static String sendJsonWithHttp(String surl, String json) throws Exception {
        URL url = new URL(surl);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        conn.setRequestMethod("POST");// 提交模式
        conn.setRequestProperty("Content-Length", json.getBytes().length + "");
        conn.setConnectTimeout(100000);// 连接超时单位毫秒 //
        conn.setReadTimeout(200000);// 读取超时 单位毫秒
        conn.setDoOutput(true);// 是否输入参数
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.connect();
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(json.getBytes());
        out.flush();
        out.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        conn.disconnect();
        return sb.toString();
    }

    final static String PROTOCOL_NAME = "https";

    public static String sendJsonWithHttps(String surl, String json) throws Exception {
        HttpUtil.HttpResponse response
            = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, surl, HttpUtil.CONTENT_TYPE_JSON, json, null);
        String repString = "";
        if (response.getResponseCode() == HttpUtil.STATUS_OK) {
            byte[] byteArray = response.getResponseBody();
            if (byteArray == null || byteArray.length < 1) {
                throw new IllegalArgumentException("this byteArray must not be null or empty");
            }else{
                repString = new String(byteArray, "utf-8");
            }
        }
        return repString;
    }

    /**
     *
     * @param jsonString
     * @param functionCode
     * @throws Exception
     */
    public static String postHttpsRequest(String jsonString, String functionCode) throws Exception {

        String url = PropertyManager.getProperty("xjl.nwjr.sfUrl");
        String code = PropertyManager.getProperty("xjl.nwjr.orgCode");
        String name = PropertyManager.getProperty("xjl.nwjr.userName");
        String password = PropertyManager.getProperty("xjl.nwjr.userPassword");
        String privateKey = PropertyManager.getProperty("xjl.nwjr.privateKey");
        String publicKey = PropertyManager.getProperty("xjl.nwjr.publicKey");
        String encryKey = PropertyManager.getProperty("xjl.nwjr.encryKey");
        Map<String, Object> root = new HashMap<String, Object>();
        Map<String, Object> header = new HashMap<String, Object>();
        Map<String, Object> securityInfo = new HashMap<String, Object>();
        header.put("orgCode", code);
        header.put("transNo", HeliUtil.generateOrderId());
        header.put("transDate", DateUtil.getSystemTimeString());
        header.put("userName", name);
        header.put("userPassword", EncryptUtil.md5(password));
        header.put("functionCode", functionCode);
        String headerStr = JSONObject.toJSONString(header);

        // 使用pcks8编码格式的私钥
        String sigValue = RSA.sign(headerStr, privateKey);
        securityInfo.put("signatureValue", sigValue);

        byte[] encBusiData = ThreeDes.encryptMode(encryKey.getBytes(), jsonString.getBytes("UTF-8"));

        root.put("header", headerStr);
        root.put("busiData", Base64.getBase64ByByteArray(encBusiData));
        root.put("securityInfo", securityInfo);
        String message = JSONObject.toJSONString(root);

        String res = HttpRequestUtil.sendJsonWithHttps(url, message);

        logger.info("响应Message" + res);
        JSONObject msgJSON = JSONObject.parseObject(res);
        String head = msgJSON.getString("header");
        String retCode = JSONObject.parseObject(head).getString("rtCode");
        if (!retCode.equals("E0000000")) {
            logger.info("Boss error rtCode = "+retCode);
            throw new BossException("jbb.xjl.error.exception.boss.error");
        }
        // 一定要验证签名的合法性！！！！！！！！
        String securityInfo1 = msgJSON.getString("securityInfo");
        String signatureValue = JSONObject.parseObject(securityInfo1).getString("signatureValue");
        boolean verifyFlag = RSA.verify(msgJSON.getString("header"), signatureValue, publicKey);
        if (verifyFlag == true) {
            byte[] b64 = Base64.getFormBase64ByString(msgJSON.getString("busiData"));
            byte[] busiData = ThreeDes.decryptMode(encryKey.getBytes(), b64);
            return new String(busiData, "UTF-8");
        } else {
            logger.info("验签失败");
            throw new BossException("jbb.xjl.error.exception.boss.error");
        }
    }

    /**
     *
     * @param jsonString
     * @param functionCode
     * @throws Exception
     */
    public static String postBossHttpsRequest(String jsonString, String functionCode) throws Exception {

        String url = PropertyManager.getProperty("xjl.nwjr.sfUrl");
        String code = PropertyManager.getProperty("xjl.nwjr.orgCode");
        String name = PropertyManager.getProperty("xjl.nwjr.userName");
        String password = PropertyManager.getProperty("xjl.nwjr.userPassword");
        String privateKey = PropertyManager.getProperty("xjl.nwjr.privateKey");
        String publicKey = PropertyManager.getProperty("xjl.nwjr.publicKey");
        String encryKey = PropertyManager.getProperty("xjl.nwjr.encryKey");
        Map<String, Object> root = new HashMap<String, Object>();
        Map<String, Object> header = new HashMap<String, Object>();
        Map<String, Object> securityInfo = new HashMap<String, Object>();
        header.put("orgCode", code);
        header.put("transNo", HeliUtil.generateOrderId());
        header.put("transDate", DateUtil.getSystemTimeString());
        header.put("userName", name);
        header.put("userPassword", EncryptUtil.md5(password));
        header.put("functionCode", functionCode);
        String headerStr = JSONObject.toJSONString(header);

        // 使用pcks8编码格式的私钥
        String sigValue = RSA.sign(headerStr, privateKey);
        securityInfo.put("signatureValue", sigValue);

        byte[] encBusiData = ThreeDes.encryptMode("AD905@!QLF-D25WEDA5!@#$%".getBytes(), jsonString.getBytes("UTF-8"));

        root.put("header", headerStr);
        root.put("busiData", Base64.getBase64ByByteArray(encBusiData));
        root.put("securityInfo", securityInfo);
        String message = JSONObject.toJSONString(root);
        System.out.println("jsonString " + jsonString);
        System.out.println("请求" + message);

        String res = HttpRequestUtil.sendJsonWithHttps(url, message);

        System.out.println("响应Message" + res);
        JSONObject msgJSON = JSONObject.parseObject(res);
        String head = msgJSON.getString("header");
        if (!JSONObject.parseObject(head).getString("rtCode").equals("E0000000")) {
            System.out.println("消息返回失败");
            return null;
        }
        if (msgJSON.getIntValue("code") != 0) {// 0:成功,1:请求参数错误,2:获取数据超时,3:数据异常,4:服务忙，稍后重试,5:其它错误
            String codeDes = msgJSON.getString("code_desc");
            System.out.println("消息返回失败");
            return null;
        }
        // 一定要验证签名的合法性！！！！！！！！
        String securityInfo1 = msgJSON.getString("securityInfo");
        String signatureValue = JSONObject.parseObject(securityInfo1).getString("signatureValue");
        boolean verifyFlag = RSA.verify(msgJSON.getString("header"), signatureValue, publicKey);
        if (verifyFlag == true) {
            System.out.println("验签成功");
            byte[] b64 = Base64.getFormBase64ByString(msgJSON.getString("busiData"));
            byte[] busiData = ThreeDes.decryptMode(encryKey.getBytes(), b64);
            System.out.println("响应BusiData明文：" + new String(busiData, "UTF-8"));
            return new String(busiData, "UTF-8");
        } else {
            System.out.println("验签失败");
            return null;
        }
    }
}
