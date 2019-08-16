package com.jbb.mgt.rs.action.pfmgt;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jbb.mgt.core.domain.LoanPlatformPublish;
import com.jbb.mgt.core.domain.Platform;
import com.jbb.mgt.core.service.LoanPlatformPublishService;
import com.jbb.mgt.core.service.LoanPlatformService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

@Service(LoanPlatformPublishAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoanPlatformPublishAction extends BasicAction {

    public static final String ACTION_NAME = "LoanPlatformPublishAction";

    private static Logger logger = LoggerFactory.getLogger(LoanPlatformPublishAction.class);

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    private Response response;

    @Autowired
    private LoanPlatformPublishService loanPlatformPublishService;

    @Autowired
    private LoanPlatformService loanPlatformService;

    public void insertPlatformPublish(Request request) {
        logger.debug(">insertPlatformPublish()");
        verifyParam(request);
        Platform platform = loanPlatformService.getPlatformById(request.platformId);
        if (null == platform) {
            throw new WrongParameterValueException("jbb.pfmgt.error.platform.notFound");
        }
        loanPlatformPublishService.insertPlatformPublish(request.platformId, request.publishDate);
        logger.debug("<insertPlatformPublish()");
    }

    public void updatePlatformPublish(Integer id, Request request) {
        logger.debug(">updatePlatformPublish()");
        if (null == id || id < 0) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "id");
        }
        verifyParam(request);
        LoanPlatformPublish publish
            = loanPlatformPublishService.selectPlatformPublish(id, request.platformId);
        if (null == publish) {
            throw new WrongParameterValueException("jbb.pfmgt.error.publish.notFound");
        }
        loanPlatformPublishService.updatePlatformPublish(id, request.platformId, request.publishDate);
        logger.debug("<updatePlatformPublish()");
    }

    public void deletePlatformPublish(Integer id) {
        logger.debug(">deletePlatformPublish()");
        if (null == id || id < 0) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "id");
        }
        LoanPlatformPublish publish = loanPlatformPublishService.selectPlatformPublish(id, null);
        if (null == publish) {
            throw new WrongParameterValueException("jbb.pfmgt.error.publish.notFound");
        }
        loanPlatformPublishService.deletePlatformPublish(id);
        logger.debug("<deletePlatformPublish()");
    }

    public void getPlatformPublishByDate(int pageNo, int pageSize, String startDate, String endDate) {
        logger.debug(">deletePlatformPublish() startDate:{} endDate:{}" + startDate + endDate);
        Timestamp startDateTs = null;
        Timestamp endDateTs = null;
        PageHelper.startPage(pageNo, pageSize);
        if (!StringUtil.isEmpty(startDate)) {
            startDateTs = Util.parseTimestamp(startDate, getTimezone());
        }
        if (!StringUtil.isEmpty(endDate)) {
            endDateTs = Util.parseTimestamp(endDate, getTimezone());
        }
        List<LoanPlatformPublish> list
            = loanPlatformPublishService.getPlatformPublishByDate(startDateTs, endDateTs, true);
        PageInfo<LoanPlatformPublish> pageInfo = new PageInfo<LoanPlatformPublish>(list);

        this.response.pageInfo = pageInfo;
        PageHelper.clearPage();
        logger.debug(">deletePlatformPublish()");
    }

    private void verifyParam(Request req) {
        if (null == req) {
            throw new MissingParameterException("jbb.mgt.exception.parameterObject.notFound", "zh", "request");
        }
        if (null == req.platformId || req.platformId < 0) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "platformId");
        }
        if (null == req.publishDate) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "publishDate");
        }
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private PageInfo<LoanPlatformPublish> pageInfo;

        public PageInfo<LoanPlatformPublish> getPageInfo() {
            return pageInfo;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Integer platformId;
        public Timestamp publishDate;
    }

}
