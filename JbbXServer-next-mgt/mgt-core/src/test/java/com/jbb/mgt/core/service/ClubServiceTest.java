package com.jbb.mgt.core.service;

import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.List;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.dao.ClubReportDao;
import com.jbb.mgt.core.domain.ClubQueryReportResponse;
import com.jbb.mgt.core.domain.ClubReport;
import com.jbb.mgt.core.service.impl.ClubServiceImpl;
import com.jbb.mgt.server.core.util.SpringUtil;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class ClubServiceTest {

    @Autowired
    private ClubService clubService;

    @Autowired
    private ClubReportDao clubReportDao;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testQueryTask() {
        String taskId = "TASKYYS100000201810081432020711034023";
        ClubReport report = clubService.getReport(taskId);
        assertNotNull(report);
    }

    // @Test
    public void testSaveNotify() {
        String event = "SUCCESS";
        String type = "ACQUIRE";
        String time = "2018-10-08 17:34:24";
        String data =
            "{\"code\":0,\"data\":{\"identity_code\":\"622201198601154824\",\"created_time\":\"2018-10-08 17:33:31\",\"channel_src\":\"中国移动\",\"user_mobile\":\"18366152089\",\"task_data\":null,\"user_name\":\"18366152089\",\"real_name\":\"何小娟\",\"channel_code\":\"100000\",\"channel_type\":\"YYS\",\"channel_attr\":\"山东\",\"lost_data\":null},\"task_id\":\"TASKYYS100000201810081733310711033390\",\"message\":\"成功\"}";
        String sign = "";
        String params = "641419";
        clubService.saveNotify(event, type, time, data, sign, params);

        // String taskId = "TASKYYS100000201810081556150720986863";
        // ClubReport report = clubService.getReport(taskId);
        // assertNotNull(report);

        // report = clubService.getReport(20, "YYS", null, null, null);
        // assertNotNull(report);
        //
        // report = clubService.getReport("430426198606056175", "唐文华", "YYS", null, "18620354020");
        // assertNotNull(report);
    }

     @Test
    public void testGetYysReportFromClub() {

        String[] tasks = {"TASKYYS100000201810260915090510160464"
        };

        int[] userIds = {897652};
        ClubServiceImpl s = SpringUtil.getBean("ClubService", ClubServiceImpl.class);
        for (int i = 0; i < tasks.length; i++) {
//            s.getYysReportFromClub(tasks[i], userIds[i]);
          
            
            s.saveReport(userIds[i], tasks[i]);
            s.getYysReportFromClub(tasks[i], userIds[i]);
        }

    }

    @Test
    public void testRefreshReport() throws InterruptedException {
        String startDate = "2018-10-26T09:00:00";
        String endDate = "2018-10-26T10:00:00";
        Timestamp startDateTs = Util.parseTimestamp(startDate, TimeZone.getDefault());
        Timestamp endDateTs = Util.parseTimestamp(endDate, TimeZone.getDefault());
        String[] channelTypes = {"YYS"};
        List<ClubReport> reports = clubReportDao.selectClubReports(startDateTs, endDateTs, channelTypes);
        ClubServiceImpl s = SpringUtil.getBean("ClubService", ClubServiceImpl.class);
//       String[] phones = {"15811836927","15821970153","18189272073","13656096066","13871174095"};
        for (ClubReport r : reports) {
//            if(!CommonUtil.inArray(r.getUsername(), phones)){
//                continue;
//            }
//            if (r.getUserId() == null) {
//                continue;
//            }
            s.saveReport(r.getUserId(), r.getTaskId());
//            s.getYysReportFromClub(r.getTaskId(), r.getUserId());
            Thread.sleep(1000);
        }

    }
}
