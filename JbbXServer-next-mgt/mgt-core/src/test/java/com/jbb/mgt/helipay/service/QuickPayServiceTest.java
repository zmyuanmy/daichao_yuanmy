package com.jbb.mgt.helipay.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.Home;
import com.jbb.server.common.PropertyManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class QuickPayServiceTest {

    @Autowired
    private QuickPayService quickPayService;

    private static String cardName = "唐文华";
    private static String idcard = "430426198606056175";
    private static String cardNo = "6230583000001622325";
    private static String phone = "18575511205";
    private static String userId = "test001";

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    /**
     * 4.1 创建订单
     *
     * @throws Exception
     */
    public void testCreateOrder() throws Exception {

        String orderId = HeliUtil.generateOrderId();

        double amount = 1.05;
        String orderIp = "192.168.0.1";
        String terminalId = "xxxxx";
        quickPayService.quickPayCreateOrder(userId, orderId, cardName, idcard, cardNo, phone,
            HeliUtil.formatAmount(amount), orderIp, terminalId);

    }

    public void testAgreementPayBindCardValidateCode() throws Exception {

        String orderId = HeliUtil.generateOrderId();
        System.out.println("orderId= " + orderId);
        quickPayService.agreementPayBindCardValidateCode(userId, orderId, phone, cardNo, cardName, idcard);
    }

    public void testquickPayBindCard() throws Exception {

        String orderId = "20180906143237961";
        String userId = "55";
        String msgCode = "997226";

        quickPayService.quickPayBindCard(orderId, userId, idcard, cardName, cardNo, msgCode, phone);
    }

    public void testquickPayCreateOrder() throws Exception {

        String orderId = HeliUtil.generateOrderId();
        String userId = "55";
        quickPayService.quickPayCreateOrder(userId, orderId, cardName, idcard, cardNo, phone, "1.01", "127.0.0.1",
            "000111");
        System.out.println("orderId " + orderId);
    }

    public void testquickPaySendValidateCode() throws Exception {

        String orderId = "20181024102903618";
        quickPayService.quickPaySendValidateCode(orderId, phone);
    }

    public void testquickPayConfirmPay() throws Exception {

        String orderId = "20180906182458397";
        String msgCode = "315104";
        String ipAddress = "127.0.0.1";
        quickPayService.quickPayConfirmPay(orderId, msgCode, ipAddress);
        System.out.println("orderId " + orderId);
    }

}
