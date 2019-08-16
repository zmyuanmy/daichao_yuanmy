package com.jbb.server.rs.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.domain.LoanPlatformPolicy;
import com.jbb.server.core.domain.Property;
import com.jbb.server.core.service.SystemService;
import com.jbb.server.rs.pojo.ActionResponse;
import com.jbb.server.shared.map.StringMapper;

@Service(ChannelPolicyAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ChannelPolicyAction extends BasicAction {

    private static Logger logger = LoggerFactory.getLogger(ChannelPolicyAction.class);

    public static final String ACTION_NAME = "ChannelPolicyAction";

    private Response response;

    @Autowired
    private SystemService systemService;

    @Override
    protected ActionResponse makeActionResponse() {
        return response = new Response();
    }

    public void getPolicy() {
        String policyContent = systemService.getSystemPropertyValue(Property.SYS_LOAN_POLICY);

        if (policyContent == null) {
            this.response.policy = null;
            return;
        }
        LoanPlatformPolicy policy = null;
        try {
            policy = StringMapper.readPolicy(policyContent);
        } catch (Exception e) {
            logger.error("load policy error =" + e.getMessage());
            e.printStackTrace();
        }
        this.response.policy = policy;
    }

    public void updatePolicy(LoanPlatformPolicy policy) {
        logger.debug(">updatePolicy, policy=" + policy.toString());
        logger.info("policy=" + policy.toString());
        //更新新的policy
        
        String policyContent = StringMapper.toString(policy);
        systemService.saveSystemProperty(Property.SYS_LOAN_POLICY, policyContent);
        logger.debug("<updatePolicy");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private LoanPlatformPolicy policy;

        public LoanPlatformPolicy getPolicy() {
            return policy;
        }

    }

}
