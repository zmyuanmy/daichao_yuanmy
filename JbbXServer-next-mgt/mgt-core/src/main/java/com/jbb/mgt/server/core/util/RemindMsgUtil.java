package com.jbb.mgt.server.core.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.core.service.XjlRemindMsgCodeService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;

public class RemindMsgUtil {
    private static Logger logger = LoggerFactory.getLogger(RemindMsgUtil.class);

    private static volatile RemindMsgUtil instance;
    private static final Object instanceLock = new Object();
    private static final long STATUSTIC_INTERVAL
        = PropertyManager.getLongProperty("xjl.mgt.remind.sendMsg.interval", DateUtil.DAY_MILLSECONDES);
    private static final int START_DAY = PropertyManager.getIntProperty("xjl.mgt.remind.sendMsg.startDay", 1);
    private static final int END_DAY = PropertyManager.getIntProperty("xjl.mgt.remind.sendMsg.endDay", 7);
    private XjlRemindMsgCodeService xjlRemindMsgCodeService;

    private Timer timer;

    private volatile boolean shutdownInProgress;

    private RemindMsgUtil() {
        try {
            xjlRemindMsgCodeService = SpringUtil.getBean("XjlRemindMsgCodeService", XjlRemindMsgCodeService.class);

        } catch (Exception e) {
            logger.error("Exception in RemindMsgUtil init", e);
            return;
        }

        initTimer();

        logger.warn("RemindMsgUtil started");
    }

    private void initTimer() {
        String startDate = PropertyManager.getProperty("xjl.mgt.remind.sendMsg.startDate");
        Timestamp startT = Util.parseTimestamp(startDate, TimeZone.getDefault());
        if (startT == null) {
            startT = new Timestamp(
                DateUtil.getTodayStartTs() + 12 * DateUtil.HOUR_MILLSECONDES + 30 * DateUtil.MINUTE_MILLSECONDES);
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                boolean enabled = PropertyManager.getBooleanProperty("xjl.mgt.remind.sendMsg.enabled", false);
                if (enabled) {
                    long startDate = DateUtil.getTodayStartTs();
                    // 小金条短信提醒
                    xjlRemindMsg(startDate);
                }
            }
        }, startT, STATUSTIC_INTERVAL);

    }

    private void shutdown() {
        shutdownInProgress = true;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        logger.warn("RemindMsgUtil stopped");
    }

    public static RemindMsgUtil init() {
        synchronized (instanceLock) {
            if (instance == null) {
                logger.warn("New RemindMsgUtil");
                instance = new RemindMsgUtil();
            }
        }
        return instance;
    }

    public static void destroy() {
        synchronized (instanceLock) {
            if ((instance != null) && !instance.shutdownInProgress) {
                instance.shutdown();
                instance = null;
            }
        }
    }

    private void xjlRemindMsg(long startDate) {
        try {
            String startDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date(startDate));
            xjlRemindMsgCodeService.getXjlApplyRecordsByStatus(3, startDate1, 1, null);
            xjlRemindMsgCodeService.getXjlApplyRecordsByStatus(4, startDate1, START_DAY, END_DAY);
            xjlRemindMsgCodeService.getXjlApplyRecordsByStatus(5, startDate1, null, null);

        } catch (Exception e) {
            logger.error("Exception in loading xjlRemindMsg", e);
        }

    }
}
