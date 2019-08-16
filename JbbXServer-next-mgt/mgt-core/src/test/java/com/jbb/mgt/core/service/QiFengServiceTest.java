package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.HumanLpPhone;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class QiFengServiceTest {

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Autowired
    private QiFengService qiFengService;

    @Autowired
    private HumanLpPhoneService humanLpPhoneService;

    @Test
    public void testqiFengCustAdd() {
        List<HumanLpPhone> humanLpPhones = new ArrayList<HumanLpPhone>();
        HumanLpPhone humanLpPhone = new HumanLpPhone("13509870987", "测试19", "男", DateUtil.getCurrentTimeStamp());
        HumanLpPhone humanLpPhone1 = new HumanLpPhone("13409870987", "测试20", "女", DateUtil.getCurrentTimeStamp());
        humanLpPhones.add(humanLpPhone);
        humanLpPhones.add(humanLpPhone1);
        qiFengService.qiFengCustAdd(humanLpPhones);
    }

    @Test
    public void testCustAdd() {
        String startDate = "2018-10-25 20:15:00";
        String endDate = "2018-10-25 20:40:00";
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Timestamp tsStartDate = ts.valueOf(startDate);
        Timestamp tsEndDate = ts.valueOf(endDate);
        List<HumanLpPhone> humanLpPhones
            = humanLpPhoneService.getHumanLpPhoneByStatus(tsStartDate, tsEndDate, false, null);

        int init = 10;
        int total = humanLpPhones.size();
        int num = (total % init) == 0 ? total / init : total / init + 1;
        for (int i = 0; i < num; i++) {
            List<HumanLpPhone> humanLpPhones1 = new ArrayList<HumanLpPhone>();
            int start = i * init;
            int end = (i + 1) * init > total ? total : (i + 1) * init;
            humanLpPhones1 = humanLpPhones.subList(start, end);
            qiFengService.qiFengCustAdd(humanLpPhones1);
        }
        humanLpPhoneService.updateHumanLpPhone(tsStartDate, tsEndDate);
    }

    @Test
    public void testAdd() {
        System.out.println("..." + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(DateUtil.getTodayStartTs()));
        System.out
            .println("..." + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(DateUtil.getTodayStartCurrentTime()));
    }
}
