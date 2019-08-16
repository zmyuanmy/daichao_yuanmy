package com.jbb.mgt.server.core.util;

import java.sql.Timestamp;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import com.jbb.mgt.core.service.LoanPlatformReportService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculatePlatFormExpenseUtil {

    private static volatile CalculatePlatFormExpenseUtil instance;
    private static final Object instanceLock = new Object();
    private static final long STATUSTIC_INTERVAL
        = PropertyManager.getLongProperty("mgt.calcalate.loanPlatform.expense.interval", 1200000L);

    private LoanPlatformReportService loanPlatformReportService;

    private Timer timer;

    private volatile boolean shutdownInProgress;

    private CalculatePlatFormExpenseUtil() {
        try {
            loanPlatformReportService
                = SpringUtil.getBean("LoanPlatformReportService", LoanPlatformReportService.class);
        } catch (Exception e) {
            log.error("Exception in LoanPlatformStatisticUtil init", e);
            return;
        }

        initTimer();

        log.warn("LoanPlatformStatisticUtil started");
    }

    private void initTimer() {

        String startDate = PropertyManager.getProperty("mgt.calcalate.loanPlatform.expense.startDate");
        Timestamp startT = Util.parseTimestamp(startDate, TimeZone.getDefault());
        if (startT == null) {
            startT = new Timestamp(DateUtil.getTodayStartTs() + 6 * DateUtil.HOUR_MILLSECONDES);
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                boolean enabled
                    = PropertyManager.getBooleanProperty("mgt.calcalate.loanPlatform.expense.enabled", false);
                if (enabled) {
                    long endDate = DateUtil.getCurrentTime();
                    long startDate = DateUtil.getTodayStartTs();
                    // 贷超冲销报表
                    CalculatePlatFormExpense(startDate, endDate);
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
        log.warn("LoanPlatformStatisticUtil stopped");
    }

    public static CalculatePlatFormExpenseUtil init() {
        synchronized (instanceLock) {
            if (instance == null) {
                log.warn("New LoanPlatformStatisticUtil");
                instance = new CalculatePlatFormExpenseUtil();
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

    private void CalculatePlatFormExpense(long startDate, long endDate) {
        try {
            Timestamp startDateTs = new Timestamp(startDate);
            Timestamp endDateTs = new Timestamp(endDate);
            loanPlatformReportService.runLoanPlatFormStatistic(startDateTs, endDateTs, false);

        } catch (Exception e) {
            log.error("Exception in loading loadLoanPlatformReportDaily", e);
        }
    }
}
