package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.StatisticFeedbackDaily;
import com.jbb.mgt.core.domain.StatisticFeedbackDetailDaily;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 渠道反馈 Mapper接口
 *
 * @author wyq
 * @date 2018/8/8 19:59
 */
public interface StatisticFeedbackDailyMapper {

    void insertStatisticFeedbackDetailDaily(@Param("lists") List<StatisticFeedbackDetailDaily> lists);

    void insertStatisticFeedbackDaily(@Param("lists") List<StatisticFeedbackDaily> lists);

    List<StatisticFeedbackDaily> selectStatisticFeedbackDaily(@Param("startDate") Date startDate,
        @Param("endDate") Date endDate);

    List<StatisticFeedbackDetailDaily> selectStatisticFeedbackDetailDaily(@Param("startDate") Date startDate,
        @Param("endDate") Date endDate);

    List<StatisticFeedbackDaily> selectStatisticFeedbackDailyByDateAndUserType(@Param("startDate") Date startDate,
        @Param("endDate") Date endDate, @Param("channelCode") String channelCode, @Param("userType") Integer userType);
}
