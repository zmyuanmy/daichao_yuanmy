package com.jbb.mgt.core.service.impl;

import java.io.IOException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.mgt.core.domain.FlashsdkLoginRsp;
import com.jbb.mgt.core.service.FlashsdkLoginService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.HttpUtil.HttpResponse;

@Service("FlashsdkLoginService")
public class FlashsdkLoginServiceImpl implements FlashsdkLoginService {
    private static Logger logger = LoggerFactory.getLogger(FlashsdkLoginServiceImpl.class);
    public static final String YIDONG = "CMCC";
    public static final String LIANTONG = "CUCC";
    public static final String DIANXIN = "CTCC";
    
    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public String flashsdkLogin(String appId, String accessToken, String telecom, String timestamp, String randoms,
        String sign, String version, String device, String platform) throws Exception {
        String url = "";
        if (telecom.equals(YIDONG)) {
            url = PropertyManager.getProperty("jbb.mgt.flashsdk.api.yidong");
        } else if (telecom.equals(LIANTONG)) {
            url = PropertyManager.getProperty("jbb.mgt.flashsdk.api.liantong");
        } else if (telecom.equals(DIANXIN)) {
            url = PropertyManager.getProperty("jbb.mgt.flashsdk.api.dianxin");
        } else {
            throw new WrongParameterValueException("jbb.mgt.exception.flashsdk.telecom");
        }
        StringBuffer sb = new StringBuffer();
        String mimeBoundary = "--" + HttpUtil.CONTENT_TYPE_FORM_DATA_BOUNDARY;
        // appId
        sb = sb.append("--").append(mimeBoundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"appId\"");
        sb.append("\r\n\r\n");
        sb.append(appId);
        sb.append("\r\n");
        // accessToken
        sb.append("--").append(mimeBoundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"accessToken\"");
        sb.append("\r\n\r\n");
        sb.append(accessToken);
        sb.append("\r\n");
        // telecom
        sb.append("--").append(mimeBoundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"telecom\"");
        sb.append("\r\n\r\n");
        sb.append(telecom);
        sb.append("\r\n");
        // timestamp
        sb.append("--").append(mimeBoundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"timestamp\"");
        sb.append("\r\n\r\n");
        sb.append(timestamp);
        sb.append("\r\n");
        // randoms
        sb.append("--").append(mimeBoundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"randoms\"");
        sb.append("\r\n\r\n");
        sb.append(randoms);
        sb.append("\r\n");
        // sign
        sb.append("--").append(mimeBoundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"sign\"");
        sb.append("\r\n\r\n");
        sb.append(sign);
        sb.append("\r\n");
        // version
        sb.append("--").append(mimeBoundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"version\"");
        sb.append("\r\n\r\n");
        sb.append(version);
        sb.append("\r\n");

        // device
        sb.append("--").append(mimeBoundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"device\"");
        sb.append("\r\n\r\n");
        sb.append(device);
        sb.append("\r\n");

        sb.append("--").append(mimeBoundary).append("--").append("\r\n");
        try {
            HttpResponse response = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url,
                HttpUtil.CONTENT_TYPE_FORM_DATA_WITH_BOUNDARY + "; charset=\"utf-8\"", sb.toString(), null);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                FlashsdkLoginRsp rsp
                    = mapper.readValue(new String(response.getResponseBody(), "UTF-8"), FlashsdkLoginRsp.class);
                if (FlashsdkLoginRsp.SUCCES_CODE.equals(rsp.getCode())) {
                    return decryptDES(rsp.getData().getMobileName(), platform);
                } else {
                    logger.debug("response = " + new String(response.getResponseBody(), "UTF-8"));
                    throw new WrongParameterValueException(rsp.getMessage());
                }

            } else {
                logger.info("response code = " + response.getResponseCode());
            }

        } catch (IOException e) {
            logger.error("response with error, " + e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    public static String decryptDES(String decryptString, String platform) throws Exception {
        String decryptKey = "";
        decryptKey = PropertyManager.getProperty("jbb.mgt.flashsdk.key." +platform);
        byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};
        byte[] byteMi = Base64.getDecoder().decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);
        return new String(decryptedData);
    }

}
