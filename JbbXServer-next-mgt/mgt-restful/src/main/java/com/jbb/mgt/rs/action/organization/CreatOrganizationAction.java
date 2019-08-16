package com.jbb.mgt.rs.action.organization;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.OrgRecharges;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.Roles;
import com.jbb.mgt.core.service.AccountService;
import com.jbb.mgt.core.service.ChannelService;
import com.jbb.mgt.core.service.JbbService;
import com.jbb.mgt.core.service.OrgRechargesService;
import com.jbb.mgt.core.service.OrganizationRelationService;
import com.jbb.mgt.core.service.OrganizationService;
import com.jbb.mgt.server.core.util.PasswordUtil;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.AccessDeniedException;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;

@Service(CreatOrganizationAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CreatOrganizationAction extends BasicAction {

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    public static final String ACTION_NAME = "CreatOrganizationAction";

    public static final Integer ROLE_ID = 2;
    public static final Integer ROLE_ID_XJL = 202;
    public static final Integer ORG_TYPE_XJL = 3;

    String SmsSignName = PropertyManager.getProperty("jbb.mgt.smsSignName.api.template");
    String smsTemplateIdt = PropertyManager.getProperty("jbb.mgt.smsTemplateId.api.template");

    private static Logger logger = LoggerFactory.getLogger(CreatOrganizationAction.class);

    private Response response;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    AccountService accountService;

    @Autowired
    OrgRechargesService orgRechargesService;

    @Autowired
    OrganizationRelationService relationService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    PlatformTransactionManager txManager;

    @Autowired
    JbbService jbbService;

    private void rollbackTransaction(TransactionStatus txStatus) {
        if (txStatus == null) {
            return;
        }

        try {
            txManager.rollback(txStatus);
        } catch (Exception er) {
            logger.warn("Cannot rollback transaction", er);
        }
    }

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getOrg(Integer orgId) {
        if (this.account.getOrgId() != 1) {
            logger.debug("<getOrg() wrong orgId:{} level:{}", this.account.getOrgId());
            throw new AccessDeniedException("jbb.error.validateAdminAccess.accessDenied");
        }
        if (orgId == null || orgId < 1) {
            throw new WrongParameterValueException("jbb.mgt.exception.orgNotFound");
        }
        Organization org = organizationService.getOrganizationById(orgId, false);
        if (org == null) {
            throw new WrongParameterValueException("jbb.mgt.exception.orgNotFound");
        }
        this.response.organization = org;
    }

    public void creatOrg(Request request) {
        // 组织创建
        logger.debug(">creatOrg");
        if (request == null) {
            logger.debug("<creatOrg() missing request");
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "req");
        }
        logger.debug(">creatOrg() orgname:{} jbbUserId:{} password:{} username:{} nickname:{} phoneNumber:{}",
            request.orgName, request.jbbUserId, request.password, request.username, request.nickname,
            request.phoneNumber);
        // 组织验证信息
        if (this.account.getOrgId() != 1) {
            logger.debug("<creatOrg() wrong orgId:{} level:{}", this.account.getOrgId());
            throw new AccessDeniedException("jbb.mgt.error.org.createPermission");
        }
        // 账户验证信息
        accountValidate(request);
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            // 新增组织
            Organization org = generateOrg(request);
            organizationService.insertOrganization(org);

            if (org.getDataEnabled()) {
                boolean isDelegate = channelService.selectExistsDelegate(org.getOrgId());
                if (!isDelegate) {
                    throw new WrongParameterValueException("jbb.mgt.exception.dataFlow.notDelegate");
                }
            }

            // 新增账户
            Account newAccount = generateAccount(request, org.getOrgId());
            accountService.createAccount(newAccount);
            Integer Code2 = jbbService.checkSend(newAccount.getAccountId(), newAccount.getJbbUserId());
            if (Code2 != 0) {
                logger.debug("<creatOrg() wrong  nickname:{}， jbbUserId:{}", newAccount.getUsername(),
                    account.getJbbUserId());
                throw new WrongParameterValueException("jbb.mgt.exception.system.Maintain");
            }

            // 新增组织充值消费
            orgRechargesService.insertOrgRecharges(new OrgRecharges(org.getOrgId()));

            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            rollbackTransaction(txStatus);
        }
        logger.debug("<creatOrg");

    }

    public void updateOrg(Integer orgId, Integer cnt, Integer weight, Boolean dataEnabled, String filterScript,
        Boolean delegateEnabled, Integer delegateWeight, Integer delegateH5Type) {
        if (this.account.getOrgId() != 1) {
            logger.debug("<insertSecondLevelOrg() wrong orgId:{} level:{}", this.account.getOrgId());
            throw new AccessDeniedException("jbb.mgt.error.org.createPermission");
        }
        logger.info(">updateOrg() account:{} orgId:{} cnt:{} weight:{} dataEnabled:{} filterScript:{}",
            this.account.getAccountId(), orgId, cnt, weight, dataEnabled, filterScript);

        if (orgId == null || orgId <= 1) {
            throw new WrongParameterValueException("jbb.mgt.exception.orgNotFound");
        }
        Organization org = organizationService.getOrganizationById(orgId, false);
        if (org == null) {
            throw new WrongParameterValueException("jbb.mgt.exception.orgNotFound");
        }
        Boolean newDataEnabled = dataEnabled == null ? false : dataEnabled;
        if (newDataEnabled) {
            boolean isDelegate = channelService.selectExistsDelegate(orgId);
            if (!isDelegate) {
                throw new WrongParameterValueException("jbb.mgt.exception.dataFlow.notDelegate");
            }
        }
        org = updateOrgPolicy(org, cnt, weight, dataEnabled, filterScript, delegateEnabled, delegateWeight,
            delegateH5Type);
        organizationService.updateOrganization(org);
    }

    private void accountValidate(Request req) {
        if (StringUtils.isEmpty(req.orgName)) {
            logger.debug("<insertSecondLevelOrg() missing orgName");
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "orgName");
        }
        if (StringUtil.isEmpty(req.password) || req.password.length() < 8) {
            logger.debug("<insertSecondLevelOrg() wrong password:{}", req.password);
            throw new WrongParameterValueException("jbb.mgt.exception.passwordError");
        }
        if (StringUtil.isEmpty(req.username) || coreAccountService.checkUsernameExist(req.username)) {
            logger.debug("<insertSecondLevelOrg() wrong username:{}", req.username);
            throw new WrongParameterValueException("jbb.mgt.exception.usernameDuplicate");
        }
        if (req.jbbUserId == null || coreAccountService.checkJbbIdExist(req.jbbUserId)) {
            logger.debug("<insertSecondLevelOrg() wrong username:{}", req.jbbUserId);
            throw new WrongParameterValueException("jbb.mgt.exception.jbbUserIdDuplicate");
        }
        Integer Code = jbbService.check(req.jbbUserId, req.nickname);
        if (Code == -1 || Code == 10) {
            logger.error("<creatOrg() wrong jbbUserId:{} nickname:{}", req.jbbUserId, req.nickname);
            throw new WrongParameterValueException("jbb.mgt.exception.system.Maintain");
        } else if (Code != 0) {
            logger.error("<creatOrg() wrong jbbUserId:{} nickname:{}", req.jbbUserId, req.nickname);
            throw new WrongParameterValueException("jbb.mgt.exception.MismatchUsernameAndJbbId");
        }
    }

    private Organization updateOrgPolicy(Organization org, Integer cnt, Integer weight, Boolean dataEnabled,
        String filterScript, Boolean delegateEnabled, Integer delegateWeight, Integer delegateH5Type) {
        org.setCnt(cnt != null ? cnt : org.getCnt());
        org.setWeight(weight != null ? weight : org.getWeight());
        org.setDataEnabled(dataEnabled != null ? dataEnabled : org.getDataEnabled());
        org.setFilterScript(filterScript != null ? filterScript : org.getFilterScript());
        org.setDelegateEnabled(delegateEnabled != null ? delegateEnabled : org.isDelegateEnabled());
        org.setDelegateWeight(delegateWeight != null ? delegateWeight : org.getDelegateWeight());
        org.setDelegateH5Type(delegateH5Type != null ? delegateH5Type : org.getDelegateH5Type());
        return org;
    }

    private Organization generateOrg(Request request) {
        Organization org = new Organization();
        Integer cnt = request.cnt == null ? 0 : request.cnt;
        Integer orgType = request.orgType == null ? 1 : request.orgType;
        Integer level = request.level == null ? 0 : request.level;
        Integer weight = request.weight == null ? 0 : request.weight;
        Boolean dataEnabled = request.dataEnabled == null ? false : request.dataEnabled;
        String description = request.description == null ? request.orgName : request.description;
        String smsTemplateId = request.smsTemplateId == null ? smsTemplateIdt : request.smsTemplateId;
        Integer delegateWeight = request.delegateWeight == null ? 0 : request.delegateWeight;
        Boolean delegateEnabled = request.delegateEnabled == null ? false : request.delegateEnabled;
        Integer delegateH5Type = request.delegateH5Type == null ? 0 : request.delegateH5Type;
        org.setName(request.orgName);
        org.setCompanyName(request.companyName);
        org.setDescription(description);
        org.setSmsSignName(StringUtil.replace(SmsSignName, "orgTemplate", request.orgName));
        org.setSmsTemplateId(smsTemplateId);
        org.setLevel(level);
        org.setOrgType(orgType);
        org.setCnt(cnt);
        org.setWeight(weight);
        org.setDataEnabled(dataEnabled);
        org.setFilterScript(request.filterScript);
        org.setDelegateEnabled(delegateEnabled);
        org.setDelegateWeight(delegateWeight);
        org.setDelegateH5Type(delegateH5Type);
        return org;
    }

    private Account generateAccount(Request req, Integer orgId) {
        Account newAccount = new Account();
        Roles role = new Roles();
        if (req.orgType == ORG_TYPE_XJL) {
            role.setRoleId(ROLE_ID_XJL);
        } else {
            role.setRoleId(ROLE_ID);
        }
        List<Roles> roles = new ArrayList<>();
        roles.add(role);
        newAccount.setJbbUserId(req.jbbUserId);
        newAccount.setPassword(PasswordUtil.passwordHash(req.password));
        newAccount.setOrgId(orgId);
        newAccount.setUsername(req.username.trim());
        newAccount.setCreator(this.account.getAccountId());
        newAccount.setNickname(req.nickname);
        newAccount.setPhoneNumber(req.phoneNumber);
        newAccount.setRoles(roles);
        newAccount.setCreationDate(DateUtil.getCurrentTimeStamp());
        return newAccount;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String orgName; // 组织名称
        public String companyName;
        public String description;
        public Integer jbbUserId; // JBBID
        public String password; // 账户密码
        public String username; // 账户名称
        public String nickname; // 昵称
        public String phoneNumber; // 号码
        public String smsTemplateId;
        public Integer level;
        public Integer orgType;// 组织类型
        public Integer cnt;// 导流数量
        public Integer weight;// 导流权重
        public Boolean dataEnabled;// 是否导流
        public String filterScript;// 过滤条件
        public Boolean delegateEnabled;
        public Integer delegateWeight;
        public Integer delegateH5Type;
    }

    public static class Response extends ActionResponse {
        private Organization organization;

        public Organization getOrganization() {
            return organization;
        }
    }

}
