package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.DataFlowSetting;
import com.jbb.mgt.core.domain.HumanLpPhone;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class DunXingServiceTest {

    @Autowired
    private DunXingMsgLogService dunXingMsgLogService;

    @Autowired
    private HumanLpPhoneService humanLpPhoneService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testChannelService() {
        dunXingMsgLogService.sendMsgCode(1,"18607142580","0000","74");
    }

    @Test
    public void testUpdateHumanLp() {
        humanLpPhoneService.updateHumanLpPhoneSendStatus("18607142512", "2",DateUtil.getCurrentTimeStamp());
    }


    @Test
    public void testSelectHumanLp() {
        Timestamp startTime = DateUtil.parseStrnew("2018-10-26"+" 00:00:00");
       List<HumanLpPhone> humanLpPhones =  humanLpPhoneService.selectHumanLpPhoneList("2512", startTime, null);
    }


    public void testUpdateLog() {
        dunXingMsgLogService.updateDunXingMsgLog("20181025141010447", "2", "测试错误信息");
    }


    @Test
    public void testSendMsgNew() {
        String phoneNumber = "18607142580";
        String inviteCode = "0000";
        dunXingMsgLogService.sendMsgCodeNew(1,phoneNumber, inviteCode);
    }


}
