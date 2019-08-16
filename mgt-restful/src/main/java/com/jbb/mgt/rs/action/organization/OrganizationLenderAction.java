package com.jbb.mgt.rs.action.organization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.OrganizationLender;
import com.jbb.mgt.core.service.OrganizationLenderService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(OrganizationLenderAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OrganizationLenderAction extends BasicAction {

    public static final String ACTION_NAME = "OrganizationLenderAction";

    private static Logger logger = LoggerFactory.getLogger(OrganizationLenderAction.class);

    private OrganizationLenderAction.Response response;

    @Autowired
    OrganizationLenderService lenderService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new OrganizationLenderAction.Response();
    }

    /**
     * 新增组织出借人和竞价信息
     *
     * @param request
     */
    public void insertOrgLender(Request request) {
        logger.debug(">insertOrgLender()");
        if (request == null) {
            logger.debug("<insertOrgLender() missing request");
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "request");
        }
        logger.debug(">insertOrgLender() accountId:{} price:{} title:{} description:{}", request.accountId,
            request.price, request.title, request.description);
        if (request.accountId == null || request.accountId == 0) {
            logger.debug("<insertOrgLender() missing accountId");
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "accountId");
        }
        OrganizationLender organizationLender = lenderService.selectByPrimaryKey(this.account.getOrgId());
        if (organizationLender != null) {
            logger.debug("<insertOrgLender() wrong orgId:{}", this.account.getOrgId());
            throw new WrongParameterValueException("jbb.mgt.error.exception.wrong.orgLenderExist");
        }
        OrganizationLender record = new OrganizationLender(this.account.getOrgId(), request.accountId, request.price,
            request.title, request.description);
        lenderService.insertSelective(record);
        logger.debug("<insertOrgLender()");
    }

    /**
     * 更新组织的出借人信息或者竞价信息
     * 
     * @param request
     */
    public void updateOrgLender(Request request) {
        logger.debug(">updateOrgLender()");
        if (request == null) {
            logger.debug("<updateOrgLender() missing request");
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "request");
        }
        logger.debug(">updateOrgLender() accountId:{} price:{} title:{} description:{}", request.accountId,
            request.price, request.title, request.description);
        if (request.accountId != null && request.accountId == 0) {
            logger.debug("<updateOrgLender() wrong accountId");
            throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "accountId");
        }
        OrganizationLender organizationLender = lenderService.selectByPrimaryKey(this.account.getOrgId());
        if (organizationLender == null) {
            logger.debug("<updateOrgLender() wrong orgId:{}", this.account.getOrgId());
            throw new ObjectNotFoundException("jbb.mgt.exception.orgLenderNotFound");
        }
        organizationLender.setUpdateDate(DateUtil.getCurrentTimeStamp());
        organizationLender.setAccountId(request.accountId);
        organizationLender.setTitle(request.title);
        organizationLender.setDescription(request.description);
        organizationLender.setPrice(request.price);
        lenderService.updateByPrimaryKeySelective(organizationLender);
        logger.debug("<insertOrgLender()");
    }

    /**
     * 获取组织出借人信息和竞价信息
     */
    public void getOrgLender() {
        logger.debug(">getOrgLender()");
        this.response.lender = lenderService.selectByPrimaryKey(this.account.getOrgId());
        logger.debug("<getOrgLender()");
    }

    /**
     * 获取所有组织出借人信息和竞价信息
     */
    public void getOrgLenders() {
        logger.debug(">getOrgLenders()");
        this.response.lenders = lenderService.selectOrgLenderList();
        logger.debug("<getOrgLenders()");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Integer accountId;

        public Integer price;

        public String title;

        public String description;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        @JsonIgnoreProperties({"orgId"})
        public OrganizationLender lender;

        @JsonIgnoreProperties({"orgId"})
        public List<OrganizationLender> lenders;

        public List<OrganizationLender> getLenders() {
            return lenders;
        }

        public OrganizationLender getLender() {
            return lender;
        }
    }

}
