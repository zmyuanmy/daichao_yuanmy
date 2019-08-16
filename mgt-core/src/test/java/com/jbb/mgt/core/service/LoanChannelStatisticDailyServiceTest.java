package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.LoanChannelStatistic;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.List;
import java.util.TimeZone;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class LoanChannelStatisticDailyServiceTest {

    @Autowired
    private LoanChannelStatisticDailyService loanChannelStatisticDailyService;

//    @Test
    public void testRunLoanChannelStatisticDaily(){
      
        
        String date = "2019-03-31";
        Timestamp begin = Util.parseTimestamp(date, TimeZone.getDefault());
        for (int i = 0; i < 12; i++) {
            Timestamp startDate = new Timestamp(begin.getTime() + i * DateUtil.DAY_MILLSECONDES);
            Timestamp endDate = new Timestamp(begin.getTime() + (i + 1) * DateUtil.DAY_MILLSECONDES);
            loanChannelStatisticDailyService.runLoanChannelStatisticDaily(startDate, endDate);
        }
    }
    
    
    @Test
    public void testSelectLoanChannelCompare(){
       List<LoanChannelStatistic> loanChannelStatistics =  loanChannelStatisticDailyService.selectLoanChannelStatisticCompare("2018-10-14", "2018-10-15");
//       assertNotNull(loanChannelStatistics);
//       
//       loanChannelStatistics.forEach(s->{
//           System.out.println(s);
//       });
    }

    @Test
    public void testSelectLoanChannelByChannelCode(){
        List<LoanChannelStatistic> loanChannelStatistics = loanChannelStatisticDailyService.selectLoanChannelStatisticByChannelCode(null, "2018-09-10",null);
    }

}
