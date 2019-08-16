package com.jbb.mgt.rs.action.channelFunnelFilter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jbb.mgt.core.domain.ChannelFunnelCondition;
import com.jbb.mgt.core.domain.ChannelFunnelFilter;
import com.jbb.mgt.rs.action.loan.UserLoanRecordUpdateAction;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.ChannelFunnel;
import com.jbb.mgt.core.service.ChannelFunnelFilterService;
import com.jbb.mgt.core.service.ChannelFunnelService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.transaction.TransactionManager;

@Service(ChannelFunnelFilterAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
public class ChannelFunnelFilterAction extends BasicAction {

    public static final String ACTION_NAME = "ChannelFunnelFilterAction";

    private Response response;

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private ChannelFunnelFilterService channelFunnelFilterService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private PlatformTransactionManager txManager;

    private void rollbackTransaction(TransactionStatus txStatus) {
        if (txStatus == null) {
            return;
        }

        try {
            txManager.rollback(txStatus);
        } catch (Exception er) {
            log.warn("Cannot rollback transaction", er);
        }
    }

    public void getChannelFunnelFilter() {
        log.debug(">getChannelFunnelFilter()");
        this.response.channelFunnelFilters = channelFunnelFilterService.getChannelFunnelFilter(null);
        log.debug("<getChannelFunnelFilter()");
    }

    public void insertChannelFunnelFilter(Request req) {
        log.debug(">insertChannelFunnelFilter() request{}" + req);
        if (req == null) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "request");
        }
        if (StringUtil.isEmpty(req.filterName)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "filterName");
        }
        ChannelFunnelFilter channelFunnelFilter = generateFilter(null, req);
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);

            channelFunnelFilterService.saveChannelFunnelFilter(channelFunnelFilter);
            if (req.conditions != null && req.conditions.size() > 0) {
                channelFunnelFilterService.insertChannelFunnelCondition(channelFunnelFilter.getFilterId(),
                    req.conditions);
            }
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            rollbackTransaction(txStatus);
        }
        log.debug("<insertChannelFunnelFilter()");
    }

    public void updateChannelFunnelFilter(Integer filterId, Request req) {
        log.debug(">updateChannelFunnelFilter() filterId{}, request{}" + filterId + ", " + req);
        List<ChannelFunnelFilter> filterList = new ArrayList<ChannelFunnelFilter>();
        if (req == null) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "request");
        }
        filterList = channelFunnelFilterService.getChannelFunnelFilter(filterId);
        if (filterList.size() == 0 || filterList.get(0) == null) {
            throw new ObjectNotFoundException("jbb.mgt.exception.filter.notFound");
        }
        ChannelFunnelFilter filter = filterId == null ? null : filterList.get(0);
        ChannelFunnelFilter channelFunnelFilter = generateFilter(filter, req);
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);

            channelFunnelFilterService.saveChannelFunnelFilter(channelFunnelFilter);
            if (req.conditions != null && req.conditions.size() > 0) {
                channelFunnelFilterService.deleteChannelFunnelCondition(filterId);
                channelFunnelFilterService.insertChannelFunnelCondition(filterId, req.conditions);
            }
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            rollbackTransaction(txStatus);
        }

        log.debug("<updateChannelFunnelFilter()");
    }

    private ChannelFunnelFilter generateFilter(ChannelFunnelFilter channelFunnelFilter, Request req) {
        channelFunnelFilter = channelFunnelFilter == null ? new ChannelFunnelFilter() : channelFunnelFilter;
        channelFunnelFilter.setFilterName(
            req.filterName = StringUtil.isEmpty(req.filterName) ? channelFunnelFilter.getFilterName() : req.filterName);
        channelFunnelFilter.setStyle(req.style = req.style == null ? channelFunnelFilter.getStyle() : req.style);
        channelFunnelFilter.setIndex(req.index = req.index == null ? channelFunnelFilter.getIndex() : req.index);
        return channelFunnelFilter;
    }

    public void deleteChannelFunnelFilter(Integer filterId) {
        log.debug(">deleteChannelFunnelFilter() filterId{}" + filterId);
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);

            channelFunnelFilterService.deleteChannelFunnelFilter(filterId);
            channelFunnelFilterService.deleteChannelFunnelCondition(filterId);
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            rollbackTransaction(txStatus);
        }
        log.debug("<deleteChannelFunnelFilter()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<ChannelFunnelFilter> channelFunnelFilters;

        public List<ChannelFunnelFilter> getChannelFunnelFilters() {
            return channelFunnelFilters;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String filterName;
        public String style;
        public Integer index;
        public List<ChannelFunnelCondition> conditions;
    }
}
