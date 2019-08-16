package com.jbb.mgt.rs.action.mgtFinOrgStatisticDaily;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.FinOrgSalesRelation;
import com.jbb.mgt.core.domain.MgtFinOrgStatisticDaily;
import com.jbb.mgt.core.domain.OrgStatisticDailyInfo;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.service.FinOrgSalesRelationService;
import com.jbb.mgt.core.service.FinOrgStatisticServices;
import com.jbb.mgt.core.service.MgtFinOrgStatisticDailyService;
import com.jbb.mgt.core.service.OrganizationService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;

@Service(MgtFinOrgStatisticDailyAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MgtFinOrgStatisticDailyAction extends BasicAction {

    public static final String ACTION_NAME = "MgtFinOrgStatisticDailyAction";

    private static Logger logger = LoggerFactory.getLogger(MgtFinOrgStatisticDailyAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private MgtFinOrgStatisticDailyService mgtFinOrgStatisticDailyService;

    @Autowired
    private FinOrgSalesRelationService finOrgSalesRelationService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    @Qualifier("FinOrgEntryStatisticServices")
    private FinOrgStatisticServices finOrgEntryStatisticServices;

    @Autowired
    @Qualifier("FinOrgRegisterStatisticServices")
    private FinOrgStatisticServices finOrgRegisterStatisticServices;
    @Autowired
    @Qualifier("FinOrgAdStatisticServices")
    private FinOrgStatisticServices finOrgAdStatisticServices;

    public void statisticByDate(String statisticDate, String type, Integer salesId) {
        logger.debug(">statisticByDate()");

        if (StringUtil.isEmpty(statisticDate)) {
            statisticDate = new Date(DateUtil.getTodayStartCurrentTime()).toString();
        }
        if (StringUtil.isEmpty(type)) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "type");
        }
        String time = statisticDate + " 00:00:00";
        Date statisticDatenew = new Date(DateUtil.parseStrnew(time).getTime());
        List<OrgStatisticDailyInfo> mgtFinOrgStatisticDailies = new ArrayList<OrgStatisticDailyInfo>();
        if (type.equals("1")) {
            mgtFinOrgStatisticDailies = finOrgEntryStatisticServices.getOrgStatistics(statisticDatenew, salesId);
        } else if (type.equals("2")) {
            mgtFinOrgStatisticDailies = finOrgRegisterStatisticServices.getOrgStatistics(statisticDatenew, salesId);
        } else {
            mgtFinOrgStatisticDailies = finOrgAdStatisticServices.getOrgStatistics(statisticDatenew, salesId);
        }

        if (mgtFinOrgStatisticDailies != null && mgtFinOrgStatisticDailies.size() != 0) {
            this.response.orgs = mgtFinOrgStatisticDailies;
            this.response.statisticDate = statisticDate;
        }
        logger.debug("<statisticByDate()");
    }

    public void statisticByOrgId(String startDate, String endDate, String type, Integer orgId) {
        logger.debug(">statisticByOrgId()");
        if (orgId == null || orgId == 0) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "orgId");
        }
        if (StringUtil.isEmpty(type)) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "type");
        }
        if (StringUtil.isEmpty(startDate)) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        if (StringUtil.isEmpty(endDate)) {
            endDate = new Date(DateUtil.getTodayStartCurrentTime()).toString();
        }

        String timeStart = startDate + " 00:00:00";
        String timeEnd = endDate + " 00:00:00";
        Date startDateParm = new Date(DateUtil.parseStrnew(timeStart).getTime());
        Date endDateParm = new Date(DateUtil.parseStrnew(timeEnd).getTime());

        List<MgtFinOrgStatisticDaily> mgtFinOrgStatisticDailies = new ArrayList<MgtFinOrgStatisticDaily>();
        if (type.equals("1")) {
            mgtFinOrgStatisticDailies
                = finOrgEntryStatisticServices.getOrgStatistics(orgId, startDateParm, endDateParm);
        } else if (type.equals("2")) {
            mgtFinOrgStatisticDailies
                = finOrgRegisterStatisticServices.getOrgStatistics(orgId, startDateParm, endDateParm);
        } else {
            mgtFinOrgStatisticDailies = finOrgAdStatisticServices.getOrgStatistics(orgId, startDateParm, endDateParm);
        }

        if (mgtFinOrgStatisticDailies != null && mgtFinOrgStatisticDailies.size() != 0) {
            this.response.statistic = mgtFinOrgStatisticDailies;
        }
        // 获取组织信息
        Organization organization = organizationService.getOrganizationById(orgId, true);
        if (organization != null) {
            this.response.organization = organization;
        }
        // 获取账户信息
        FinOrgSalesRelation finOrgSalesRelation = finOrgSalesRelationService.selectOrgSalesRelationByOrgId(orgId);
        if (finOrgSalesRelation != null) {
            Account account = this.coreAccountService.getAccountById(finOrgSalesRelation.getAccountId(), null, false);
            this.response.salesAccount = account;
        }

        logger.debug("<statisticByOrgId()");
    }

    public void updateStatistic(String statisticDate, Integer type, Integer orgId, Request req) {
        logger.debug(">updateStatistic()");
        // 修改一条组织某一天的历史记录的单价 跟应付款额
        if (orgId == null || orgId == 0) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "orgId");
        }
        if (type == null || type == 0) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "type");
        }

        if (StringUtil.isEmpty(statisticDate)) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "statisticDate");
        }
        Date dateFormat = new Date(DateUtil.getTodayStartCurrentTime());
        String time = statisticDate + " 00:00:00";
        Date statisticDateParm = new Date(DateUtil.parseStrnew(time).getTime());
        if (statisticDate.equals(dateFormat.toString())) {
            // 操作当天的数据，首先查询数据库中有没有当天数据的条目，如果没有先插入一条今天的数据
            mgtFinOrgStatisticDailyService.checkAndInsertMgtFinOrgStatisticDaily(orgId, type, statisticDateParm);
        }
        // 获得指定的条目，修改内容，再修改当前组织的之后的所有数据的余额
        if (req.status != null) {
            // 修改状态 只有管理员，财务可以修改，销售不能修改
            int[] ps = {Constants.MGT_P_SYSADMIN,  Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102, Constants.MGT_P1_137};
            this.validateUserAccess(ps);
        }

        MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily = new MgtFinOrgStatisticDaily();
        mgtFinOrgStatisticDaily.setStatisticDate(statisticDateParm);
        mgtFinOrgStatisticDaily.setOrgId(orgId);
        mgtFinOrgStatisticDaily.setStatus(req.status);
        mgtFinOrgStatisticDaily.setAmount(req.amount);
        mgtFinOrgStatisticDaily.setPrice(req.price);
        if (type == 1) {
            finOrgEntryStatisticServices.saveOrgStatistic(mgtFinOrgStatisticDaily);
        } else if (type == 2) {
            finOrgRegisterStatisticServices.saveOrgStatistic(mgtFinOrgStatisticDaily);
        } else {
            finOrgAdStatisticServices.saveOrgStatistic(mgtFinOrgStatisticDaily);
        }
        logger.debug("<updateStatistic()");
    }

    public void updatemgtFinOrgStatisticDaily(Request req){
        logger.debug(">updatemgtFinOrgStatisticDaily()");
        if (null != req && null != req.orgs && req.orgs.size() > 0) {
            mgtFinOrgStatisticDailyService.updateMgtFinOrgStatisticDailyList(req.orgs);
        }
        logger.debug("<updatemgtFinOrgStatisticDaily()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public List<OrgStatisticDailyInfo> orgs;
        public String statisticDate;
        public Organization organization;
        public Account salesAccount;
        public List<MgtFinOrgStatisticDaily> statistic;

        public List<OrgStatisticDailyInfo> getOrgs() {
            return orgs;
        }

        public String getStatisticDate() {
            return statisticDate;
        }

        public Organization getOrganization() {
            return organization;
        }

        public Account getSalesAccount() {
            return salesAccount;
        }

        public List<MgtFinOrgStatisticDaily> getStatistic() {
            return statistic;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Integer status;
        public Integer price;
        public Integer amount;
        public List<MgtFinOrgStatisticDaily> orgs;
    }

}
