package com.jbb.mgt.rs.action.user;

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
import com.jbb.mgt.core.domain.UserLibraryInfo;
import com.jbb.mgt.core.service.UserLibraryService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

import lombok.Getter;

@Service(UserLibraryAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserLibraryAction extends BasicAction {

    public static final String ACTION_NAME = "UserLibraryAction";

    private static Logger logger = LoggerFactory.getLogger(UserLibraryAction.class);

    private Response response;
    @Autowired
    private UserLibraryService UserLibraryService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getUserList(int pageNo, int pageSize, String channelCode, String phoneNumber, String ipAddress,
        String startDate, String endDate) {
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("缺少参数:startDate");
        }
        Timestamp startTs = Util.parseTimestamp(startDate, this.getTimezone());
        Timestamp endTs = Util.parseTimestamp(endDate, this.getTimezone());
        pageSize = pageSize != 0 ? pageSize : 10;
        PageHelper.startPage(pageNo, pageSize);
        List<UserLibraryInfo> userLibraryInfos
            = UserLibraryService.getUserLibraryInfo(channelCode, phoneNumber, ipAddress, startTs, endTs);
        PageInfo<UserLibraryInfo> pageInfo = new PageInfo<>(userLibraryInfos);
        this.response.pageInfo = pageInfo;
        PageHelper.clearPage();
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @Getter
    public static class Response extends ActionResponse {
        public PageInfo<UserLibraryInfo> pageInfo;

    }

}
