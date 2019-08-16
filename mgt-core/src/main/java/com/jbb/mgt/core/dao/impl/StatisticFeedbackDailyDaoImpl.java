package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.StatisticFeedbackDailyDao;
import com.jbb.mgt.core.dao.mapper.StatisticFeedbackDailyMapper;
import com.jbb.mgt.core.domain.StatisticChannelDaily;
import com.jbb.mgt.core.domain.StatisticChannelDetailDaily;
import com.jbb.mgt.core.domain.StatisticFeedbackDaily;
import com.jbb.mgt.core.domain.StatisticFeedbackDetailDaily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 渠道反馈 Dao接口实现类
 *
 * @author wyq
 * @date 2018/8/8 19:58
 */
@Repository("StatisticFeedbackDailyDao")
public class StatisticFeedbackDailyDaoImpl implements StatisticFeedbackDailyDao {

    @Autowired
    private StatisticFeedbackDailyMapper mapper;

    @Override
    public void insertStatisticFeedbackDetailDaily(List<StatisticFeedbackDetailDaily> list) {
        mapper.insertStatisticFeedbackDetailDaily(list);
    }

    @Override
    public void insertStatisticFeedbackDaily(List<StatisticFeedbackDaily> list) {
        mapper.insertStatisticFeedbackDaily(list);
    }

    @Override
    public List<StatisticFeedbackDaily> selectStatisticFeedbackDaily(Date startDate, Date endDate) {
        return mapper.selectStatisticFeedbackDaily(new java.sql.Date(startDate.getTime()),
            new java.sql.Date(endDate.getTime()));
    }

    @Override
    public List<StatisticFeedbackDetailDaily> selectStatisticFeedbackDetailDaily(Date startDate, Date endDate) {
        return mapper.selectStatisticFeedbackDetailDaily(new java.sql.Date(startDate.getTime()),
            new java.sql.Date(endDate.getTime()));
    }

    @Override
    public List<StatisticFeedbackDaily> selectStatisticFeedbackDailyByDateAndUserType(Date startDate, Date endDate,
        String channelCode, Integer userType) {
        /*return mapper.selectStatisticFeedbackDailyByDateAndUserType(new java.sql.Date(startDate.getTime()),
            new java.sql.Date(endDate.getTime()), channelCode, userType);*/
        return mapper.selectStatisticFeedbackDailyByDateAndUserType(startDate, endDate, channelCode, userType);
    }

}
