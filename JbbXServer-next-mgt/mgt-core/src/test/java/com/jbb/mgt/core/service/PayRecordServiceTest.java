package com.jbb.mgt.core.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.PayRecord;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class PayRecordServiceTest {

    @Autowired
    private PayRecordService payRecordService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testPayRecordService() {
        /*  PayRecord payRecord = new PayRecord("2018082801", "T200", 1000, "ICBC", "888888", "8888", "B2B", null, "PAYER",
            false, null, "123123", null, null, null, 0, 10003);
        payRecordService.insertPayRecord(payRecord);*/

        payRecordService.updatePayRecord("2018082801", "FAIL","");

        PayRecord record = payRecordService.selectByOrderId("2018082801");
        /*assertNotNull(record);*/

        PayRecord record1 = payRecordService.selectBySerialNumber("123123");
        assertNotNull(record1);

        PayRecord record2 = payRecordService.selectByApplyId("10003");
        assertNotNull(record2);
        /* System.out.println(record + "---" + record1 + "---" + record2);*/

    }

    @Test
    public void testUnfinalService() {
        String[] m = payRecordService.selectUnfinalRecord(10, 2);
        assertTrue(m.length > 0);
    }
}
