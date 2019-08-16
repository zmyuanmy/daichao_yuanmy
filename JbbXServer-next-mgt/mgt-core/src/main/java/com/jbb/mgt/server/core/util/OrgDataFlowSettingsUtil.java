package com.jbb.mgt.server.core.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.core.domain.DataFlowBase;
import com.jbb.mgt.core.domain.DataFlowSetting;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.service.DataFlowBaseService;
import com.jbb.mgt.core.service.OrganizationService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;

public class OrgDataFlowSettingsUtil {
    private static Logger logger = LoggerFactory.getLogger(OrgDataFlowSettingsUtil.class);

    private static volatile OrgDataFlowSettingsUtil instance;
    private static final Object instanceLock = new Object();
    private static final long POLLING_INTERVAL = PropertyManager.getLongProperty("jbb.settings.refresh.org", 180000);

    private DataFlowBaseService dataFlowBaseService;

    private OrganizationService organizationService;

    private Timer timer;

    private Timer timerForSet;

    public static final Set<Integer> APPLY_USER_SET = Collections.synchronizedSet(new HashSet<Integer>());

    public static final ConcurrentHashMap<Integer, DataFlowSetting> ORG_SETTINGS
        = new ConcurrentHashMap<Integer, DataFlowSetting>();

    public static final ConcurrentHashMap<Integer, Organization> ORGS = new ConcurrentHashMap<Integer, Organization>();

    public static final ConcurrentHashMap<Integer, DataFlowBase> DF_SETTINGS_BASE
        = new ConcurrentHashMap<Integer, DataFlowBase>();

    private volatile boolean shutdownInProgress;

    private OrgDataFlowSettingsUtil() {
        try {
            dataFlowBaseService = SpringUtil.getBean("DataFlowBaseService", DataFlowBaseService.class);
            organizationService = SpringUtil.getBean("OrganizationService", OrganizationService.class);
        } catch (Exception e) {
            logger.error("Exception in OrgDataFlowSettingsUtil init", e);
            return;
        }

        initTimer();

        logger.warn("OrgDataFlowSettingsUtil started");
    }

    private void initTimer() {

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                loadOrgSettings();
            }
        }, 0, POLLING_INTERVAL);

        timerForSet = new Timer();
        // 每隔三小时清理下用户申请列表
        timerForSet.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                APPLY_USER_SET.clear();
            }
        }, 0, 12 * DateUtil.HOUR_MILLSECONDES);
    }

    private void shutdown() {
        shutdownInProgress = true;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        if (timerForSet != null) {
            timerForSet.cancel();
            timerForSet = null;
        }
        logger.warn("LenderesUtil stopped");
    }

    public static OrgDataFlowSettingsUtil init() {
        synchronized (instanceLock) {
            if (instance == null) {
                logger.warn("New LenderesUtil");
                instance = new OrgDataFlowSettingsUtil();
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

    private void loadOrgSettings() {
        try {
            // List<DataFlowSetting> dfSettings = dataFlowSettingService.getDataFlowSettings();

            List<DataFlowBase> dfBases = dataFlowBaseService.getDataFlowBases(true);

            List<Organization> organizations
                = organizationService.getOrganizations(true, false, false, null, null, true);

            // 先移除
            ORG_SETTINGS.clear();
            DF_SETTINGS_BASE.clear();
            ORGS.clear();

            if (!CommonUtil.isNullOrEmpty(dfBases)) {
                dfBases.forEach(dfBase -> {
                    DF_SETTINGS_BASE.put(dfBase.getDataFlowId(), dfBase);
                });
            }

            if (!CommonUtil.isNullOrEmpty(organizations)) {
                organizations.forEach(org -> {
                    ORGS.put(org.getOrgId(), org);
                    if (org.getDataFlowSetting() != null) {
                        ORG_SETTINGS.put(org.getOrgId(), org.getDataFlowSetting());
                    }
                });
            }

            ORG_SETTINGS.forEach((orgId, orgSetting) -> {
                logger.info("refreshed org settings , setting = " + orgSetting);
            });

        } catch (Exception e) {
            logger.error("Exception in loading lenderes", e);
        }
    }
}