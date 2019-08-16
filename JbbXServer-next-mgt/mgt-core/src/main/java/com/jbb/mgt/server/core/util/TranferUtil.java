package com.jbb.mgt.server.core.util;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.core.service.PayRecordService;
import com.jbb.mgt.helipay.service.TransferService;
import com.jbb.server.common.PropertyManager;

public class TranferUtil {
    private static Logger logger = LoggerFactory.getLogger(TranferUtil.class);

    private static volatile TranferUtil instance;
    private static final Object instanceLock = new Object();
    private static final long STATUSTIC_INTERVAL
        = PropertyManager.getLongProperty("mgt.xjl.tranferQuery.interval", 120000L);

    private TransferService transferService;
    private PayRecordService payRecordService;

    private Timer timer;

    private volatile boolean shutdownInProgress;

    private TranferUtil() {
        try {
            transferService = SpringUtil.getBean("TransferService", TransferService.class);
            payRecordService = SpringUtil.getBean("PayRecordService", PayRecordService.class);

        } catch (Exception e) {
            logger.error("Exception in TranferUtil init", e);
            return;
        }

        initTimer();

        logger.warn("TranferUtil started");
    }

    private void initTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                boolean enabled = PropertyManager.getBooleanProperty("mgt.xjl.tranferQuery.enabled", false);
                if (enabled) {
                    int size = PropertyManager.getIntProperty("mgt.xjl.tranferQuery.size", 10);
                    int orgId = PropertyManager.getIntProperty("mgt.xjl.tranferQuery.orgId", 1);
                    tranferQuery(size, orgId);
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
        logger.warn("TranferUtil stopped");
    }

    public static TranferUtil init() {
        synchronized (instanceLock) {
            if (instance == null) {
                logger.warn("New TranferUtil");
                instance = new TranferUtil();
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

    private void tranferQuery(int size, int orgId) {
        try {
            String[] orderIds = payRecordService.selectUnfinalRecord(size, orgId);
            if (orderIds != null && orderIds.length > 0) {
                for (int i = 0; i < orderIds.length; i++) {
                    transferService.tranferQuery(orderIds[i]);
                }

            }

        } catch (Exception e) {
            logger.error("Exception in loading tranferQuery", e);
        }
    }

}
