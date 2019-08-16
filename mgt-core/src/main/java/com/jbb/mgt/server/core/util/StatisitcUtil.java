package com.jbb.mgt.server.core.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.core.domain.ChannelStatisticDaily;
import com.jbb.mgt.core.domain.Channels;
import com.jbb.mgt.core.domain.FinMerchantStatisticDaily;
import com.jbb.mgt.core.domain.StatisticChannelDaily;
import com.jbb.mgt.core.domain.StatisticFeedbackDaily;
import com.jbb.mgt.core.domain.StatisticFeedbackDetailDaily;
import com.jbb.mgt.core.service.ChannelStatisticDailyService;
import com.jbb.mgt.core.service.FinMerchantStatisticDailyService;
import com.jbb.mgt.core.service.LoanChannelStatisticDailyService;
import com.jbb.mgt.core.service.LoanPlatformReportService;
import com.jbb.mgt.core.service.MgtFinOrgStatisticDailyService;
import com.jbb.mgt.core.service.StatisticChannelDailyService;
import com.jbb.mgt.core.service.StatisticFeedbackDailyService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;

public class StatisitcUtil {
    private static Logger logger = LoggerFactory.getLogger(StatisitcUtil.class);

    private static volatile StatisitcUtil instance;
    private static final Object instanceLock = new Object();
    private static final long STATUSTIC_INTERVAL
        = PropertyManager.getLongProperty("mgt.daily.statistic.interval", DateUtil.DAY_MILLSECONDES);

    private MgtFinOrgStatisticDailyService mgtFinOrgStatisticDailyService;
    private ChannelStatisticDailyService channelStatisticDailyService;
    private StatisticChannelDailyService statisticChannelDailyService;
    private FinMerchantStatisticDailyService finMerchantStatisticDailyService;
    private StatisticFeedbackDailyService statisticFeedbackDailyService;
    private LoanPlatformReportService loanPlatformReportService;
    private LoanChannelStatisticDailyService loanChannelStatisticDailyService;

    private Timer timer;

    private volatile boolean shutdownInProgress;

    private StatisitcUtil() {
        try {
            mgtFinOrgStatisticDailyService
                = SpringUtil.getBean("MgtFinOrgStatisticDailyService", MgtFinOrgStatisticDailyService.class);
            channelStatisticDailyService
                = SpringUtil.getBean("ChannelStatisticDailyService", ChannelStatisticDailyService.class);
            statisticChannelDailyService
                = SpringUtil.getBean("StatisticChannelDailyService", StatisticChannelDailyService.class);
            finMerchantStatisticDailyService
                = SpringUtil.getBean("FinMerchantStatisticDailyService", FinMerchantStatisticDailyService.class);
            statisticFeedbackDailyService
                = SpringUtil.getBean("StatisticFeedbackDailyService", StatisticFeedbackDailyService.class);
            loanPlatformReportService
                = SpringUtil.getBean("LoanPlatformReportService", LoanPlatformReportService.class);
            loanChannelStatisticDailyService
                = SpringUtil.getBean("LoanChannelStatisticDailyService", LoanChannelStatisticDailyService.class);

        } catch (Exception e) {
            logger.error("Exception in StatisitcUtil init", e);
            return;
        }

        initTimer();

        logger.warn("StatisitcUtil started");
    }

