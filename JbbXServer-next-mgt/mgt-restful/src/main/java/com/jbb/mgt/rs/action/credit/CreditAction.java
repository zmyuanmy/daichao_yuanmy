package com.jbb.mgt.rs.action.credit;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Agreement;
import com.jbb.mgt.core.domain.Credit;
import com.jbb.mgt.core.service.AgreementService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.MissingParameterException;

@Service(CreditAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CreditAction extends BasicAction {
    public static final String ACTION_NAME = "CreditAction";

    private static Logger logger = LoggerFactory.getLogger(CreditAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private AgreementService agreementService;

    public void getCredit(String deviceId, Boolean getAgreements, int orgId) {
        logger.debug(">getCredit(),deviceId=" + deviceId);
        int maxAmount = PropertyManager.getIntProperty("jbb.xjl.Credit.maxAmount", 100000);
        int minAmount = PropertyManager.getIntProperty("jbb.xjl.Credit.minAmount", 50000);
        int serviceFeePercent = PropertyManager.getIntProperty("jbb.xjl.Credit.serviceFeePercent", 100);
        int days = PropertyManager.getIntProperty("jbb.xjl.Credit.days", 7);
        getAgreements = getAgreements == null ? false : getAgreements;
        if (orgId <= 0) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "orgId");
        }
        if (getAgreements) {
            this.response.agreements = agreementService.getAgreement(orgId);
        }
        this.response.credit = new Credit(maxAmount, minAmount, serviceFeePercent, days);
        logger.debug(">getCredit()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private Credit credit;
        private List<Agreement> agreements;

        public Credit getCredit() {
            return credit;
        }

        public List<Agreement> getAggrements() {
            return agreements;
        }

    }
}
