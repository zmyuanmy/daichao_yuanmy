package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.StatisticFeedbackDaily;
import com.jbb.mgt.core.domain.StatisticFeedbackDetailDaily;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateFormatUtil;
import com.jbb.server.common.util.DateUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class StatisticFeedbackDailyServiceTest {

    @Autowired
    private StatisticFeedbackDailyService service;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void feedbackDaily() {
        String startS = "2018-08-16";
        String endS = "2018-08-17";
        Date startDate = DateFormatUtil.stringToDate(startS);
        Date endDate = DateFormatUtil.stringToDate(endS);

        List<StatisticFeedbackDaily> list = service.selectStatisticFeedbackDaily(startDate, endDate);
        List<StatisticFeedbackDetailDaily> list2 = service.selectStatisticFeedbackDetailDaily(startDate, endDate);
        if (list.size() > 0) {
            service.insertStatisticFeedbackDaily(list);
        }
        if (list2.size() > 0) {
            service.insertStatisticFeedbackDetailDaily(list2);
        }

    }

    // @Test
    public void selectFeedbackDaily() {
        List<StatisticFeedbackDaily> list = service.selectStatisticFeedbackDailyByDateAndUserType(
            DateFormatUtil.stringToDate("2018-06-06"), DateFormatUtil.stringToDate("2018-06-09"), null, 1);
    }

    /*@Test
    public void insertStatisticChannelDetailDaily() throws ParseException {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = new Date(DateUtil.getCurrentTime());
        Calendar date = Calendar.getInstance();
        date.setTime(endDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
        Date startDate = null;
        java.util.Date startDate2 = dft.parse(dft.format(date.getTime()));
        startDate = new Date(startDate2.getTime());
    
        List<StatisticChannelDetailDaily> list1
            = dailyService.selectStatisticChannelDetailDaily(startDate, endDate, null, 1, 1);
        *//*= dailyService.selectStatisticChannelDetailDaily(new Date(dft.parse("2018-06-06 ").getTime()),
              new Date(dft.parse("2018-06-09").getTime()), null, 1, 1);*/
    /*
       List<StatisticChannelDetailDaily> list3 = dailyService.selectHaveFriend(startDate, endDate,
       null, 1, 1);
       if (list1.size() > 0 && list3.size() > 0) {
       for (int i = 0; i < list1.size(); i++) {
       for (int j = 0; j < list3.size(); j++) {
       if (list1.get(i).getStatisticDate().getTime() == list3.get(j).getStatisticDate().getTime()
       && list1.get(i).getChannelCode().equals(list3.get(j).getChannelCode())
       && list1.get(i).getUserType() == list3.get(j).getUserType()) {
       list1.get(i).setReason(list3.get(j).getReason());
       list1.get(i).setCnt(list3.get(j).getCnt());
       list3.remove(j);
       j--;
       }
       }
       }
       service.insertStatisticChannelDetailDaily(list1);
       }
    
    
    
       List<StatisticChannelDetailDaily> list2
       = dailyService.selectStatisticChannelDetailDaily(startDate, endDate, null, 0, 1);
       List<StatisticChannelDetailDaily> list4 = dailyService.selectHaveFriend(startDate, endDate,
       null, 0, 1);
       if (list2.size() > 0 && list4.size()> 0) {
       for (int i = 0; i < list2.size(); i++) {
       for (int j = 0; j < list4.size(); j++) {
       if (list2.get(i).getStatisticDate().getTime() == list4.get(j).getStatisticDate().getTime()
       && list2.get(i).getChannelCode().equals(list4.get(j).getChannelCode())
       && list2.get(i).getUserType() == list4.get(j).getUserType()) {
       list2.get(i).setReason(list4.get(j).getReason());
       list1.get(i).setCnt(list4.get(j).getCnt());
       list3.remove(j);
       j--;
       }
       }
       }
       service.insertStatisticChannelDetailDaily(list2);
       }
       }
    */
}
