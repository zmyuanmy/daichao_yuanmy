package com.jbb.mgt.server.core.util;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.core.domain.mq.RegisterEvent;
import com.jbb.mgt.core.service.RegisterEventService;
import com.jbb.server.common.Constants;

public class RegisterEventUtil {
    private static Logger logger = LoggerFactory.getLogger(RegisterEventUtil.class);

    private static volatile RegisterEventUtil instance;
    private static final Object instanceLock = new Object();

    private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static ConcurrentHashMap<Integer, RegisterEvent> delayRegisterEvents = new ConcurrentHashMap<>(100);
    private static ConcurrentHashMap<Integer, ScheduledFuture<?>> handers = new ConcurrentHashMap<>(100);

    private volatile boolean shutdownInProgress;

    private static RegisterEventService registerEventService;

    private RegisterEventUtil() {
        logger.warn("RegisterEventUtil started");
    }

    private void shutdown() {
        shutdownInProgress = true;

        logger.warn("RegisterEventUtil stopped");
    }

    public static RegisterEventUtil init() {
        registerEventService = SpringUtil.getBean("RegisterEventService", RegisterEventService.class);
        synchronized (instanceLock) {
            if (instance == null) {
                logger.warn("New RegisterEventUtil");
                instance = new RegisterEventUtil();
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

    public static void addRegisterEventWithDelay(RegisterEvent event, long delay) {
        int userId = event.getUserId();
        // 先查找是否已经加入，如果存在，则返回
        ScheduledFuture<?> pHander = handers.get(userId);
        if (pHander != null) {
            logger.debug("exist user, userId=" + userId);
            return;
        }

        final ScheduledFuture<?> hander = scheduler.schedule(new Runnable() {
            public void run() {
                int userType = event.getUserType();

                if (userType == Constants.USER_TYPE_ENTRY) {
                    // 进件数据处理
                    logger.debug("process event to self orgs , userId=" + userId);
                    handers.remove(userId);
                    RegisterEvent registerEvent = delayRegisterEvents.get(userId);
                    // 第一档的数据，推荐给自己设置的组织
                    registerEventService.processEvent(registerEvent);
                } else {
                    // 注册数据处理
                    registerEventService.processEvent(event);
                }

                delayRegisterEvents.remove(userId);

            }
        }, delay, SECONDS);
        logger.debug("put user, userId=" + userId);
        handers.put(userId, hander);
        delayRegisterEvents.put(userId, event);
    }

    public static void removeSendTask(RegisterEvent event) {
        int userId = event.getUserId();
        ScheduledFuture<?> hander = handers.get(userId);
        if (hander != null) {
            logger.debug("cancel send user , userId=" + userId);
            hander.cancel(false);
        }
        logger.debug("remove user , userId=" + userId);
        handers.remove(userId);
        delayRegisterEvents.remove(userId);
    }

}
