package com.jbb.mgt.server.core.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.core.domain.HumanLpPhone;
import com.jbb.mgt.core.service.HumanLpPhoneService;
import com.jbb.mgt.core.service.QiFengService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;

public class QiFengCustAddUtil {

    private static Logger logger = LoggerFactory.getLogger(QiFengCustAddUtil.class);

    private static volatile QiFengCustAddUtil instance;
    private static final Object instanceLock = new Object();
    private static final long STATUSTIC_INTERVAL
        = PropertyManager.getLongProperty("mgt.qiFeng.custAdd.interval", 15 * DateUtil.MINUTE_MILLSECONDES);

    private QiFengService qiFengService;
    private HumanLpPhoneService humanLpPhoneService;

    private Timer timer;

    private volatile boolean shutdownInProgress;

    private QiFengCustAddUtil() {
        try {
            qiFengService = SpringUtil.getBean("QiFengService", QiFengService.class);
            humanLpPhoneService = SpringUtil.getBean("HumanLpPhoneService", HumanLpPhoneService.class);
        } catch (Exception e) {
            logger.error("Exception in QiFengCustAddUtil init", e);
            return;
        }

        initTimer();

        logger.warn("QiFengCustAddUtil started");
    }

    private void initTimer() {
        String startDate = PropertyManager.getProperty("mgt.qiFeng.custAdd.startDate");
        Timestamp startT = Util.parseTimestamp(startDate, TimeZone.getDefault());
        if (startT == null) {
            startT = DateUtil.getCurrentTimeStamp();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                boolean enabled = PropertyManager.getBooleanProperty("mgt.qiFeng.custAdd.enabled", false);
                if (enabled) {
                    long endDate = DateUtil.getCurrentTimeStamp().getTime();
                    long startDate = endDate - STATUSTIC_INTERVAL - 300000L;
                    // 定时推送数据至企蜂
                    qiFengCustAdd(startDate, endDate);
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
        logger.warn("QiFengCustAddUtil stopped");
    }

    public static QiFengCustAddUtil init() {
        synchronized (instanceLock) {
            if (instance == null) {
                logger.warn("New QiFengCustAddUtil");
                instance = new QiFengCustAddUtil();
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

    private void qiFengCustAdd(long startDate, long endDate) {
        logger.info("> qiFengCustAdd, startDate =" + startDate + " ,endDate=" + endDate);
        try {
            Timestamp startDateTs = new Timestamp(startDate);
            Timestamp endDateTs = new Timestamp(endDate);
            List<HumanLpPhone> humanLpPhones
                = humanLpPhoneService.getHumanLpPhoneByStatus(startDateTs, endDateTs, false, null);
            // 目前对接 接口不能超过10!!!
            int total = humanLpPhones.size();
            int page = 0;
            int size = 10;
            logger.info(" total =" + total);
            while (page * size < total) {
                logger.info(" page =" + page);
                List<HumanLpPhone> humanLpPhones1 = new ArrayList<HumanLpPhone>();
                int end = ((page + 1) * size > total) ? total : ((page + 1) * size);
                humanLpPhones1 = humanLpPhones.subList(page * size, end);
                qiFengService.qiFengCustAdd(humanLpPhones1);
                page++;

                Thread.sleep(2000);
            }

            humanLpPhoneService.updateHumanLpPhone(startDateTs, endDateTs);
        } catch (Exception e) {
            logger.error("Exception in loading qiFengCustAdd", e);
        }
        logger.info("< qiFengCustAdd");
    }
}
