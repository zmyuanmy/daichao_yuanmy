package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.LoanTotal;
import com.jbb.mgt.core.domain.PayRecord;
import com.jbb.mgt.core.domain.StatusCnt;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.domain.XjlApplyRecordOpLog;
import com.jbb.mgt.core.domain.XjlApplyRecordStatistic;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class XjlapplyRecordServiceTest {

    @Autowired
    private XjlApplyRecordService service;

    @Autowired
    private XjlApplyRecordOpLogService opservice;

    @Autowired
    private XjlAntFraudResultService xjlAntFraudResultService;

    @Autowired
    private XjlRemindMsgCodeService codeService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testChannelService() {
        service.deleteXjlApplyRecord("1");
        XjlApplyRecord x1 = service.getXjlApplyRecordByApplyId("1", null, false);
        Assert.assertTrue(null == x1);
        XjlApplyRecord xa = new XjlApplyRecord();
        xa.setApplyId("1");
        service.saveXjlApplyRecord(xa);
        XjlApplyRecord x2 = service.getXjlApplyRecordByApplyId("1", null, false);
        Assert.assertTrue(null != x2);
        xa.setAmount(1);
        service.updateXjlApplyRecord(xa);
        XjlApplyRecord xaj = service.getXjlApplyRecordByApplyId("1", null, false);
        Assert.assertTrue(xaj.getAmount() == 1);
        service.deleteXjlApplyRecord("1");
    }

    @Test
    public void testInsertXjlApplyRecordOpLog() {
        XjlApplyRecordOpLog xo = new XjlApplyRecordOpLog();
        xo.setAccountId(1);
        xo.setApplyId("1");
        xo.setOpId(1);
        xo.setOpType(1);
        opservice.saveXjlApplyRecordOpLogs(xo);
    }

    @Test
    public void testList() {
        Timestamp startDate = DateUtil.parseStrnew("2000-08-01 17:26:32");
        Timestamp endDate = DateUtil.parseStrnew("2019-08-24 17:26:39");
        // List<XjlApplyRecord> list
        // = service.selectXjlApplyRecords(false, null,
        // new String[]{"0", "2", "3", "4", "5", "99"}, null, null, null, 2);
        /*   List<XjlApplyRecord> list = service.selectXjlApplyRecords(false, null,
            new String[] {"0", "2", "3", "4", "5", "99"}, null, null, null, 2);*/
    }

    @Test
    public void testlist2() {
        List<StatusCnt> statusCnts = service.selectStatusCnts(null, null, null, null, null, 2, null, null, null);
    }

    @Test
    public void testlist3() {
        LoanTotal statusCnts = service.selectXjlApplyRecordsTotal(null, new String[] {"0", "2", "3", "4", "5", "99"},
            null, null, null, 2);
    }

    @Test
    public void selectXjlPerformance() {
        List<XjlApplyRecordStatistic> list = service.getXjlPerformance(2, null, null);
    }

    @Test
    public void checkApplyPull() {
        // boolean b = service.checkApplyPull(29, 1, "toReceive2");
        // boolean a = service.checkXjlApplyExist(29, 1);
        // System.out.println(b + "==========================" + a);
        // service.saveApplyPull(29, 1, "toReceive2");
        List<XjlApplyRecord> s = service.selectXjlApplyRecords(false, null, null, null, null, null, 1, 1, null, "xjl",
            null, null, "toFinalPull", null);
    }

    @Test
    public void test5() {
        String[] str = {"2", "3", "4"};
        for (String id : str) {
            service.updateCollectorAccId(String.valueOf(id), 99);
        }

    }

    @Test
    public void test6() {
        xjlAntFraudResultService.editXjlFraudResult(77, "Test!");
    }

    @Test
    public void testStatus() {
        Timestamp startDate = DateUtil.getCurrentTimeStamp();
        String date = "2018-10-31";
        codeService.getXjlApplyRecordsByStatus(3, date, 1, null);
        codeService.getXjlApplyRecordsByStatus(4, date, 1, 7);
        codeService.getXjlApplyRecordsByStatus(5, date, null, null);

    }

    @Test
    public void testLoanSendCode() {
        PayRecord payRecord = new PayRecord();
        payRecord.setAccountId(100149);
        XjlApplyRecord xjlApply
            = new XjlApplyRecord("57", 10441, 333, null, 50000, 3500, null, null, null, null, null, null, null);
        codeService.loanSendCode(payRecord, xjlApply);
    }

}
