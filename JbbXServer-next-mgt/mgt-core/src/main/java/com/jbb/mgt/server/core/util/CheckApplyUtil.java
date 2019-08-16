package com.jbb.mgt.server.core.util;

import com.jbb.mgt.core.service.PayRecordService;
import com.jbb.mgt.core.service.QianChengService;
import com.jbb.mgt.helipay.service.TransferService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class CheckApplyUtil {

    private static Logger logger = LoggerFactory.getLogger(CheckApplyUtil.class);

    private static volatile CheckApplyUtil instance;
    private static final Object instanceLock = new Object();
    private static final long STATUSTIC_INTERVAL
        = PropertyManager.getLongProperty("mgt.xjl.checkapply.interval", 300000L);

    private QianChengService qianChengService;

    private Timer timer;

    private volatile boolean shutdownInProgress;

    private CheckApplyUtil() {
        try {
            qianChengService = SpringUtil.getBean("QianChengService", QianChengService.class);
        } catch (Exception e) {
            logger.error("Exception in CheckApplyUtil init", e);
            return;
        }

        initTimer();

        logger.warn("CheckApplyUtil started");
    }

    private void initTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                boolean enabled = PropertyManager.getBooleanProperty("mgt.xjl.checkapply.enable", false);
                if (enabled) {
                    checkApply();
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
        logger.warn("CheckApplyUtil stopped");
    }

    public static CheckApplyUtil init() {
        synchronized (instanceLock) {
            if (instance == null) {
                logger.warn("New CheckApplyUtil");
                instance = new CheckApplyUtil();
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

    private void checkApply() {
        try {
           qianChengService.checkUserApplyRecord();
        } catch (Exception e) {
            logger.error("Exception in loading tranferQuery", e);
        }
    }

}
