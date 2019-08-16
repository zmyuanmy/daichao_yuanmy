package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.UserDao;
import com.jbb.mgt.core.dao.UserEventLogDao;
import com.jbb.mgt.core.domain.PlatformUv;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.service.UserEventLogService;
import com.jbb.server.sensors.SensorsAnalyticsFactory;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;

@Service("userEventLogService")
public class UserEventLogServiceImpl implements UserEventLogService {

    @Autowired
    private UserEventLogDao userEventLogDao;

    @Autowired
    private UserDao userDao;

    // 记录用户操作
    @Override
    public boolean insertEventLog(Integer userId, String sourceId, String cookieId, String eventName,
        String eventAction, String eventLabel, String eventValue, String remoteAddress, Timestamp creationDate) {
        return this.insertEventLog(userId, sourceId, cookieId, eventName, eventAction, eventLabel, eventValue, null,
            remoteAddress, creationDate);
    }

    // 统计用户操作
    @Override
    public int countEventLogByParams(Integer userId, String sourceId, String cookieId, String eventName,
        String eventAction, String eventLabel, String eventValue, Timestamp start, Timestamp end) {
        return userEventLogDao.countEventLogByParams(userId, sourceId, cookieId, eventName, eventAction, eventLabel,
            eventValue, start, end);
    }

    @Override
    public boolean insertEventLog(Integer userId, String sourceId, String cookieId, String eventName,
        String eventAction, String eventLabel, String eventValue, String eventValue2, String remoteAddress,
        Timestamp creationDate) {
        boolean flag = userEventLogDao.insertEventLog(userId, sourceId, cookieId, eventName, eventAction, eventLabel,
            eventValue, eventValue2, remoteAddress, creationDate);
        // 同步至神策分析
        // 贷超产品点击
        if ("daichao".equals(eventName)) {
            String distinctId = String.valueOf(userId);
            // 转换jbb userId 至manager系统里的userId
            if (userId != null) {
                User user = userDao.selectJbbUserById(userId, 1);
                if (user != null) {
                    distinctId = String.valueOf(user.getUserId());
                }
            }
            SensorsAnalytics sa = SensorsAnalyticsFactory.getSa();
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put("area_id", eventAction == null ? "" : eventAction);
            properties.put("area_pos", eventValue == null ? "" : eventValue);
            properties.put("pos", eventValue2 == null ? "" : eventValue2);
            properties.put("platform_id", cookieId == null ? "" : cookieId);
            properties.put("app_channel", sourceId == null ? "" : sourceId);
            properties.put("area_name", eventLabel == null ? "" : eventLabel);
            properties.put("jbb_user_id", userId == null ? "" : userId);
            properties.put("remote_address", remoteAddress);
            properties.put("creationDate", creationDate);
            // 记录用户浏览商品事件
            try {
                sa.track(distinctId, true, "JbbProductClickEvent", properties);
                sa.flush();
            } catch (InvalidArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return flag;
    }

    @Override
    public int countEventLogByParams(Integer userId, String sourceId, String cookieId, String eventName,
        String eventAction, String eventLabel, String eventValue, String eventValue2, Timestamp start, Timestamp end) {
        return userEventLogDao.countEventLogByParams(userId, sourceId, cookieId, eventName, eventAction, eventLabel,
            eventValue, eventValue2, start, end);
    }

    @Override
    public List<PlatformUv> getPlatformUv(Integer salesId, String groupName, Integer platformId, String startDate,
        String endDate) {
        return userEventLogDao.selectPlatformUv(salesId, groupName, platformId, startDate, endDate);
    }

}
