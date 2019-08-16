package com.jbb.mgt.helipay.util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.HttpUtil.HttpResponse;
import com.jbb.server.common.util.StringUtil;

public class HeliUtil {
    private static Logger logger = LoggerFactory.getLogger(HeliUtil.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    private static DecimalFormat df = new DecimalFormat("#.00");

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> Object post(Object formData, Class<T> rspClass, String methodName) {

        logger.info("heli request , request = " + formData);
        String url = PropertyManager.getProperty("jbb.pay.heli.server.url");

        String[][] requestProperties = {};

        try {
            HttpResponse response = HttpUtil.sendDataViaHTTPRepeat(HttpUtil.HTTP_POST, url, HttpUtil.CONTENT_TYPE_X_WWW_FORM_URLENCODED,
                formData, requestProperties, methodName);
            
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                T rspVo = mapper.readValue(new String(response.getResponseBody()), rspClass);
                return rspVo;

            } else {
                logger.info("heli request " + methodName + ",  response code =  " + response.getResponseCode());
                return null;
            }

        } catch (IOException e) {
            logger.error("heli request " + methodName + ",  response with error, " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    
    //transfer
    public static <T> Object transferPost(Object formData, Class<T> rspClass, String methodName) {

        logger.info("heli request , request = " + formData);
        String url = PropertyManager.getProperty("jbb.pay.heli.transfer.url");

        String[][] requestProperties = {};

        try {
            HttpResponse response = HttpUtil.sendDataViaHTTPRepeat(HttpUtil.HTTP_POST, url, HttpUtil.CONTENT_TYPE_X_WWW_FORM_URLENCODED,
                formData, requestProperties, methodName);
            
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                T rspVo = mapper.readValue(new String(response.getResponseBody()), rspClass);
                return rspVo;

            } else {
                logger.info("heli request " + methodName + ",  response code =  " + response.getResponseCode());
                return null;
            }

        } catch (IOException e) {
            logger.error("heli request " + methodName + ",  response with error, " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    

    public static synchronized String generateOrderId() {
        return sdf.format(DateUtil.getCurrentTimeStamp()) + StringUtil.getRandomnum(3);
    }

    public static String getCurrentTs() {
        return sdf.format(DateUtil.getCurrentTimeStamp());
    }

    public static String formatAmount(double amount) {
        return df.format(amount);
    }

}
