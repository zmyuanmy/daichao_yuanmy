package com.jbb.mgt.server.core.util;

import java.util.Timer;
import java.util.TimerTask;

import com.jbb.mgt.core.service.MsgToSalesService;
import com.jbb.server.common.PropertyManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MsgToSalesUtil {

    private static volatile MsgToSalesUtil instance;
    private static final Object instanceLock = new Object();
    private static final long STATUSTIC_INTERVAL
        = PropertyManager.getLongProperty("jbb.platform.remind.sendMsg.interval", 300000L);
    private MsgToSalesService msgToSalesService;

    private Timer timer;

    private volatile boolean shutdownInProgress;

    private MsgToSalesUtil() {
        try {
            msgToSalesService = SpringUtil.getBean("MsgToSalesService", MsgToSalesService.class);

        } catch (Exception e) {
            log.error("Exception in MsgToSalesUtil init", e);
            return;
        }

        initTimer();

        log.warn("MsgToSalesUtil started");
    }

    private void initTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                boolean enabled = PropertyManager.getBooleanProperty("jbb.platform.msgRemind.enabled", false);
                if (enabled) {
                    sendMsgToSales(1);
                    sendMsgToSales(2);
                }
            }
        }, 0, STATUSTIC_INTERVAL);

    }

    private void shutdown() {
        shutdownInProgress = true;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        log.warn("RemindMsgUtil stopped");
    }

    public static MsgToSalesUtil init() {
        synchronized (instanceLock) {
            if (instance == null) {
                log.warn("New RemindMsgUtil");
                instance = new MsgToSalesUtil();
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

    private void sendMsgToSales(Integer type) {
        Thread asyncThread = new Thread("asyncDataHandlerThread") {
            public void run() {
                log.debug("异步调用service...");
                try {
                    msgToSalesService.sendMsgToSales(type);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        asyncThread.start();

    }
}
