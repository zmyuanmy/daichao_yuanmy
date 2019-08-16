package com.jbb.mgt.rs.action.user;

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
import com.jbb.mgt.core.domain.UserApplyRecord;
import com.jbb.mgt.core.service.UserApplyRecordService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DesensitizedUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

/**
 * userApplyRecord action类
 * 
 * @author wyq
 * @date 2018/6/9 17:52
 */
@Service(UserApplyRecordAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserApplyRecordAction extends BasicAction {

    public static final String ACTION_NAME = "UserApplyRecordAction";

    private static Logger logger = LoggerFactory.getLogger(UserApplyRecordAction.class);

    private Response response;

    @Autowired
    private UserApplyRecordService userApplyRecordService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new UserApplyRecordAction.Response();
    }

    public void selectUserApplyRecords(Integer orgId, Integer userType, String startDate, String endDate,
        Integer pageNo, Integer pageSize) {
        logger.debug(">selectUserApplyRecords,orgId=",
            orgId + "startDate=" + startDate + "endDate=" + endDate + "pageNo=" + pageNo + "pageSize=" + pageSize);
        if (null == orgId || orgId == 0) {
            throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "orgId");
        }
        if (StringUtil.isEmpty(startDate)) {
            startDate = null;
        }
        if (StringUtil.isEmpty(endDate)) {
            endDate = null;
        }
        PageHelper.startPage(pageNo, pageSize);
        List<UserApplyRecord> list = userApplyRecordService.selectUserApplyRecordsByOrgId(orgId, userType,
            Util.parseTimestamp(startDate, getTimezone()), Util.parseTimestamp(endDate, getTimezone()));
        if (list.size() > 0) {
            for (UserApplyRecord u : list) {
                if (!(((this.isOrgAdmin() || this.isOpOrgAdmin()) && this.account.getOrgId() == Constants.JBB_ORG) || this.isSysAdmin() ||  this.isOpSysAdmin())) {
                    if (!StringUtil.isEmpty(u.getUser().getUserName())) {
                        u.getUser().setUserName(DesensitizedUtil.chineseName(u.getUser().getUserName()));
                    }
                    if (!StringUtil.isEmpty(u.getUser().getQq())) {
                        u.getUser().setQq("*");
                    }
                    if (!StringUtil.isEmpty(u.getUser().getWechat())) {
                        u.getUser().setWechat("*");
                    }
                    if (!StringUtil.isEmpty(u.getUser().getPhoneNumber())) {
                        u.getUser().setPhoneNumber(DesensitizedUtil.mobilePhone(u.getUser().getPhoneNumber()));
                    }
                }
            }
        }
        PageInfo<UserApplyRecord> pageInfo = new PageInfo<>(list);
        this.response.pageInfo = pageInfo;
        PageHelper.clearPage();
        logger.debug("<selectUserApplyRecords");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {

    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public PageInfo<UserApplyRecord> pageInfo;

        public PageInfo<UserApplyRecord> getPageInfo() {
            return pageInfo;
        }

        public void setPageInfo(PageInfo<UserApplyRecord> pageInfo) {
            this.pageInfo = pageInfo;
        }
    }
}
