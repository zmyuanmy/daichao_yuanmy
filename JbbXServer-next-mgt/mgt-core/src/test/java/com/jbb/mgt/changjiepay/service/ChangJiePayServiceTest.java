package com.jbb.mgt.changjiepay.service;

import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.helipay.service.QuickPayService;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.Home;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class ChangJiePayServiceTest {

    @Autowired
    private ChangJieQuickPayService changJieQuickPayService;

    @Autowired
    private ChangJieTransferService changJieTransferService;

    private static String cardName = "唐文华";
    private static String idcard = "430426198606056175";
    private static String cardNo = "6230583000001622325";
    private static String phone = "18575511205";
    private static String userId = "T100011";
    private static String msgCode = "660843";

    /*private static String cardName = "孙志颖";
    private static String idcard = "42098419941002751X";
    private static String cardNo = "6216881031003271967";
    private static String phone = "18607142580";
    private static String userId = "100002580";
    private static String msgCode  = "421209";*/

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    // 1.1 鉴权绑卡请求
    public void testBizApiAuthReq() throws Exception {
        String orderId = HeliUtil.generateOrderId();
        changJieQuickPayService.bizApiAuthReq(userId, orderId, idcard, cardNo, phone, cardName, "", "");
    }

    // 1.2鉴权绑卡确认接口
    public void testApiAuthSms() throws Exception {

        changJieQuickPayService.apiAuthSms("20181010193834871", "079928");
    }

    // 1.4 绑卡查询接口
    public void testApiAuthInfoQry() throws Exception {
        changJieQuickPayService.apiAuthInfoQry(userId);
    }

    // 1.5 支付请求接口
    public void testBizApiQuickPayment() throws Exception {
        String amount = "0.01";
        String orderId = HeliUtil.generateOrderId();
        changJieQuickPayService.bizApiQuickPayment(userId, cardNo, amount, orderId);
    }

    // 1.6 支付确认接口
    public void testApiQuickPaymentSmsConfirm() throws Exception {
        changJieQuickPayService.apiQuickPaymentSmsConfirm("20181015105946681", "660843");
    }

    // transfer 余额查询
    public void testQueryBalance() throws Exception {
        changJieTransferService.queryBalance("", "");
    }

    // transfer 异步单笔代付
    public void testTransfer() throws Exception {
        //changJieTransferService.transfer(cardNo, cardName, "0.01", "PINGAN", 19, 1);
    }

    // transfer 直接支付
    public void testQuickPayCard() throws Exception {
        String orderId = HeliUtil.generateOrderId();
        changJieQuickPayService.apiQuickPayment(userId, idcard, cardNo, "200.00", orderId, cardName, phone);
    }

}
