package com.jbb.mgt.rs.action.ipStatistic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jbb.mgt.core.domain.IpAddressStatistic;
import com.jbb.mgt.core.domain.IpAddressUser;
import com.jbb.mgt.core.service.IpAddressStatisticService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.StringUtil;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author lishaojie
 * @title: IpAddressStatisticAction
 * @projectName JBBXServer
 * @description: IP异常分析
 * @date 2019/5/28
 */
@Service(IpAddressStatisticAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class IpAddressStatisticAction extends BasicAction {

    public static final String ACTION_NAME = "IpAddressStatisticAction";
    private final static int DEFAULT_PAGE_NO = 1;
    private final static int DEFAULT_PAGE_SIZE = 30;
    private final static int DEFAULT_REGISTER_CNT = 5;

    private static Logger logger = LoggerFactory.getLogger(IpAddressStatisticAction.class);

    @Autowired
    private IpAddressStatisticService ipStatisticService;

    private Response response;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getIpAddressStatistic(int pageNo, int pageSize, Integer registerCnt, String startDate, String endDate) {
        logger.debug(
            ">getIpAddressStatistic,registerCnt=" + registerCnt + ",startDate=" + startDate + ",endDate=" + endDate);
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        registerCnt = registerCnt == null ? DEFAULT_REGISTER_CNT : registerCnt;
        PageHelper.startPage(pageNo == 0 ? DEFAULT_PAGE_NO : pageNo, pageSize == 0 ? DEFAULT_PAGE_SIZE : pageSize);
        List<IpAddressStatistic> ipStatisticsList
            = ipStatisticService.getIpAddressStatistic(registerCnt, startDate, endDate);
        PageInfo<IpAddressStatistic> pageInfo = new PageInfo<>(ipStatisticsList);
        this.response.ipAddressFunnels = pageInfo;
        PageHelper.clearPage();
        logger.debug("<getIpAddressStatistic");
    }

    public void getIpAddressUsers(int pageNo, int pageSize, String ipAddress, String startDate, String endDate) {
        logger.debug(">getIpAddressUsers,ipAddress=" + ipAddress + ",startDate=" + startDate + ",endDate=" + endDate);
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        if (StringUtil.isEmpty(ipAddress)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "ipAddress");
        }
        PageHelper.startPage(pageNo == 0 ? DEFAULT_PAGE_NO : pageNo, pageSize == 0 ? DEFAULT_PAGE_SIZE : pageSize);
        List<IpAddressUser> ipUsersList = ipStatisticService.getIpAddressUser(ipAddress, startDate, endDate);
        PageInfo<IpAddressUser> pageInfo = new PageInfo<>(ipUsersList);
        this.response.ipAddressUsers = pageInfo;
        PageHelper.clearPage();
        logger.debug("<getIpAddressUsers");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private PageInfo<IpAddressStatistic> ipAddressFunnels;
        private PageInfo<IpAddressUser> ipAddressUsers;

        public PageInfo<IpAddressStatistic> getIpAddressFunnels() {
            return ipAddressFunnels;
        }

        public PageInfo<IpAddressUser> getIpAddressUsers() {
            return ipAddressUsers;
        }
    }
}
