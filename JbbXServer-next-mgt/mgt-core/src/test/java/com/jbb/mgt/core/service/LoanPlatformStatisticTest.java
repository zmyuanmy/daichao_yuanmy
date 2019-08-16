package com.jbb.mgt.core.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.TimeZone;

import com.jbb.mgt.core.domain.LoanPlatformStatistic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class LoanPlatformStatisticTest {

    @Autowired
    private LoanPlatformReportService loanPlatformReportService;

    /*
     * 该测试类为贷超充消报表即mgt_loan_platform_statistic的数据插入方法
     * 参数为startDate，endDate,calculateFlag
     * 1.当mgt_platform_statistic表中对应的日期存在时,
     *     a. 如果表mgt_loan_platform_statistic中数据不存在,会插入数据,不会计算余额，消耗等数据;
     *     b. 当mgt_loan_platform_statistic表对应数据存在,只会更新uv和点击数
     * 2.当mgt_platform_statistic表中对应的日期不存在时,
     *      会执行a和b两种情况,
     *      同時：
     *      当calculateFlag为false,会按照uv数对应给相应记录计算puv(产品uv-->uv)和cnt(注册数-->uv/2),
     *          同时会计算消耗和余额,主要应对每天隔一段时间的跑批任务,供商务每日参考;
     *      当calculateFlag为true,会按照uv数对应给相应记录计算puv(产品uv-->uv),cnt(注册数)会初始化为0,
     *          同时会计算消耗和余额,同時在表mgt_platform_statistic插入数据,主要应对每天凌晨对前一日数据的跑批任务。
     *          
     *  该方法中Thread.sleep(60000L)代码，目的为了调用异步线程计算余额。
     */
    // @Test
    public void testRunLoanPlatform() {
        //
        String date = "2019-06-05";
        Timestamp begin = Util.parseTimestamp(date, TimeZone.getDefault());
        for (int i = 0; i < 1; i++) {
            Timestamp startDate = new Timestamp(begin.getTime() + i * DateUtil.DAY_MILLSECONDES);
            Timestamp endDate = new Timestamp(begin.getTime() + (i + 1) * DateUtil.DAY_MILLSECONDES);
            // flag 是否计算余额
            loanPlatformReportService.runLoanPlatFormStatistic(startDate, endDate, true);
            /*  try {
                loanPlatformReportService.runLoanPlatFormStatistic(startDate, endDate, true);
                // sleep为了调用异步线程计算余额
                Thread.sleep(60000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

    }

    // @Test
    public void testRunLoanPlatformDetail() {

        String date = "2019-03-29";
        Timestamp begin = Util.parseTimestamp(date, TimeZone.getDefault());
        for (int i = 0; i < 2; i++) {
            Timestamp startDate = new Timestamp(begin.getTime() + i * DateUtil.DAY_MILLSECONDES);
            Timestamp endDate = new Timestamp(begin.getTime() + (i + 1) * DateUtil.DAY_MILLSECONDES);
            loanPlatformReportService.runLoanPlatFormDetailStatistic(startDate, endDate);
        }

    }

    // @Test
    public void testSelectStatistic() {
        String startDate = "2018-09-10";
        String endDate = "2018-09-30";
        List<LoanPlatformStatistic> loanPlatformStatistics
            = loanPlatformReportService.selectLoanPlatformDetailStatisticByStatisticDate(startDate, endDate, null);
    }

}
