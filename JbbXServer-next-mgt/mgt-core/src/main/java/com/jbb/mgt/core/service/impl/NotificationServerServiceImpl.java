package com.jbb.mgt.core.service.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.mgt.core.domain.Message;
import com.jbb.mgt.core.domain.UserLcDevice;
import com.jbb.mgt.core.doman.notification.LeanCloudPayload;
import com.jbb.mgt.core.doman.notification.LeanCloudPayload.AndroidAlertData;
import com.jbb.mgt.core.doman.notification.LeanCloudPayload.IosAlertData;
import com.jbb.mgt.core.doman.notification.NotificationRequest;
import com.jbb.mgt.core.service.NotificationServerService;
import com.jbb.mgt.core.service.UserLcDeviceService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.CodecUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.HttpUtil.HttpResponse;
import com.jbb.server.common.util.StringUtil;

@Service("NotificationServerService")
public class NotificationServerServiceImpl implements NotificationServerService {
    private static Logger logger = LoggerFactory.getLogger(NotificationServerServiceImpl.class);
    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Autowired
    private UserLcDeviceService userLcDeviceService;

    @Override
    public void pushNotification(NotificationRequest notificationReq) {
        List<LeanCloudPayload> pushDataList = generatePushNotification(notificationReq);
        if (pushDataList == null || pushDataList.size() == 0) {
            return;
        }
        for (LeanCloudPayload pushData : pushDataList) {
            pushNotification(pushData);
        }

    }

    private void pushNotification(LeanCloudPayload pushData) {
        String lcId = PropertyManager.getProperty("jbb.xjl.leancloud.id");
        String appKey = PropertyManager.getProperty("jbb.xjl.leancloud.appKey");
        String url = PropertyManager.getProperty("jbb.xjl.leancloud.push.url");
        String[][] requestProperties = {{"X-LC-Id", lcId}, {"X-LC-Key", appKey}};

        try {
            String jsonData = mapper.writeValueAsString(pushData);
            if (logger.isDebugEnabled()) {
                logger.debug("pushNotification=", jsonData);
            }

            HttpResponse response = HttpUtil.sendDataViaHTTPRepeat(HttpUtil.HTTP_POST, url, HttpUtil.CONTENT_TYPE_JSON,
                jsonData, requestProperties, "pushNotification");

            if (response.isOkResponseCode()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("pushNotification success", new String(response.getResponseBody()));
                }
            } else {
                logger.error("pushNotification error", response.getErrorMessage());
            }
        } catch (JsonProcessingException e) {
            logger.error("pushNotification error", e);
        }
    }

    private List<LeanCloudPayload> generatePushNotification(NotificationRequest req) {

        List<LeanCloudPayload> list = new ArrayList<LeanCloudPayload>();

        Message message = req.getMessage();

        int userId = message.getToUserId();
        String appName = message.getAppName();

        List<UserLcDevice> devices = userLcDeviceService.getUserLcDeviceListByUserId(userId, appName);

        for (UserLcDevice device : devices) {

            LeanCloudPayload payload = new LeanCloudPayload();
            payload.setCql("select * from _Installation where objectId='" + device.getObjectId() + "'");
            if (device.isAndroid()) {
                AndroidAlertData alert = new AndroidAlertData();
                alert.setAlert(message.getMessageText());
                alert.setTitle(message.getSubject());
                payload.setData(alert);
            } else if (device.isIos()) {
                IosAlertData alert = new IosAlertData();
                alert.setAlert(message.getSubject() + "\r\n" + message.getMessageText());
                alert.setBadge("Increment");
                payload.setData(alert);
                payload.setProd(PropertyManager.getProperty("jbb.xjl.platform.ios.push"));
            }
            list.add(payload);
        }

        return list;
    }

    private String getSignature(String input, String key) {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1);
        String signature = null;
        try {
            Mac mac = Mac.getInstance(HMAC_SHA1);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(input.getBytes());
            signature = new String(CodecUtil.toHex(rawHmac));
        } catch (InvalidKeyException e) {
            logger.error("getLoginSignature error", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("getLoginSignature error", e);
        }
        return signature;
    }

    @Override
    public String getConvSignature(String clientId, String sortedMemberIds, String nonce, long timestamp, String convId,
        String action) {
        String appid = PropertyManager.getProperty("jbb.xjl.leancloud.id");
        String masterKey = PropertyManager.getProperty("jbb.xjl.leancloud.masterkey");
        String str = appid + ":" + clientId + (StringUtil.isEmpty(convId) ? "" : (":" + convId)) + ":" + sortedMemberIds
            + ":" + timestamp + ":" + nonce
            + ((StringUtil.isEmpty(action) || "create".equals(action)) ? "" : (":" + action));
        String signature = getSignature(str, masterKey);
        if (logger.isDebugEnabled()) {
            logger.debug(str + "->" + signature);
        }
        return signature;
    }

    @Override
    public String getLoginSignature(String clientId, String nonce, long timestamp) {
        String appid = PropertyManager.getProperty("jbb.xjl.leancloud.id");
        String masterKey = PropertyManager.getProperty("jbb.xjl.leancloud.masterkey");
        String str = appid + ":" + clientId + "::" + timestamp + ":" + nonce;
        String signature = getSignature(str, masterKey);
        if (logger.isDebugEnabled()) {
            logger.debug(str + "->" + signature);
        }
        return signature;
    }

}
