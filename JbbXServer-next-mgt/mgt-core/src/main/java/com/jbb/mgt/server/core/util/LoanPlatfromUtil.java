package com.jbb.mgt.server.core.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.core.domain.PlatformVo;
import com.jbb.mgt.core.service.ChuangLanService;
import com.jbb.mgt.core.service.LoanPlatformService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.HttpUtil;

public class LoanPlatfromUtil {

    private static Logger logger = LoggerFactory.getLogger(LoanPlatfromUtil.class);

    private static volatile LoanPlatfromUtil instance;
    private static final Object instanceLock = new Object();
    private static final long URL_CHECK_INTERVAL =
        PropertyManager.getLongProperty("mgt.loanPlatform.url.check.interval", 600000);

    private LoanPlatformService loanPlatformService;

    private ChuangLanService chuanLanService;

    private Timer timer;

    private volatile boolean shutdownInProgress;

    private LoanPlatfromUtil() {
        try {
            loanPlatformService = SpringUtil.getBean("LoanPlatformService", LoanPlatformService.class);
            chuanLanService =  SpringUtil.getBean("ChuangLanService", ChuangLanService.class);
        } catch (Exception e) {
            logger.error("Exception in LoanPlatfromUtil init", e);
            return;
        }

        initTimer();

        logger.warn("LoanPlatfromUtil started");
    }

    private void initTimer() {

        Timestamp startT = new Timestamp(DateUtil.getCurrentTime() + URL_CHECK_INTERVAL);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                boolean enabled = PropertyManager.getBooleanProperty("mgt.loanPlatform.url.check.enabled", false);
                if (enabled) {
                    // 贷超冲销报表
                    List<Integer> ids = checkLoanPlatformUrl();
                    if (ids == null || ids.size() == 0) {
                        return;
                    }
                    // 发送短信
                    String[] phones = PropertyManager.getProperties("mgt.loanPlatform.url.check.alterPhones");
                    if (phones == null) {
                        return;
                    }
                    String str = "";
                    for (int id : ids) {
                        str += id + "|";
                    }
                    for (String phone : phones) {
                        chuanLanService.sendMsgCodeWithContent(phone, "贷超平台链接失效，产品列表：" + str);
                    }

                }
            }
        }, startT, URL_CHECK_INTERVAL);

    }

    private List<Integer> checkLoanPlatformUrl() {
        List<Integer> ids = new ArrayList<Integer>();
        String[] errorStatuses = PropertyManager.getProperties("mgt.loanPlatform.url.check.alterStatuses");
        List<PlatformVo> loanPlatforms = loanPlatformService.getPlatforms(false, null, null, null, null, null, null);
        if (loanPlatforms == null) {
            return ids;
        }
        
        int[] ignoreIds = PropertyManager.getIntProperties("mgt.loanPlatform.url.check.ignoreIds", 0);
        
        for (PlatformVo loanPlatform : loanPlatforms) {
            String url = loanPlatform.getUrl();
            int platformId = loanPlatform.getPlatformId();
            
            if(CommonUtil.inArray(platformId, ignoreIds)){
                continue;
            };
            int status = HttpUtil.testUrlConnection(url);
            if (CommonUtil.inArray(String.valueOf(status), errorStatuses)) {
                ids.add(platformId);
            } else if (CommonUtil.inArray("4xx", errorStatuses) && status >= 400 && status < 500) {
                ids.add(platformId);
            } else if (CommonUtil.inArray("5xx", errorStatuses) && status >= 500 && status < 600) {
                ids.add(platformId);
            } else if (CommonUtil.inArray("6xx", errorStatuses) && status >= 600 && status < 700) {
                ids.add(platformId);
            }
        }
        return ids;
    }

    private void shutdown() {
        shutdownInProgress = true;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        logger.warn("LoanPlatfromUtil stopped");
    }

    public static LoanPlatfromUtil init() {
        synchronized (instanceLock) {
            if (instance == null) {
                logger.warn("New LoanPlatfromUtil");
                instance = new LoanPlatfromUtil();
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

}
