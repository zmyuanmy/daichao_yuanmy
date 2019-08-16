package com.jbb.mgt.core.service;

import com.alibaba.fastjson.JSON;
import com.jbb.mgt.core.domain.StatisticChannelDaily;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.List;
import java.util.TimeZone;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class StatisticChannelDailyServiceTest {

    @Autowired
    private StatisticChannelDailyService service;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void getStatisticChannelDailyByChannelCode() {

        String startDateStr = "2019-01-14";
        // String endDate = "2018-09-20";
        Timestamp startDateTs = Util.parseTimestamp(startDateStr, TimeZone.getDefault());
        for (int i = 0; i < 1; i++) {
            Timestamp startDate = new Timestamp(startDateTs.getTime() + i * DateUtil.DAY_MILLSECONDES);
            Timestamp endDate = new Timestamp(startDateTs.getTime() + (i + 1) * DateUtil.DAY_MILLSECONDES);
//
            List<StatisticChannelDaily> list =
                service.getStatisticChannelDailyByChannelCode(null, startDate, endDate, 1, 1);
            System.out.println("==========" + JSON.toJSONString(list));
            service.insertStatisticChannelDaily(list);

            list = service.getStatisticChannelDailyByChannelCode(null, startDate, endDate, 2, 1);
            System.out.println("==========" + JSON.toJSONString(list));
            service.insertStatisticChannelDaily(list);

             list = service.getStatisticChannelDailyByChannelCode(null, startDate, endDate, 3, 1);
            System.out.println("==========" + JSON.toJSONString(list));
            service.insertStatisticChannelDaily(list);

            // list = service.getStatisticChannelDailyByChannelCode(null, startDate, endDate, 1, 1);
            // service.insertStatisticChannelDaily(list);
            // System.out.println("==========" + JSON.toJSONString(list));

        }

    }
}
