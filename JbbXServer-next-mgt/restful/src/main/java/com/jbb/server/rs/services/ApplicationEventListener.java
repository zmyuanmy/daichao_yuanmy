package com.jbb.server.rs.services;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.server.common.Home;
import com.jbb.server.core.util.LenderesUtil;
import com.jbb.server.core.util.PropertiesUtil;
import com.jbb.server.core.util.SpringUtil;

/**
 * Application startup and shutdown listener
 * 
 * @author Vincent Tang
 */
@WebListener
public class ApplicationEventListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(ApplicationEventListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Application started");
        PropertiesUtil.init();
        LenderesUtil.init();
        Home.getHomeDir();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        PropertiesUtil.destroy();
        LenderesUtil.destroy();
        SpringUtil.close();
        Home.shutdown();
        logger.info("Application destroyed");

    }

}
