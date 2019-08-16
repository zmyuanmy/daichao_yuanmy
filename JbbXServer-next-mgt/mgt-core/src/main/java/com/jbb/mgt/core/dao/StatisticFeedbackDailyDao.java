package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.StatisticFeedbackDaily;
import com.jbb.mgt.core.domain.StatisticFeedbackDetailDaily;

import java.util.Date;
import java.util.List;

/**
 * 渠道反馈 Dao接口
 *
 * @author wyq
 * @date 2018/8/8 19:57
 */
public interface StatisticFeedbackDailyDao {

    void insertStatisticFeedbackDaily(List<StatisticFeedbackDaily> list);

    void insertStatisticFeedbackDetailDaily(List<StatisticFeedbackDetailDaily> list);

    List<StatisticFeedbackDaily> selectStatisticFeedbackDaily(Date startDate, Date endDate);

    List<StatisticFeedbackDetailDaily> selectStatisticFeedbackDetailDaily(Date startDate, Date endDate);

    List<StatisticFeedbackDaily> selectStatisticFeedbackDailyByDateAndUserType(Date startDate,
        Date endDate, String channelCode, Integer userType);
}