    private void initTimer() {

        String startDate = PropertyManager.getProperty("mgt.daily.statistic.startDate");
        Timestamp startT = Util.parseTimestamp(startDate, TimeZone.getDefault());
        if (startT == null) {
            startT = new Timestamp(DateUtil.getTodayStartTs() + DateUtil.DAY_MILLSECONDES + 5 * 60 * 1000L);
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                boolean enabled = PropertyManager.getBooleanProperty("mgt.daily.statistic.enabled", false);
                if (enabled) {
                    long endDate = DateUtil.getTodayStartTs();
                    long startDate = endDate - DateUtil.DAY_MILLSECONDES;
                    // 进件注册充销统计
                    loadMgtFinOrgStatisticDaily(startDate, endDate);
                    // 渠道充销统计
                    loadChannelStatisticDaily(startDate);
                    // 进件注册漏斗
                    loadStatisticChannelDaily(startDate, endDate);
                    // 贷超冲销报表
                    loadLoanPlatformReportDaily(startDate, endDate);
                    // 贷超冲销详细表
                    loadLoanPlatformDetailReportDaily(startDate, endDate);
                    // 渠道贷超绩效报表
                    loadLoanChannelDaily(startDate, endDate);
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
        logger.warn("StatisitcUtil stopped");
    }

    public static StatisitcUtil init() {
        synchronized (instanceLock) {
            if (instance == null) {
                logger.warn("New StatisitcUtil");
                instance = new StatisitcUtil();
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

    private void loadMgtFinOrgStatisticDaily(long startDate, long endDate) {
        try {
            Timestamp startDateTs = new Timestamp(startDate);
            Timestamp endDateTs = new Timestamp(endDate);
            mgtFinOrgStatisticDailyService.insertMgtFinOrgStatisticDailyAll(null, null, startDateTs, endDateTs);

        } catch (Exception e) {
            logger.error("Exception in loading mgtFinOrgStatisticDaily", e);
        }
    }

    private void loadChannelStatisticDaily(long startDate) {
        try {
            String startDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date(startDate));
            List<Channels> channels
                = channelStatisticDailyService.getChannelStatisticDailys(startDate1, null, null, null);
            List<ChannelStatisticDaily> list = new ArrayList<ChannelStatisticDaily>();
            for (Channels channel : channels) {
                list.add(channel.getChannelStatisticDaily());
            }
            channelStatisticDailyService.insertChannelsStatisticDaily(list);
        } catch (Exception e) {
            logger.error("Exception in loading channelStatisticDaily", e);
        }
    }

    private void loadStatisticChannelDaily(long startDate, long endDate) {
        try {
            Timestamp startDateTs = new Timestamp(startDate);
            Timestamp endDateTs = new Timestamp(endDate);
            // 注册
            List<StatisticChannelDaily> list = statisticChannelDailyService.getStatisticChannelDailyByChannelCode(null,
                startDateTs, endDateTs, 2, 1);
            statisticChannelDailyService.insertStatisticChannelDaily(list);
            // 进件
            list = statisticChannelDailyService.getStatisticChannelDailyByChannelCode(null, startDateTs, endDateTs, 1,
                1);
            statisticChannelDailyService.insertStatisticChannelDaily(list);
            // 代理
            list = statisticChannelDailyService.getStatisticChannelDailyByChannelCode(null, startDateTs, endDateTs, 3,
                1);
            statisticChannelDailyService.insertStatisticChannelDaily(list);
        } catch (Exception e) {
            logger.error("Exception in loading statisticChannelDaily", e);
        }
    }

    private void loadFinMerchantStatisticDaily(long startDate, long endDate) {
        try {
            Timestamp startDateTs = new Timestamp(startDate);
            Timestamp endDateTs = new Timestamp(endDate);
            List<FinMerchantStatisticDaily> list
                = finMerchantStatisticDailyService.selectEventLogByEventValue(startDateTs, endDateTs);
            if (list.size() != 0) {
                for (FinMerchantStatisticDaily merchant : list) {
                    if (merchant != null && merchant.getStatisticDate() != null && merchant.getMerchantId() > 0) {

                        FinMerchantStatisticDaily merchantStatistic
                            = finMerchantStatisticDailyService.selectMerchantByStaDate(merchant.getMerchantId(),
                                new SimpleDateFormat("yyyy-MM-dd").format(merchant.getStatisticDate()), null);

                        int balance = null != merchantStatistic ? merchantStatistic.getBalance() : 0;
                        merchant.setBalance(balance + merchant.getAmount() - merchant.getExpense());
                        finMerchantStatisticDailyService.insertMerchant(merchant);
                    }
                }
            }

        } catch (Exception e) {
            logger.error("Exception in loading finMerchantStatisticDaily", e);
        }
    }

    private void loadStatisticFeedbackDaily(long startDate, long endDate) {
        try {

            Timestamp startDateTs = new Timestamp(startDate);
            Timestamp endDateTs = new Timestamp(endDate);

            List<StatisticFeedbackDaily> list
                = statisticFeedbackDailyService.selectStatisticFeedbackDaily(startDateTs, endDateTs);
            List<StatisticFeedbackDetailDaily> list2
                = statisticFeedbackDailyService.selectStatisticFeedbackDetailDaily(startDateTs, endDateTs);
            if (list.size() > 0) {
                statisticFeedbackDailyService.insertStatisticFeedbackDaily(list);
            }
            if (list2.size() > 0) {
                statisticFeedbackDailyService.insertStatisticFeedbackDetailDaily(list2);
            }
        } catch (Exception e) {
            logger.error("Exception in loading statisticFeedbackDaily", e);
        }
    }

    private void loadMgtFinOrgAdStatisticDaily(long startDate, long endDate) {
        try {
            Timestamp startDateTs = new Timestamp(startDate);
            Timestamp endDateTs = new Timestamp(endDate);
            mgtFinOrgStatisticDailyService.insertMgtFinOrgStatisticDailyAd(3, null, startDateTs, endDateTs);;

        } catch (Exception e) {
            logger.error("Exception in loading mgtFinOrgAdStatisticDaily", e);
        }
    }

    private void loadLoanPlatformReportDaily(long startDate, long endDate) {
        try {
            Timestamp startDateTs = new Timestamp(startDate);
            Timestamp endDateTs = new Timestamp(endDate);
            loanPlatformReportService.runLoanPlatFormStatistic(startDateTs, endDateTs, true);

        } catch (Exception e) {
            logger.error("Exception in loading loadLoanPlatformReportDaily", e);
        }
    }

    private void loadLoanPlatformDetailReportDaily(long startDate, long endDate) {
        try {
            Timestamp startDateTs = new Timestamp(startDate);
            Timestamp endDateTs = new Timestamp(endDate);
            loanPlatformReportService.runLoanPlatFormDetailStatistic(startDateTs, endDateTs);

        } catch (Exception e) {
            logger.error("Exception in loading loadLoanPlatformDetailReportDaily", e);
        }
    }

    private void loadLoanChannelDaily(long startDate, long endDate) {
        try {
            Timestamp startDateTs = new Timestamp(startDate);
            Timestamp endDateTs = new Timestamp(endDate);
            loanChannelStatisticDailyService.runLoanChannelStatisticDaily(startDateTs, endDateTs);

        } catch (Exception e) {
            logger.error("Exception in loading loadLoanChannelDaily", e);
        }
    }

}
