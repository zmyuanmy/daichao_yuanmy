package com.jbb.mgt.rs.action.finOrgSalesRelation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.FinOrgSalesRelation;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.service.FinOrgSalesRelationService;
import com.jbb.mgt.core.service.OrganizationService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import static javafx.scene.input.KeyCode.F;

@Service(FinOrgSalesRelationAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FinOrgSalesRelationAction extends BasicAction {

    public static final String ACTION_NAME = "FinOrgSalesRelationAction";

    private static Logger logger = LoggerFactory.getLogger(FinOrgSalesRelationAction.class);

    private Response response;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private FinOrgSalesRelationService finOrgSalesRelationService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void insertFinOrgSalesRelation(int orgId, int accountId, Request req) {
        logger.debug(">insertFinOrgSalesRelation");

        // 验证account 组织是否存在
        if (orgId == 0) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "orgId");
        }
        if (accountId == 0) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "accountId");
        }
        Organization organization = organizationService.getOrganizationById(orgId, false);
        if (organization == null) {
            throw new WrongParameterValueException("jbb.mgt.exception.orgNotFound");
        }

        Account account = this.coreAccountService.getAccountById(accountId, null, false);
        if (account == null) {
            throw new WrongParameterValueException("jbb.mgt.exception.accountNotFound");
        }
        // 检查是否存在 如果存在将delete改为false
        Integer defaultPrice = null;
        Integer delegatePrice = null;
        if (null != req) {
            defaultPrice = req.defaultPrice;
            delegatePrice = req.delegatePrice;
        }
        FinOrgSalesRelation finOrgSalesRelationHistory
            = finOrgSalesRelationService.selectOrgSalesRelationByOrgId(orgId);
        if (finOrgSalesRelationHistory != null) {
            // 表示存在,更新条目
            finOrgSalesRelationService.updateFinOrgSalesRelation(orgId, accountId, false);
            return;
        }
        FinOrgSalesRelation finOrgSalesRelation = new FinOrgSalesRelation();
        finOrgSalesRelation.setAccountId(accountId);
        finOrgSalesRelation.setCreationDate(DateUtil.getCurrentTimeStamp());
        finOrgSalesRelation.setCreator(this.account.getAccountId());
        finOrgSalesRelation.setDeleted(false);
        finOrgSalesRelation.setOrgId(orgId);
        if (defaultPrice != null) {
            finOrgSalesRelation.setDefaultPrice(defaultPrice);
        } else {
            finOrgSalesRelation.setDefaultPrice(500);
        }
        if (delegatePrice != null){
            finOrgSalesRelation.setDelegatePrice(delegatePrice);
        }
        finOrgSalesRelationService.insertFinOrgSalesRelation(finOrgSalesRelation);
        logger.debug("<insertFinOrgSalesRelation");
    }

    public void deleteFinOrgSalesRelation(int orgId, int accountId) {
        logger.debug(">deleteFinOrgSalesRelation");
        // 验证account 组织是否存在
        if (orgId == 0) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "orgId");
        }
        if (accountId == 0) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "accountId");
        }
        Organization organization = organizationService.getOrganizationById(orgId, false);
        if (organization == null) {
            throw new WrongParameterValueException("jbb.mgt.exception.orgNotFound");
        }

        Account account = this.coreAccountService.getAccountById(accountId, null, false);
        if (account == null) {
            throw new WrongParameterValueException("jbb.mgt.exception.accountNotFound");
        }

        boolean flag = finOrgSalesRelationService.deleteFinOrgSalesRelation(orgId, accountId);

        logger.debug("<deleteFinOrgSalesRelation");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Integer defaultPrice;
        public Integer delegatePrice;
    }
}
