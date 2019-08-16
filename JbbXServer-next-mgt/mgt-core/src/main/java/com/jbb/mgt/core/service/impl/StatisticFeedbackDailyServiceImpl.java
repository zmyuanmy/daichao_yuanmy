package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.StatisticFeedbackDailyDao;
import com.jbb.mgt.core.domain.StatisticChannelDaily;
import com.jbb.mgt.core.domain.StatisticChannelDetailDaily;
import com.jbb.mgt.core.domain.StatisticFeedbackDaily;
import com.jbb.mgt.core.domain.StatisticFeedbackDetailDaily;
import com.jbb.mgt.core.service.StatisticFeedbackDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 渠道反馈 Service接口实现类
 *
 * @author wyq
 * @date 2018/8/8 20:00
 */
@Service("StatisticFeedbackDailyService")
public class StatisticFeedbackDailyServiceImpl implements StatisticFeedbackDailyService {

    @Autowired
    private StatisticFeedbackDailyDao dao;

    @Override
    public void insertStatisticFeedbackDetailDaily(List<StatisticFeedbackDetailDaily> list) {
        dao.insertStatisticFeedbackDetailDaily(list);
    }

    @Override
    public void insertStatisticFeedbackDaily(List<StatisticFeedbackDaily> list) {
        dao.insertStatisticFeedbackDaily(list);
    }

    @Override
    public List<StatisticFeedbackDaily> selectStatisticFeedbackDaily(Date startDate, Date endDate) {
        return dao.selectStatisticFeedbackDaily(startDate, endDate);
    }

    @Override
    public List<StatisticFeedbackDetailDaily> selectStatisticFeedbackDetailDaily(Date startDate, Date endDate) {
        return dao.selectStatisticFeedbackDetailDaily(startDate, endDate);
    }

    @Override
    public List<StatisticFeedbackDaily> selectStatisticFeedbackDailyByDateAndUserType(Date startDate, Date endDate,
        String channelCode, Integer userType) {
        return dao.selectStatisticFeedbackDailyByDateAndUserType(startDate, endDate, channelCode, userType);
    }

}
