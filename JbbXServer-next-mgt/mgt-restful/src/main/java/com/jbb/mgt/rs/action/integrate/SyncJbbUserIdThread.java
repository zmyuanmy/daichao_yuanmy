package com.jbb.mgt.rs.action.integrate;

import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserResponse;
import com.jbb.mgt.core.service.JbbService;
import com.jbb.mgt.core.service.UserService;
import com.jbb.mgt.server.core.util.SpringUtil;
import com.jbb.server.sensors.SensorsAnalyticsFactory;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;

public class SyncJbbUserIdThread extends Thread {

    private User user;

    public SyncJbbUserIdThread(User user) {
        this.user = user;
    }

    public void run() {
        updateUserJbbId(user);
    }

    private void updateUserJbbId(User user) {
        if (user.getJbbUserId() != null) {
            return;
        }
        JbbService jbbService = SpringUtil.getBean("JbbService", JbbService.class);
        UserService userService = SpringUtil.getBean("UserService", UserService.class);
        UserResponse userReport = jbbService.getUserReportByPhoneNumber(user.getPhoneNumber());
        if (userReport != null) {
            user.setJbbUserId(userReport.getUserId());
            userService.updateUser(user);

            // 发送userId和借帮帮jbbUserId的绑定事件
            SensorsAnalytics sa = SensorsAnalyticsFactory.getSa();
            try {
                sa.profileSetOnce(String.valueOf(user.getUserId()), true, "jbbUserId",
                    String.valueOf(user.getJbbUserId()));
                sa.flush();
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
            //END
        }

    }
}
