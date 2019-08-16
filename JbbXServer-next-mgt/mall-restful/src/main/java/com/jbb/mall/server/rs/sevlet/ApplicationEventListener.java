package com.jbb.mall.server.rs.sevlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.server.common.Home;
import com.jbb.mall.server.core.util.SpringUtil;

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

    }

    /**
     * Application shutdown
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.warn("Destroying application...");

        SpringUtil.close();
        Home.shutdown();

        logger.warn("Application destroyed");
    }

}
