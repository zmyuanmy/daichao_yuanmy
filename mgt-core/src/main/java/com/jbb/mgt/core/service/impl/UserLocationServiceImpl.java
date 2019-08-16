package com.jbb.mgt.core.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.mgt.core.dao.UserLocationDao;
import com.jbb.mgt.core.domain.Location;
import com.jbb.mgt.core.domain.RegeoRsp;
import com.jbb.mgt.core.domain.Regeocodes;
import com.jbb.mgt.core.service.UserLocationService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.HttpUtil.HttpResponse;

import net.sf.json.JSONObject;

@Service("UserLocationService")
public class UserLocationServiceImpl implements UserLocationService {
    private static Logger logger = LoggerFactory.getLogger(UserLocationService.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Autowired
    private UserLocationDao userLocationDao;

    @Override
    public void insertLocation(Location location) {
        userLocationDao.insertLocation(location);
    }

    @Override
    public List<Location> getLocations(int userId, Timestamp startDate, Timestamp endDate) {
        return userLocationDao.selectLocations(userId, startDate, endDate);
    }

    @Override
    public Location getLastLocation(int userId) {
        return userLocationDao.selectLastLocation(userId);
    }

    @Override
    public String getRegeo(double latitude, double longitude) {
        String url = PropertyManager.getProperty("jbb.mgt.amap.api.url");
        String appKey = PropertyManager.getProperty("jbb.mgt.amap.api.appKey");
        url += "?key=" + appKey + "&radius=0&extensions=base" + "&location=" + longitude + "," + latitude;
        String address = null;
        try {
            HttpResponse response
                = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_GET, url, HttpUtil.CONTENT_TYPE_JSON, null, null);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                String rspText = new String(response.getResponseBody());
                rspText = new String(rspText.getBytes(), "UTF-8");
                JSONObject object = JSONObject.fromObject(rspText);
                if (object.getInt("status") == RegeoRsp.SUCCES_CODE) {
                    RegeoRsp rsp = mapper.readValue(rspText, RegeoRsp.class);
                    Regeocodes regeocode = rsp.getRegeocode();
                    address = regeocode.getFormatted_address();
                } else {
                    logger.error("getRegeo() request =  " + url);
                    logger.error("getRegeo() response with error, " + rspText);
                }
            } else {
                String bodyStr = new String(response.getResponseBody());
                logger.error("getRegeo() request =  " + url);
                logger.error("getRegeo() response with error, " + bodyStr);
            }
        } catch (IOException e) {
            logger.error("getRegeo() response with error, " + e.getMessage());
            e.printStackTrace();
        }
        return address;

    }
}
