package com.jbb.mgt.core.service;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.FinOrgAdDelStatisticDaily;
import com.jbb.mgt.core.domain.FinOrgDelStatisticDaily;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateFormatUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class FinOrgDelStatisticDailyServiceTest {

    @Autowired
    private FinOrgDelStatisticDailyService service;
    @Autowired
    private FinAdStatisticDailyService finAdStatisticDailyService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void insertMgtFinOrgStatisticDaily() {
        String startDate = "2018-09-19";
        String endDate = "2018-09-20";
        service.selectOrgDelStatistic(startDate, endDate);
    }

   // @Test
    public void testInsertMgtFinOrgStatisticDaily() {
        FinOrgDelStatisticDaily mf = new FinOrgDelStatisticDaily();
        mf.setStatisticDate(DateFormatUtil.stringToDate("2018-01-02"));
        mf.setOrgId(1);
        service.saveFinOrgDelStatisticDaily(mf);
        // FinOrgDelStatisticDaily f = service.getFinOrgDelStatisticDaily(DateFormatUtil.stringToDate("2018-01-01"), 1);
        // List<FinOrgDelStatisticDaily> fo = service.getFinOrgDelStatisticDailys(1, "2018-01-01", "2018-01-02");
    }

    @Test
    public void test() {
        List<FinOrgAdDelStatisticDaily> list
            = finAdStatisticDailyService.selectFinAdStatisticDaily(null, "2018-08-01", null);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("================" + list.get(i));
        }

    }

}
