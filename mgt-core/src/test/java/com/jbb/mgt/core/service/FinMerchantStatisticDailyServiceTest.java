package com.jbb.mgt.core.service;

import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.FinMerchantStatisticDaily;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class FinMerchantStatisticDailyServiceTest {

    @Autowired
    private FinMerchantStatisticDailyService dailyService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testInsertMerchant() {
        String startDate = "2018-09-30";
        String endDate = "2018-10-01";
        Timestamp startDateTs = Util.parseTimestamp(startDate, TimeZone.getDefault());
        Timestamp endDateTs = Util.parseTimestamp(endDate, TimeZone.getDefault());
        List<FinMerchantStatisticDaily> list = dailyService.selectEventLogByEventValue(startDateTs, endDateTs);
        if (list.size() != 0) {
            for (FinMerchantStatisticDaily merchant : list) {
                if (merchant != null && merchant.getStatisticDate() != null && merchant.getMerchantId() > 0) {
                    FinMerchantStatisticDaily merchantStatistic
                        = dailyService.selectMerchantByStaDate(merchant.getMerchantId(),
                            (new SimpleDateFormat("yyyy-MM-dd")).format(merchant.getStatisticDate()), null);
                    int balance = null != merchantStatistic ? merchantStatistic.getBalance() : 0;
                    merchant.setBalance(balance + merchant.getAmount() - merchant.getExpense());
                    dailyService.insertMerchant(merchant);
                }
            }
        }
    }

    @Test
    public void testFinMerchantStatisticDaily() {
        // 初始数据生成
        /*  FinMerchantStatisticDaily merchant = new FinMerchantStatisticDaily(new Date(), 1, 10, 10, 100, 100, 0, 0,
            DateUtil.getCurrentTimeStamp(), DateUtil.getCurrentTimeStamp(), null, 0, 0, 0);
        dailyService.insertMerchant(merchant);*/
        List<FinMerchantStatisticDaily> list = dailyService.selectMerchantByDate(new Date().toString());
        assertNotNull(list);

        List<FinMerchantStatisticDaily> merchantList
            = dailyService.selectMerchantById(1, DateUtil.getCurrentTimeStamp(), null);
        assertNotNull(merchantList);
    }

    @Test
    public void testMerchantDaily() {
        int[] mId = {1, 2, 3};
        List<FinMerchantStatisticDaily> list = dailyService.selectMerchantDaily(mId, "2017-07-24", "2017-07-30");
        assertNotNull(list);
        List<FinMerchantStatisticDaily> list1 = dailyService.selectMerchantCompare(mId, "2017-07-24", "2017-07-30");
        assertNotNull(list1);
    }

    @Test
    public void testUpdateMerchantDaily() {
        ArrayList<FinMerchantStatisticDaily> list = new ArrayList<>();
        String time = "2018-08-15" + " 00:00:00";
        String time2 = "2018-08-16" + " 00:00:00";
        java.sql.Date statisticDateParm = new java.sql.Date(DateUtil.parseStrnew(time).getTime());
        java.sql.Date statisticDateParm2 = new java.sql.Date(DateUtil.parseStrnew(time2).getTime());
        FinMerchantStatisticDaily f = new FinMerchantStatisticDaily();
        f.setStatisticDate(statisticDateParm);
        f.setMerchantId(11);
        list.add(f);
        FinMerchantStatisticDaily f2 = new FinMerchantStatisticDaily();
        f2.setStatisticDate(statisticDateParm2);
        f2.setMerchantId(1);
        list.add(f2);
        dailyService.updateMerchantList(list);
    }

}
