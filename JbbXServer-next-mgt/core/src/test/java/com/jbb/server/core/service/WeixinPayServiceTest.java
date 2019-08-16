package com.jbb.server.core.service;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.dao.OrdersDao;
import com.jbb.server.core.domain.Order;
import com.jbb.server.core.util.PropertiesUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class WeixinPayServiceTest {
    @Autowired
    private WeixinPayService weixinPayService;

    @Autowired
    private OrdersDao orderDao;

    private static DefaultTransactionDefinition NEW_TX_DEFINITION =
        new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private PlatformTransactionManager txManager;

    @BeforeClass
    public static void oneTimeSetUp() {
        PropertiesUtil.init();
        Home.getHomeDir();
        Home.settingsTest();
    }

    @Test
    public void unifiedorder() {

        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);

            String orderNo = StringUtil.randomAlphaNum(32);
            WxPayAppOrderResult unifiedOrderResult =
                weixinPayService.unifiedorder(1000000, "auth", orderNo, "127.0.0.1", null);
            System.out.println(unifiedOrderResult);

            Order order = orderDao.selectOrder(orderNo);
            String timeEnd = "20180508122012";
            order.setPayDate(DateUtil.parseStr(timeEnd));
            orderDao.selectUserProductCountForUpdate(order.getUserId(), order.getProductName());
            orderDao.upadateOrder(order);
            orderDao.increaseUserProductCount(order.getUserId(), order.getProductName());
        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            // roll back not committed transaction
            rollbackTransaction(txStatus);
        }

    }

    private void rollbackTransaction(TransactionStatus txStatus) {
        if (txStatus == null) {
            return;
        }

        try {
            txManager.rollback(txStatus);
        } catch (Exception er) {
            er.printStackTrace();
        }
    }

    @Test
    public void testGetCnt() {
        int userId = 1000000;
        try {
            orderDao.insertUserProductCount(userId, "auth", 1);
        } catch (DuplicateKeyException e) {

        }
        int cnt = orderDao.selectUserProductCountForUpdate(userId, "auth");

        assertTrue(cnt > 0);
        
        cnt = orderDao.selectUserProductCountForUpdate(userId, "notExist");
        assertTrue(cnt == 0);

    }

}
