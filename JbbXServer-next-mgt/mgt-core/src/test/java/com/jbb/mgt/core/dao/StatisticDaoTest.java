package com.jbb.mgt.core.dao;

import com.jbb.server.common.Home;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

import static org.junit.Assert.*;

/**
 * @author wyq
 * @date 2018/6/28 11:03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class StatisticDaoTest {

    @Autowired
    private StatisticDao statisticDao;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void selectReturnAmount() throws Exception {
        int i = statisticDao.selectReturnAmount(18, null, null);
        assertTrue(i==0);
    }

    @Test
    public void selectDueAmount() throws Exception {
        int i = statisticDao.selectDueAmount(18, null,null);
        assertTrue(i==0);
    }

    @Test
    public void selectAuditAmount() throws Exception {
        Integer i = statisticDao.selectAuditAmount(18, null,null);
        assertTrue(i==0);
    }

    @Test
    public void selectIntotAmount() throws Exception {
        int i = statisticDao.selectIntoAmount(188, null,null);
        assertTrue(i== 0);
    }
    @Test
    public void selectLoannumber() throws Exception {
        int i = statisticDao.selectLoanNumber(19, new Timestamp(1527782400000L),null);
        assertTrue(i==0);
    }
    @Test
    public void selectloanAmount() throws Exception {
        int i = statisticDao.selectLoanAmount(18, new Timestamp(1527868800000L),null);
        assertTrue(i==0);
    }

}