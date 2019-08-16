package com.jbb.mgt.core.service;

import com.alibaba.fastjson.JSON;
import com.jbb.mgt.core.dao.mapper.MgtFinOrgStatisticDailyMapper;
import com.jbb.mgt.core.domain.MgtFinOrgStatisticDaily;
import com.jbb.mgt.core.domain.OrgStatisticDailyInfo;
import com.jbb.mgt.core.domain.OrganizationIncludeSales;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class MgtFinOrgStatisticDailyServiceTest {

    @Autowired
    private MgtFinOrgStatisticDailyService mgtFinOrgStatisticDailyService;

    @Autowired
    protected MgtFinOrgStatisticDailyMapper mgtFinOrgStatisticDailyMapper;

    @Autowired
    @Qualifier("FinOrgEntryStatisticServices")
    private FinOrgStatisticServices finOrgEntryStatisticServices;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    //@Test
    public void testInsertMgtFinOrgStatisticDaily() {

        String startDate = "2018-09-20";
        String endDate = "2018-09-21";

        Timestamp startDateTs = Util.parseTimestamp(startDate, TimeZone.getDefault());
        Timestamp endDateTs = Util.parseTimestamp(endDate, TimeZone.getDefault());
        mgtFinOrgStatisticDailyService.insertMgtFinOrgStatisticDailyAll(null, null, startDateTs, endDateTs);
    }

    @Test
    public void testInsertMgtFinOrgAd() {

        
        String startDateStr = "2018-09-26";
        //String endDate = "2018-09-20";
        Timestamp startDateTs = Util.parseTimestamp(startDateStr, TimeZone.getDefault());
        for(int i = 0;i<1;i++){
            Timestamp startDate = new Timestamp(startDateTs.getTime() + i*DateUtil.DAY_MILLSECONDES);
            Timestamp endDate  = new Timestamp(startDateTs.getTime() + (i+1)*DateUtil.DAY_MILLSECONDES);
            mgtFinOrgStatisticDailyService.insertMgtFinOrgStatisticDailyAd(3, null, startDate, endDate);
        }
     
    }

    // @Test
    public void testSelect() {
        Timestamp startDate = new Timestamp(DateUtil.getTodayStartCurrentTime() - DateUtil.DAY_MILLSECONDES * 20);
        Timestamp endDate = new Timestamp(DateUtil.getTodayStartCurrentTime());
        Date startDate1 = new Date(startDate.getTime());
        Date endDate1 = new Date(endDate.getTime());
        List<OrgStatisticDailyInfo> orgStatisticDailyInfos
            = mgtFinOrgStatisticDailyMapper.selectMgtFinOrgStatisticDaily(null, 2, startDate1, endDate1, 10116);
        JSON json = (JSON)JSON.toJSON(orgStatisticDailyInfos);
        System.out.println("json " + json);
    }

    public void testSelectNow() {
        String time = "2018-7-25" + " 00:00:00";
        List<OrganizationIncludeSales> organizationIncludeSales
            = mgtFinOrgStatisticDailyMapper.selectMgtFinOrgStatisticDailyNow(1, false, true, DateUtil.parseStrnew(time),
                DateUtil.getCurrentTimeStamp(), null, false);
        JSON json = (JSON)JSON.toJSON(organizationIncludeSales);
        System.out.println("json " + json);
    }

    // @Test
    public void testService() {
        String time = "2018-7-23" + " 00:00:00";
        Date statisticDateParm = new Date(DateUtil.parseStrnew(time).getTime());
        finOrgEntryStatisticServices.getOrgStatistics(statisticDateParm, 1);
    }

    @Test
    public void tesUpdate() {
        String time = "2018-08-15" + " 00:00:00";
        Date statisticDateParm = new Date(DateUtil.parseStrnew(time).getTime());
         List<MgtFinOrgStatisticDaily> list = new ArrayList<>();
         MgtFinOrgStatisticDaily mf = new MgtFinOrgStatisticDaily();
         mf.setStatisticDate(statisticDateParm);
         mf.setOrgId(2);
         mf.setType(1);
         list.add(mf);
         MgtFinOrgStatisticDaily mf2 = new MgtFinOrgStatisticDaily();
         mf2.setStatisticDate(statisticDateParm);
         mf2.setOrgId(2);
         mf2.setType(2);
         list.add(mf2);
         mgtFinOrgStatisticDailyService.updateMgtFinOrgStatisticDailyList(list);
    }

}
