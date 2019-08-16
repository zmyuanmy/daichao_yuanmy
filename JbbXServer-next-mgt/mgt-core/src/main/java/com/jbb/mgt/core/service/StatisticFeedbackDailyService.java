package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.StatisticFeedbackDaily;
import com.jbb.mgt.core.domain.StatisticFeedbackDetailDaily;

import java.util.Date;
import java.util.List;

/**
 * 渠道反馈 Service接口
 *
 * @author wyq
 * @date 2018/8/8 20:00
 */
public interface StatisticFeedbackDailyService {

    /**
     * mgt_statistic_feedback_detail_daily 插入数据
     * 
     * @param list
     */
    void insertStatisticFeedbackDetailDaily(List<StatisticFeedbackDetailDaily> list);

    /**
     * mgt_statistic_feedback_daily 插入数据
     * 
     * @param list
     */
    void insertStatisticFeedbackDaily(List<StatisticFeedbackDaily> list);

    List<StatisticFeedbackDaily> selectStatisticFeedbackDaily(Date startDate, Date endDate);

    List<StatisticFeedbackDetailDaily> selectStatisticFeedbackDetailDaily(Date startDate, Date endDate);

    /**
     * /statistic/channel/feedback接口
     * @param startDate
     * @param endDate
     * @param channelCode
     * @param userType
     * @return
     */
    List<StatisticFeedbackDaily> selectStatisticFeedbackDailyByDateAndUserType(Date startDate,
        Date endDate, String channelCode, Integer userType);
}
