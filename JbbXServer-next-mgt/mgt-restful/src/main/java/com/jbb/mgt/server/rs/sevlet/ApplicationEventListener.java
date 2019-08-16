package com.jbb.mgt.server.rs.sevlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.jbb.mgt.server.core.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.server.common.Home;
import com.jbb.server.sensors.SensorsAnalyticsFactory;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;

/**
 * Application startup and shutdown listener
 * 
 * @author Vincent Tang
 */
@WebListener
public class ApplicationEventListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationEventListener.class);

    /**
     * Application startup
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.warn("Application started");
        Home.getHomeDir();
        Home.settingsTest();

        PropertiesUtil.init();
        // OrgDataFlowSettingsUtil.init();
        // RegisterEventUtil.init();
        StatisitcUtil.init();
        // TranferUtil.init();
        // QiFengCustAddUtil.init();
        // RemindMsgUtil.init();
        // LoanPlatformStatisticUtil.init();
        CalculatePlatFormExpenseUtil.init();

        // 检测贷超产品链接
        LoanPlatfromUtil.init();
        MsgToSalesUtil.init();
        // CheckApplyUtil.init();
    }

    /**
     * Application shutdown
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.warn("Destroying application...");

        // OrgDataFlowSettingsUtil.destroy();
        PropertiesUtil.destroy();
        // RegisterEventUtil.destroy();
        StatisitcUtil.destroy();
        // TranferUtil.destroy();
        // QiFengCustAddUtil.destroy();
        // RemindMsgUtil.destroy();
        // LoanPlatformStatisticUtil.destroy();
        CalculatePlatFormExpenseUtil.destroy();
        // CheckApplyUtil.destroy();

        // 销毁检测产品链接
        LoanPlatfromUtil.destroy();

        // 神策分析关闭
        SensorsAnalyticsFactory.close();
        MsgToSalesUtil.destroy();
        SpringUtil.close();
        Home.shutdown();

        logger.warn("Application destroyed");
    }

}
