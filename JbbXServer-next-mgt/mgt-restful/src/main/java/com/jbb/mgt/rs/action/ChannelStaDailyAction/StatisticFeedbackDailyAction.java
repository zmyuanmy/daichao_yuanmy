package com.jbb.mgt.rs.action.ChannelStaDailyAction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.FeedBack;
import com.jbb.mgt.core.domain.StatisticFeedbackDaily;
import com.jbb.mgt.core.service.ChannelService;
import com.jbb.mgt.core.service.StatisticFeedbackDailyService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateFormatUtil;
import com.jbb.server.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 渠道质量反馈表 Action
 *
 * @author wyq
 * @date 2018/8/9 19:50
 */
@Service(StatisticFeedbackDailyAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class StatisticFeedbackDailyAction extends BasicAction {

    public static final String ACTION_NAME = "ChannelsFeedbackDailyAction";

    private static Logger logger = LoggerFactory.getLogger(StatisticFeedbackDailyAction.class);

    private Response response;

    @Autowired
    private StatisticFeedbackDailyService feedbackDailyService;

    @Autowired
    private ChannelService channelService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getChannelFeedback(String channelCode, String startDate, String endDate, Integer userType) {
        logger.debug(">getChannelFeedback()");
        if (!StringUtil.isEmpty(channelCode)) {
            Channel channelByCode = channelService.getChannelByCode(channelCode);
            if (null == channelByCode) {
                throw new WrongParameterValueException("jbb.mgt.exception.channel.notFound", "zh", "channelCode");
            }
        } else {
            channelCode = null;
        }
        Date startS = null;
        Date endS = null;
        if (null == startDate) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        } else {
            startS = DateFormatUtil.stringToDate(startDate);
        }
        if (null == endDate) {
            endS = null;
        } else {
            endS = DateFormatUtil.stringToDate(endDate);
        }
        if (null == userType || userType < 0) {
            userType = null;
        }
        List<StatisticFeedbackDaily> list
            = feedbackDailyService.selectStatisticFeedbackDailyByDateAndUserType(startS, endS, channelCode, userType);
        FeedBack total = new FeedBack();
        List<FeedBack> fbss = total.getFeedBackTotal();
        ArrayList<StatisticFeedbackDaily> list2 = null;
        if (list.size() > 0) {
            // 把数据按channel_code相加
            list2 = sumStatisticFeedbackDaily(list);
            // 计算比率
            for (int i = 0; i < list2.size(); i++) {
                StatisticFeedbackDaily sdf = list2.get(i);
                sdf.setHangupRate((double)sdf.getHangupCnt() / sdf.getTotalRecommandCnt());
                sdf.setRejectRate((double)sdf.getRejectCnt() / sdf.getTotalRecommandCnt());
                total.setHangupTotal(total.getHangupTotal() + sdf.getHangupCnt());
                total.setRejectTotal(total.getRejectTotal() + sdf.getRejectCnt());
                total.setTotalRecommandTotal(total.getTotalRecommandTotal() + sdf.getTotalRecommandCnt());
                List<FeedBack> fbs = sdf.getFeedback();
                if (null != fbs && fbs.size() > 0) {
                    for (int j = 0; j < fbs.size(); j++) {
                        fbs.get(j).setRate((double)fbs.get(j).getCnt() / sdf.getTotalRecommandCnt());
                        if (null != fbss && Contain(fbss, fbs.get(j))) {
                            for (int k = 0; k < fbss.size(); k++) {
                                if (fbss.get(k).getReason().equals(fbs.get(j).getReason())) {
                                    fbss.get(k).setCnt(fbss.get(k).getCnt() + fbs.get(j).getCnt());
                                }
                            }
                        } else {
                            if (null == fbss) {
                                fbss = new ArrayList<FeedBack>();
                            }
                            FeedBack nfbs = new FeedBack();
                            nfbs.setCnt(fbs.get(j).getCnt());
                            nfbs.setReason(fbs.get(j).getReason());
                            fbss.add(nfbs);
                            total.setFeedBackTotal(fbss);
                        }
                    }

                }
            }
            total.setHangupRateTotal((double)total.getHangupTotal() / total.getTotalRecommandTotal());
            total.setRejectRateTotal((double)total.getRejectTotal() / total.getTotalRecommandTotal());
                if (null != total.getFeedBackTotal()) {
                for (int n = 0; n < total.getFeedBackTotal().size(); n++) {
                    total.getFeedBackTotal().get(n)
                        .setRate((double)total.getFeedBackTotal().get(n).getCnt() / total.getTotalRecommandTotal());
                }
            }
        }
        this.response.totalS = new FeedBack();
        this.response.totalS = total;
        this.response.statistic = new ArrayList<>();
        if (null != list2 && list2.size() > 0) {
            this.response.statistic.addAll(list2);
        }
        logger.debug("<getChannelFeedback()");
    }
    //List<StatisticFeedbackDaily> 去重
    private ArrayList<StatisticFeedbackDaily> sumStatisticFeedbackDaily(List<StatisticFeedbackDaily> list) {
        ArrayList<StatisticFeedbackDaily> list2 = new ArrayList<>();
        for (int m = 0; m < list.size(); m++) {
            if (m == 0 || !ContainStatisticFeedbackDaily(list2, list.get(m))) {
                list2.add(list.get(m));
            } else {
                for (int mm = 0; mm < list2.size(); mm++) {
                    if (list2.get(mm).getChannelCode().equals(list.get(m).getChannelCode())
                        && list2.get(mm).getUserType() == list.get(m).getUserType()) {
                        list2.get(mm).setRejectCnt(list2.get(mm).getRejectCnt() + list.get(m).getRejectCnt());
                        list2.get(mm).setHangupCnt(list2.get(mm).getHangupCnt() + list.get(m).getHangupCnt());
                        list2.get(mm).setTotalRecommandCnt(
                            list2.get(mm).getTotalRecommandCnt() + list.get(m).getTotalRecommandCnt());
                        List<FeedBack> fbs = list2.get(mm).getFeedback();
                        fbs.addAll(list.get(m).getFeedback());
                        for (int i = 0; i < fbs.size() - 1; i++) {
                            for (int j = 1; j < fbs.size(); j++) {
                                if (fbs.get(i).getReason().equals(fbs.get(j).getReason())) {
                                    fbs.get(i).setCnt(fbs.get(i).getCnt() + fbs.get(j).getCnt());
                                    fbs.remove(j);
                                    j--;
                                }
                            }
                        }
                        list.remove(m);
                        m--;
                        break;
                    }
                }
            }
        }
        return list2;
    }
    //验证List<StatisticFeedbackDaily>中包含StatisticFeedbackDaily
    private boolean ContainStatisticFeedbackDaily(List<StatisticFeedbackDaily> list, StatisticFeedbackDaily fbs) {
        boolean flag = false;
        for (int i = 0; i < list.size(); i++) {
            if (fbs.getChannelCode().equals(list.get(i).getChannelCode())
                && fbs.getUserType() == list.get(i).getUserType()) {
                flag = true;
            }
        }
        return flag;
    }
    //验证List<FeedBack>包含FeedBack
    private boolean Contain(List<FeedBack> fbss, FeedBack fbs) {
        ArrayList<String> list3 = new ArrayList<>();
        for (FeedBack fb : fbss) {
            list3.add(fb.getReason());
        }
        boolean flag = true;
        if (!list3.contains(fbs.getReason())) {
            flag = false;
        }
        return flag;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<StatisticFeedbackDaily> statistic;
        private FeedBack totalS;

        public List<StatisticFeedbackDaily> getStatistic() {
            return statistic;
        }

        public FeedBack getTotalS() {
            return totalS;
        }
    }

}
