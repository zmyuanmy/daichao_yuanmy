package com.jbb.mgt.proc.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.core.domain.ChuangLanPhoneNumberRsp;
import com.jbb.mgt.core.domain.TeleMarketing;
import com.jbb.mgt.core.domain.TeleMarketingDetail;
import com.jbb.mgt.core.service.ChuangLanService;
import com.jbb.mgt.core.service.TeleMarketingDetailService;
import com.jbb.mgt.core.service.TeleMarketingService;
import com.jbb.mgt.server.core.util.SpringUtil;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.PhoneNumberUtil;

public class TelePhoneNumberProcessor {
    private static final Logger logger = LoggerFactory.getLogger(TelePhoneNumberProcessor.class);

    private static final TelePhoneNumberProcessor phoneNumberProcessor = new TelePhoneNumberProcessor();

    private volatile boolean stop = false;
    // private final int threadsNumber =
    // PropertyManager.getIntProperty("ppc.cloud.honeywell.event.threads", 10);
    // private final ExecutorService executor =
    // new ThreadPoolExecutor(threadsNumber, threadsNumber, 0L,
    // TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    private TeleMarketingService teleMarketingService
        = SpringUtil.getBean("TeleMarketingService", TeleMarketingService.class);
    private ChuangLanService chuangLanService = SpringUtil.getBean("ChuangLanService", ChuangLanService.class);
    private TeleMarketingDetailService teleMarketingDetailService
        = SpringUtil.getBean("TeleMarketingDetailService", TeleMarketingDetailService.class);

    private TelePhoneNumberProcessor() {}

    public static TelePhoneNumberProcessor getInstance() {
        return phoneNumberProcessor;
    }

    public void initialize() {
        logger.debug(">initialize()");

        boolean enable = PropertyManager.getBooleanProperty("jbb.mgt.cl.processor.enable", false);
        if (!enable) {
            logger.info("TelePhoneNumberProcessor is not enabled!");
        }

        new Thread(() -> processMobiles()).start();

        logger.debug("<initialize()");
    }

    private void processMobiles() {

        while (!stop) {

            //int limitSize = PropertyManager.getIntProperty("jbb.mgt.cl.processor.limitPerCall", 50);
            int initStatus = PropertyManager.getIntProperty("jbb.mgt.processor.validatemobile.initStatus", 5);
            List<TeleMarketing> teleMarketing = teleMarketingService.selectTeleMarkBystatus(3);

            if (teleMarketing.size()!=0) {
                for (int i = 0; i < teleMarketing.size(); i++) {
                    teleMarketing.get(i).setStatus(1);
                    teleMarketingService.updateTeleMarketing(teleMarketing.get(i));
                    int batchId = teleMarketing.get(i).getBatchId();
                    List<TeleMarketingDetail> list
                        = teleMarketingDetailService.selectTeleMarketingByStatus(initStatus, 0, batchId);
                    if (!CommonUtil.isNullOrEmpty(list)) {
                        for (TeleMarketingDetail mobileDetail : list) {
                            validateMobile(mobileDetail);
                        }
                    }
                    teleMarketing.get(i).setValidCnt(teleMarketingDetailService.getPhoneCount(batchId, 1));
                    teleMarketing.get(i).setStatus(2);
                    teleMarketingService.updateTeleMarketing(teleMarketing.get(i));
                }
            } else {
                try {
                    Thread.sleep(300000L);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    // teleMarketing.setStatus(1);
    // teleMarketingService.updateTeleMarketing(teleMarketing);

    // int batchId = teleMarketing.getBatchId();
    // TODO change 10 to 5 when prod

    private void validateMobile(TeleMarketingDetail phoneDetail) {
        logger.debug(">validateMobile, phoneDetail=" + phoneDetail);
        if (!PhoneNumberUtil.isValidPhoneNumber((phoneDetail.getTelephone()))) {
            phoneDetail.setStatus(7);
            teleMarketingDetailService.updateMobile(phoneDetail);
        } else {
            ChuangLanPhoneNumberRsp rsp = chuangLanService.validateMobile(phoneDetail.getTelephone(), false);
            phoneDetail.setArea(rsp.getData().getArea());
            phoneDetail.setChargesStatus(rsp.getData().getChargesStatus());
            phoneDetail.setNumberType(rsp.getData().getNumberType());
            phoneDetail.setStatus(Integer.parseInt(rsp.getData().getStatus()));
            phoneDetail.setLastDate(rsp.getData().getLastTime());
            teleMarketingDetailService.updateMobile(phoneDetail);
        }
        logger.debug("<validateMobile");
    }

    public void destroy() {
        logger.debug(">destroy()");
        stop = true;
        // try {
        // executor.shutdown();
        // executor.awaitTermination(3000000L / threadsNumber,
        // TimeUnit.MILLISECONDS);
        // } catch (InterruptedException ignore) {
        //
        // }

        logger.debug("<destroy()");
    }

}
