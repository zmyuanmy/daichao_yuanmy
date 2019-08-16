package com.jbb.mgt.server.core.util;

import com.jbb.mgt.core.service.*;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by inspironsun on 2018/12/18
 */
public class LoanPlatformStatisticUtil {
    private static Logger logger = LoggerFactory.getLogger(LoanPlatformStatisticUtil.class);

    private static volatile LoanPlatformStatisticUtil instance;
    private static final Object instanceLock = new Object();
    private static final long STATUSTIC_INTERVAL
        = PropertyManager.getLongProperty("mgt.daily.loanPlatform.statistic.interval", DateUtil.DAY_MILLSECONDES);

    private LoanPlatformReportService loanPlatformReportService;

    private Timer timer;

    private volatile boolean shutdownInProgress;

    private LoanPlatformStatisticUtil() {
        try {
            loanPlatformReportService
                = SpringUtil.getBean("LoanPlatformReportService", LoanPlatformReportService.class);
        } catch (Exception e) {
            logger.error("Exception in LoanPlatformStatisticUtil init", e);
            return;
        }

        initTimer();

        logger.warn("LoanPlatformStatisticUtil started");
    }

    private void initTimer() {

        String startDate = PropertyManager.getProperty("mgt.daily.loanPlatform.statistic.startDate");
        Timestamp startT = Util.parseTimestamp(startDate, TimeZone.getDefault());
        if (startT == null) {
            startT = new Timestamp(DateUtil.getTodayStartTs() + 12 * DateUtil.HOUR_MILLSECONDES);
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                boolean enabled = PropertyManager.getBooleanProperty("mgt.daily.loanPlatform.statistic.enabled", false);
                if (enabled) {
                    long endDate = DateUtil.getTodayStartTs() + 12 * DateUtil.HOUR_MILLSECONDES;
                    long startDate = DateUtil.getTodayStartTs();
                    // 贷超冲销报表
                    loadLoanPlatformReportDaily(startDate, endDate);
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
        logger.warn("LoanPlatformStatisticUtil stopped");
    }

    public static LoanPlatformStatisticUtil init() {
        synchronized (instanceLock) {
            if (instance == null) {
                logger.warn("New LoanPlatformStatisticUtil");
                instance = new LoanPlatformStatisticUtil();
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

    private void loadLoanPlatformReportDaily(long startDate, long endDate) {
        try {
            Timestamp startDateTs = new Timestamp(startDate);
            Timestamp endDateTs = new Timestamp(endDate);
            loanPlatformReportService.runLoanPlatFormStatistic(startDateTs, endDateTs, false);

        } catch (Exception e) {
            logger.error("Exception in loading loadLoanPlatformReportDaily", e);
        }
    }
}
