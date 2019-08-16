package com.jbb.mgt.rs.action.channelFunnel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.jbb.mgt.core.domain.ChannelFunnelFilter;
import com.jbb.mgt.core.domain.LargeLoanOrg;
import com.jbb.mgt.core.domain.UserProperty;
import com.jbb.mgt.core.service.ChannelFunnelFilterService;
import com.jbb.server.common.Constants;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import net.sf.json.JSONObject;
import org.apache.commons.jexl3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.ChannelFunnel;
import com.jbb.mgt.core.service.ChannelFunnelService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.StringUtil;

@Service(ChannelFunnelAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ChannelFunnelAction extends BasicAction {

    public static final String ACTION_NAME = "ChannelFunnelAction";

    private static Logger logger = LoggerFactory.getLogger(ChannelFunnelAction.class);
    private static final JexlEngine JEXL = new JexlBuilder().cache(512).strict(true).silent(false).create();
    private Response response;

    @Autowired
    private ChannelFunnelService channelFunnelService;

    @Autowired
    private ChannelFunnelFilterService channelFunnelFilterService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getChannelFunnelDaily(String[] channelCodes, Integer uType, String startDate, String endDate,
        Integer salesId, Integer mode) {
        logger.debug(">getChannelFunnelDaily() channelCodes{} ,uType{} ,startDate{} ,endDate{} ,salesId{} ,mode{}"
            + channelCodes + " ," + uType + " ," + startDate + " ," + endDate + " ," + salesId + " ," + mode);

        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        String[] newchannelCodes = channelCodes.length == 0 ? null : channelCodes;
        if (!this.checkChannelAndSale(Constants.MGT_STATISTIC_SALE_CHECK)) {
            salesId = this.account.getAccountId();
        }

        this.response.channelFunnels = channelFunnelService.getChannelFunnelDaily(newchannelCodes, uType, null, null,
            startDate, endDate, salesId, mode);
        logger.debug("<getChannelFunnelDaily()");

    }

    public void getChannelFunnelCompare(Integer uType, String startDate, String endDate, Integer salesId, Integer mode,
        int pageNo, int pageSize) {
        logger
            .debug(">getChannelFunnelCompare() uType{} ,startDate{} ,endDate{} ,salesId{} ,mode{} ,pageNo{} ,pageSize{}"
                + " ," + uType + " ," + startDate + " ," + endDate + " ," + salesId + " ," + mode + " ," + pageNo + " ,"
                + pageSize);
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        if (!this.checkChannelAndSale(Constants.MGT_STATISTIC_SALE_CHECK)) {
            salesId = this.account.getAccountId();
        }
        pageSize = pageSize == 0 ? 10 : pageSize;
        PageHelper.startPage(pageNo, pageSize);

        List<ChannelFunnel> list
            = channelFunnelService.getChannelFunnelCompare(uType, null, null, startDate, endDate, salesId, mode);

        List<ChannelFunnelFilter> filterList = channelFunnelFilterService.getChannelFunnelFilter(null);

        for (ChannelFunnel channelFunnel : list) {
            Map<String, Object> channelFunnelMap = beanToMap(channelFunnel);
            JexlContext context = new MapContext(channelFunnelMap);

            for (ChannelFunnelFilter filter : filterList) {
                if (!StringUtil.isEmpty(filter.getFilterScript())) {
                    boolean result = (Boolean)JEXL.createExpression(filter.getFilterScript()).evaluate(context);
                    if (result) {
                        channelFunnel.setStyle(filter.getStyle());
                        break;
                    }
                }
            }
        }

        PageInfo<ChannelFunnel> pageInfo = new PageInfo<ChannelFunnel>(list);

        this.response.channelFunnelTotal
            = channelFunnelService.getChannelFunnelCompareTotal(uType, startDate, endDate, salesId, mode);
        this.response.pageInfo = pageInfo;
        PageHelper.clearPage();
        logger.debug("<getChannelFunnelCompare()");
    }

    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<ChannelFunnel> channelFunnels;

        private PageInfo<ChannelFunnel> pageInfo;

        private ChannelFunnel channelFunnelTotal;

        public List<ChannelFunnel> getChannelFunnels() {
            return channelFunnels;
        }

        public PageInfo<ChannelFunnel> getPageInfo() {
            return pageInfo;
        }

        public ChannelFunnel getChannelFunnelTotal() {
            return channelFunnelTotal;
        }
    }

}
