package com.jbb.mgt.rs.action.account;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jbb.mgt.server.rs.pojo.RsAccount;
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
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.Roles;
import com.jbb.mgt.core.service.OrganizationService;
import com.jbb.mgt.server.core.util.PasswordUtil;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;
import com.jbb.server.common.exception.AccessDeniedException;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.PhoneNumberUtil;
import com.jbb.server.common.util.StringUtil;

@Service(AccountAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AccountAction extends BasicAction {
    public static final String ACTION_NAME = "AccountAction";
    private static Logger logger = LoggerFactory.getLogger(AccountAction.class);
    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    private Response response;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private PlatformTransactionManager txManager;

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

    public void insertAccount(Request request) {
        logger.debug("> insertAccount(), request = {}", request);
        TransactionStatus txStatus = null;
        validateRequest(request);
        Account newAccount = generateAccount(request);
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            coreAccountService.createAccount(newAccount);
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            rollbackTransaction(txStatus);
        }
        this.response.accountId = newAccount.getAccountId();
        logger.debug("< insertAccount()");
    }

    public void getAccountlById(Integer accountId) {
        logger.debug("> getAccountlById(), accountId = " + accountId);
        if (accountId == null) {
            accountId = this.account.getAccountId();
        }
        this.response.account = coreAccountService.getAccountById(accountId, this.account.getOrgId(), true);
        logger.debug("< getAccountlById()");

    }

    public void getAccounts(int pageNo, int pageSize, boolean detail) {
        logger.debug("> getAccounts(),isDetail = " + detail);
        PageHelper.startPage(pageNo, pageSize);
        List<Account> accounts = coreAccountService.getAccount(this.account.getOrgId(), detail);
        PageInfo<Account> pageInfo = new PageInfo<>(accounts);
        this.response.pageInfo = pageInfo;
        PageHelper.clearPage();
        logger.debug("< getAccounts()");
    }

    public void getAccounts(int[] roleIds) {
        logger.debug("> getAccounts(), roleIds = " + roleIds);
        List<Account> accounts = coreAccountService.selectAccountByRoleIds(this.account.getOrgId(), roleIds);
        this.response.accounts = new ArrayList<RsAccount>(accounts.size());
        for (Account account : accounts) {
            this.response.accounts.add(new RsAccount(account));
        }

        logger.debug("< getAccounts()");
    }

    public void updateAccountlById(Integer accountId, Request request) {
        logger.debug("> updateAccountlById(), accountId ={}, request = {}", accountId, request);

        if (accountId == null) {
            accountId = this.account.getAccountId();
        }
//        validateRoleId(request);

        Account accountEdit = coreAccountService.getAccountById(accountId, this.account.getOrgId(), true);
        if (accountId == this.account.getAccountId() && (this.isOrgAdmin() || this.isOpOrgAdmin())) {
            // 组织管理员不能修改自己的组强管理权限
            request.roles = accountEdit.getRoles();
        }

        // 检查权限
        validateOpRight(accountEdit);

        generateAccountForEdit(accountEdit, request);

        coreAccountService.updateAccount(accountEdit);
        logger.debug("> updateAccountlById()");
    }

    public void updateAccountStatus(Integer accountId, boolean isDeleted) {
        logger.debug("> updateAccountStates(), accountId ={}", accountId + " , isDeleted = " + isDeleted);
        if (accountId == null) {
            accountId = this.account.getAccountId();
        }

        // 组织管理员不能删除自己
        if (isDeleted && (accountId == this.account.getAccountId() && (this.isOrgAdmin() || this.isOpOrgAdmin()))) {
            throw new WrongParameterValueException("jbb.mgt.exception.error.admin");
        }

        Account accountDeleted = coreAccountService.getAccountById(accountId, this.account.getOrgId(), false);
        // 检查权限
        validateOpRight(accountDeleted);
        // 管理员账号不能删除
        if (isDeleted && ((CommonUtil.inArray(Constants.MGT_P_ORGADMIN, accountDeleted.getPermissions()))
            || (CommonUtil.inArray(Constants.MGT_P1_102, accountDeleted.getPermissions())))) {
            throw new WrongParameterValueException("jbb.mgt.exception.error.admin");
        }

        coreAccountService.updateAccountStatus(accountId, isDeleted);
        logger.debug("< updateAccountStates()");
    }

    public void freezeAccountById(Integer accountId) {
        logger.debug("> freezeAccount(), accountId ={}", accountId);

        if (accountId == this.account.getAccountId() && (this.isOrgAdmin() || this.isOpOrgAdmin())) {
            // 组织管理员不能冻结自己
            throw new WrongParameterValueException("jbb.mgt.exception.error.admin");
        }

        Account freezeAccount = coreAccountService.getAccountById(accountId, this.account.getOrgId(), false);
        Organization org = organizationService.getOrganizationById(this.account.getOrgId(), false);
        // 检查权限
        validateOpRight(freezeAccount);
        if ((org.getOrgType() != Constants.SYS_ORG_TYPE
            && CommonUtil.inArray(Constants.MGT_P_ORGADMIN, freezeAccount.getPermissions()))
            || (org.getOrgType() == Constants.SYS_ORG_TYPE
                && CommonUtil.inArray(Constants.MGT_P1_102, freezeAccount.getPermissions()))) {
            throw new WrongParameterValueException("jbb.mgt.exception.error.admin");
        }

        coreAccountService.freezeAccount(accountId);
        logger.debug("< freezeAccount()");
    }

    public void thawAccountById(int accountId) {
        logger.debug("> thawAccountById(), accountId ={}", accountId);

        if (accountId == this.account.getAccountId() && (this.isOrgAdmin() || this.isOpOrgAdmin())) {
            // 组织管理员不能解冻自己
            return;
        }

        logger.debug("> thawAccountById(), accountId ={}", accountId);
        Account thawAccount = coreAccountService.getAccountById(accountId, this.account.getOrgId(), false);
        // 检查权限
        validateOpRight(thawAccount);

        coreAccountService.thawAccount(accountId);
        logger.debug("< thawAccountById()");

    }

    private void validateOpRight(Account account) {

        if (account == null) {
            throw new ObjectNotFoundException("jbb.mgt.exception.accountNotFound");
        }

        if (!(account.getCreator() == this.account.getCreator() || this.isOrgAdmin() || this.isOpOrgAdmin())) {
            throw new AccessDeniedException("jbb.mgt.exception.accountAccessDenied");
        }
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private Integer accountId;
        private Account account;
        private List<RsAccount> accounts;
        private PageInfo<Account> pageInfo;

        public Integer getAccountId() {
            return accountId;
        }

        public Account getAccount() {
            return account;
        }

        public List<RsAccount> getAccounts() {
            return accounts;
        }

        public PageInfo<Account> getPageInfo() {
            return pageInfo;
        }
    }

    private void validateRequest(Request req) {
        if (StringUtil.isEmpty(req.password) || req.password.length() < 8) {
            throw new WrongParameterValueException("jbb.mgt.exception.passwordError");
        }

        if (StringUtil.isEmpty(req.username.trim()) || coreAccountService.checkUsernameExist(req.username.trim())) {
            throw new WrongParameterValueException("jbb.mgt.exception.usernameDuplicate");
        }

        if ((StringUtil.isEmpty(req.phoneNumber)) || !PhoneNumberUtil.isValidPhoneNumber(req.phoneNumber)) {
            throw new WrongParameterValueException("jbb.xjl.error.exception.phoneNumber");
        }

//        validateRoleId(req);
    }

    private void validateRoleId(Request req) {
        List<Roles> roles = req.roles;
        if(roles != null) {
            for (int i = 0; i < roles.size(); i++) {
                int rolesId = roles.get(i).getRoleId();
                if (!(rolesId == Constants.MGT_P1_102 || rolesId == Constants.MGT_P1_103 || rolesId == Constants.MGT_P1_104
                        || rolesId == Constants.MGT_P1_105 || rolesId == Constants.MGT_P1_106 || rolesId == Constants.MGT_P1_107
                        || rolesId == Constants.MGT_P1_108 || rolesId == Constants.MGT_P1_110)) {
                    throw new WrongParameterValueException("jbb.mgt.exception.wrongRole");
                }
            }
        }
    }

    private Account generateAccount(Request req) {
        Account newAccount = new Account();
        newAccount.setPassword(PasswordUtil.passwordHash(req.password));
        newAccount.setOrgId(account.getOrgId());
        newAccount.setUsername(req.username.trim());
        newAccount.setCreator(account.getAccountId());
        newAccount.setNickname(req.nickname);
        newAccount.setPhoneNumber(req.phoneNumber);
        newAccount.setRoles(req.roles);
        newAccount.setCreationDate(DateUtil.getCurrentTimeStamp());
        return newAccount;
    }

    private void generateAccountForEdit(Account accountEdit, Request req) {
        // 修改时，仅允许修改如下字段
        if (!StringUtil.isEmpty(req.password)) {
            accountEdit.setPassword(PasswordUtil.passwordHash(req.password));
            coreAccountService.deleteUserKeyByAccountId(accountEdit.getAccountId());
        }
        accountEdit.setNickname(req.nickname);
        accountEdit.setPhoneNumber(req.phoneNumber);
        // 只有管理员可以修改账户角色信息
        if ((this.isOrgAdmin() && !CommonUtil.inArray(Constants.MGT_P_ORGADMIN, accountEdit.getPermissions()))
            || (this.isOpOrgAdmin() && !CommonUtil.inArray(Constants.MGT_P1_102, accountEdit.getPermissions()))) {
            accountEdit.setRoles(req.roles);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String password;
        public String username;
        public String nickname;
        public String phoneNumber;
        public List<Roles> roles;
    }

}
