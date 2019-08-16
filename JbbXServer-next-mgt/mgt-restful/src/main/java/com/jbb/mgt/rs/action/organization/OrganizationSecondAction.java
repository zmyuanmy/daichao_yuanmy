package com.jbb.mgt.rs.action.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.*;
import com.jbb.mgt.core.service.*;
import com.jbb.mgt.server.core.util.PasswordUtil;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.server.common.exception.AccessDeniedException;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;
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

import java.util.ArrayList;
import java.util.List;

@Service(OrganizationSecondAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OrganizationSecondAction extends BasicAction {

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    public static final String ACTION_NAME = "OrganizationSecondAction";

    private static Logger logger = LoggerFactory.getLogger(OrganizationSecondAction.class);

    @Autowired
    OrganizationService organizationService;

    @Autowired
    AccountService accountService;

    @Autowired
    OrgRechargesService orgRechargesService;

    @Autowired
    OrganizationRelationService relationService;

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

    public void insertSecondLevelOrg(Request req) {
        logger.debug(">insertSecondLevelOrg");
        if (req == null) {
            logger.debug("<insertSecondLevelOrg() missing request");
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "req");
        }
        logger.debug(
            ">insertSecondLevelOrg() orgname:{} jbbUserId:{} password:{} username:{} nickname:{} phoneNumber:{}",
            req.orgName, req.jbbUserId, req.password, req.username, req.nickname, req.phoneNumber);
        // 组织验证信息
        orgValidate(req);
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);

            // 新增二级组织
            Organization org = new Organization(req.orgName, req.orgName, "【" + req.orgName + "】", "SMS_121165611", 2);
            organizationService.insertOrganization(org);

            // 账户验证信息
            accountValidate(req);

            // 新增账户
            Account account = new Account(req.username, req.nickname, req.phoneNumber, req.jbbUserId,
                PasswordUtil.passwordHash(req.password), org.getOrgId(), this.account.getAccountId());
            account.setRoles(new ArrayList<>());
            account.getRoles().add(new Roles(2));
            accountService.createAccount(account);
            Integer Code2 = jbbService.checkSend(account.getAccountId(), account.getJbbUserId());
            if (Code2 != 0) {
                logger.debug("<insertSecondLevelOrg() wrong jbbUserId:{} nickname:{}", account.getAccountId(),
                    account.getJbbUserId());
                throw new WrongParameterValueException("jbb.mgt.exception.system.Maintain");
            }

            // 新增组织充值消费
            orgRechargesService.insertOrgRecharges(new OrgRecharges(org.getOrgId()));

            // 新增组织与子组织关联
            List<OrganizationRelation> organizationRelations
                = relationService.selectOrgRelationByOrgId(this.account.getOrgId());
            if (organizationRelations.stream().anyMatch(s -> s.getSubOrgId().equals(org.getOrgId()))) {
                logger.debug("<insertSecondLevelOrg() wrong subOrgId:{}", org.getOrgId());
                throw new WrongParameterValueException("jbb.mgt.error.exception.wrong.orgRelationExist");
            }
            OrganizationRelation record = new OrganizationRelation(this.account.getOrgId(), org.getOrgId());
            relationService.insert(record);
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            rollbackTransaction(txStatus);
        }
        logger.debug("<insertSecondLevelOrg");
    }

    public void updateSecondLevelOrg(String orgName, String password) {
        logger.debug(">updateSecondLevelOrg");
        if (StringUtils.isEmpty(orgName)) {
            logger.debug("<updateSecondLevelOrg() missing orgName:{}", orgName);
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "orgName");
        }
        if (StringUtils.isEmpty(password)) {
            logger.debug("<updateSecondLevelOrg() missing password:{}", password);
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "password");
        }
        if (password.length() < 8) {
            logger.debug("<updateSecondLevelOrg() wrong password:{}", password);
            throw new WrongParameterValueException("jbb.mgt.exception.passwordError");
        }
        Organization organization = organizationService.getOrganizationById(this.account.getOrgId(), false);
        if (organization == null) {
            logger.error("<updateSecondLevelOrg() error this.account.getOrgId():{}", this.account.getOrgId());
            throw new ObjectNotFoundException("jbb.mgt.exception.orgNotFound");
        }
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            organization.setName(orgName);
            organization.setDescription(orgName);
            organization.setSmsSignName("【" + orgName + "】");
           // organizationService.updateOrganization(organization);
            Account account
                = coreAccountService.getAccountById(this.account.getAccountId(), this.account.getOrgId(), true);
            account.setPassword(PasswordUtil.passwordHash(password));
            coreAccountService.updateAccount(account);
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            rollbackTransaction(txStatus);
        }
        logger.debug("<updateSecondLevelOrg");
    }

    private void orgValidate(Request req) {
        Organization organization = organizationService.getOrganizationById(this.account.getOrgId(), false);
        if (organization == null) {
            logger.error("<insertSecondLevelOrg() error this.account.getOrgId():{}", this.account.getOrgId());
            throw new ObjectNotFoundException("jbb.mgt.exception.orgNotFound");
        }
        if (organization.getLevel() != 1) {
            logger.debug("<insertSecondLevelOrg() wrong orgId:{} level:{}", this.account.getOrgId(),
                organization.getLevel());
            throw new AccessDeniedException("jbb.mgt.error.org.createPermission");
        }
        if (StringUtils.isEmpty(req.orgName)) {
            logger.debug("<insertSecondLevelOrg() missing orgName");
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "orgName");
        }
    }

    private void accountValidate(Request req) {
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
            logger.debug("<insertSecondLevelOrg() wrong jbbUserId:{} nickname:{}", req.jbbUserId, req.nickname);
            throw new WrongParameterValueException("jbb.mgt.exception.system.Maintain");
        } else if (Code != 0) {
            logger.debug("<insertSecondLevelOrg() wrong jbbUserId:{} nickname:{}", req.jbbUserId, req.nickname);
            throw new WrongParameterValueException("jbb.mgt.exception.MismatchUsernameAndJbbId");
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String orgName; // 组织名称
        public Integer jbbUserId; // JBBID
        public String password; // 账户密码
        public String username; // 账户名称
        public String nickname; // 昵称
        public String phoneNumber; // 号码
    }
}
