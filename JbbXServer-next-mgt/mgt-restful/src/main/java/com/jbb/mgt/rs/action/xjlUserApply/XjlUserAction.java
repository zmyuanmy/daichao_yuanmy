package com.jbb.mgt.rs.action.xjlUserApply;

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
import com.jbb.mgt.core.domain.XjlUser;
import com.jbb.mgt.core.service.XjlApplyRecordService;
import com.jbb.mgt.core.service.XjlUserService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;

@Service(XjlUserAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XjlUserAction extends BasicAction {
    public static final String ACTION_NAME = "XjlUserAction";

    private static Logger logger = LoggerFactory.getLogger(XjlUserAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private XjlApplyRecordService xjlApplyRecordService;
    @Autowired
    private XjlUserService xjlUserService;

    public void getXjlUsers(String searchText, String startDate, String endDate, String ChannelCode, String flag,
        Boolean isAppLogin, Boolean isUserInfoVerified, Boolean isMobileVerified, Boolean isVideoVerified,
        Boolean isRealnameVerified, Boolean hasContacts, Boolean isTaobaoVerified, Integer userStatus,
        Boolean enableReceive, int pageNo, int pageSize) {
        logger.debug(">getXjlUsersForNotApply()");
        Timestamp startS = Util.parseTimestamp(startDate, getTimezone());
        Timestamp endS = Util.parseTimestamp(endDate, getTimezone());
        Timestamp times = null;
        if (enableReceive != null && enableReceive) {
            times = new Timestamp(DateUtil.getCurrentTime() - DateUtil.HOUR_MILLSECONDES / 2);
        }
        PageHelper.startPage(pageNo, pageSize);
        pageSize = pageSize == 0 ? 30 : pageSize;
        List<XjlUser> list = xjlUserService.getUsers(this.account.getAccountId(), this.account.getOrgId(), searchText,
            startS, endS, ChannelCode, flag, isAppLogin, isUserInfoVerified, isMobileVerified, isVideoVerified,
            isRealnameVerified, hasContacts, isTaobaoVerified, userStatus, times);

        PageInfo<XjlUser> pageInfo = new PageInfo<XjlUser>(list);
        this.response.pageInfo = pageInfo;

        PageHelper.clearPage();

        logger.debug("<getXjlUsersForNotApply()");
    }

    public void updateUserStatus(Integer userId, Integer status) {
        logger.debug(">updateUserStaus()");
        if (userId == null || userId < 0) {
            throw new ObjectNotFoundException("jbb.mgt.exception.userNotFound");
        }
        if (!checkUserExistInXjl(userId)) {
            throw new ObjectNotFoundException("jbb.mgt.exception.userNotFound");
        }
        if (status == null) {
            throw new WrongParameterValueException("jbb.xjl.error.user.statusNotCorrect");
        }
        xjlUserService.saveUserStatus(userId, this.account.getOrgId(), status);
        logger.debug("<updateUserStaus()");
    }

    public void receiveUsers(Request req) {
        logger.debug(">receiveUsers()");
        if (req.userIds == null || req.userIds.length == 0) {
            return;
        }
        int receiveCnt = 0;
        int receiveFailCnt = 0;
        for (int i = 0; i < req.userIds.length; i++) {
            if (!checkUserExistInXjl(req.userIds[i]) || checkApplyExistByUserId(req.userIds[i])
                || checkUserReceive(req.userIds[i])) {
                receiveFailCnt = receiveFailCnt + 1;

                continue;
            } else {
                xjlUserService.saveReceive(req.userIds[i], this.account.getOrgId(), this.account.getAccountId());
                receiveCnt = receiveCnt + 1;
            }
        }
        this.response.totalCnt = req.userIds.length;
        this.response.receiveCnt = receiveCnt;
        this.response.receiveFailCnt = receiveFailCnt;
        logger.debug("<receiveUsers()");
    }

    private boolean checkUserExistInXjl(Integer userId) {
        Timestamp times = new Timestamp(DateUtil.getCurrentTime() - DateUtil.HOUR_MILLSECONDES / 2);
        return xjlUserService.checkExistByUserId(this.account.getOrgId(), userId, times);
    }

    private boolean checkUserReceive(Integer userId) {
        return xjlUserService.checkUserReceive(this.account.getOrgId(), userId);
    }

    private boolean checkApplyExistByUserId(Integer userId) {
        return xjlApplyRecordService.checkExistByUserId(this.account.getOrgId(), userId);
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private PageInfo<XjlUser> pageInfo;
        private Integer totalCnt;
        private Integer receiveCnt;
        private Integer receiveFailCnt;

        public Integer getTotalCnt() {
            return totalCnt;
        }

        public PageInfo<XjlUser> getPageInfo() {
            return pageInfo;
        }

        public Integer getReceiveCnt() {
            return receiveCnt;
        }

        public Integer getReceiveFailCnt() {
            return receiveFailCnt;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public int[] userIds;
    }

}
