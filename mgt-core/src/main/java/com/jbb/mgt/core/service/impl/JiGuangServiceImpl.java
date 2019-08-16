package com.jbb.mgt.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.mgt.core.dao.JiGuangDao;
import com.jbb.mgt.core.domain.JiGuangMessage;
import com.jbb.mgt.core.domain.JiGuangNotification;
import com.jbb.mgt.core.domain.JiGuangNotification.Notification;
import com.jbb.mgt.core.domain.JiGuangNotification.android;
import com.jbb.mgt.core.domain.JiGuangNotification.extras;
import com.jbb.mgt.core.domain.JiGuangNotification.ios;
import com.jbb.mgt.core.domain.JiGuangUserDevice;
import com.jbb.mgt.core.service.JiGuangService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.CodecUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.HttpUtil.HttpResponse;

@Service("JiGuangService")
public class JiGuangServiceImpl implements JiGuangService {
    private static Logger logger = LoggerFactory.getLogger(JiGuangServiceImpl.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private JiGuangDao dao;

    @Override
    public void saveUserDevice(JiGuangUserDevice userDevice) {
        dao.saveUserDevice(userDevice);

    }

    @Override
    public void jiGuangPush(JiGuangMessage jiGuangMessage) {
        JiGuangNotification notification = new JiGuangNotification();
        Object platform = jiGuangMessage.getPlatform() == null ? "all" : jiGuangMessage.getPlatform();
        Object audience = jiGuangMessage.getAudience() == null ? "all" : jiGuangMessage.getAudience();

        notification.setPlatform(platform);
        notification.setAudience(audience);
        // extra 信息
        extras ex = new extras();
        ex.setPlatformId(jiGuangMessage.getPlatformId());
        ex.setPlatformName(jiGuangMessage.getPlatformName());
        ex.setPlatformUrl(jiGuangMessage.getPlatformUrl());
        // 安卓信息
        android alert = new android();
        alert.setAlert(jiGuangMessage.getAlert());
        alert.setTitle(jiGuangMessage.getTitle());
        alert.setExtras(ex);
        // ios信息
        ios alert1 = new ios();
        alert1.setAlert(jiGuangMessage.getAlert());
        alert1.setSound("default");
        alert1.setBadge(0);
        alert1.setExtras(ex);

        Notification n = new Notification();
        n.setAndroid(alert);
        n.setIos(alert1);
        notification.setNotification(n);

        jiGuangPushNotification(notification);
    }

    private void jiGuangPushNotification(JiGuangNotification notification) {
        String Mastersecret = PropertyManager.getProperty("jbb.mgt.jiguang.mastersecret");
        String AppKey = PropertyManager.getProperty("jbb.mgt.jiguang.appkey");
        String url = PropertyManager.getProperty("jbb.mgt.jiguang.url");
        String s = AppKey + ":" + Mastersecret;
        String Authorization = "Basic" + " " + CodecUtil.toBase64String(s.getBytes());
        String[][] Authorization1 = {{"Authorization", Authorization}};
        System.out.println(Authorization);

        try {
            String jsonData = mapper.writeValueAsString(notification);

            HttpResponse response = HttpUtil.sendDataViaHTTPRepeat(HttpUtil.HTTP_POST, url, HttpUtil.CONTENT_TYPE_JSON,
                jsonData, Authorization1, "pushNotification");

            if (response.isOkResponseCode()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("jiGuangPushNotification success", new String(response.getResponseBody()));
                }
            } else {
                logger.error("jiGuangPushNotification error", response.getErrorMessage());
            }
        } catch (JsonProcessingException e) {
            logger.error("jiGuangPushNotification error", e);
        }

    }

}
