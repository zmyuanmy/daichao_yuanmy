package com.jbb.mgt.rs.action.loanFields;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.LargeLoanField;
import com.jbb.mgt.core.service.LargeLoanFieldService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

@Service(LoanFieldAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoanFieldAction extends BasicAction {
    public static final String ACTION_NAME = "LoanFieldAction";
    private static Logger logger = LoggerFactory.getLogger(LoanFieldAction.class);

    private Response response;
    @Autowired
    private LargeLoanFieldService largeLoanFieldService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getLoanFields() {
        this.response.LargeLoanFields = largeLoanFieldService.getLoanFields(false, true);

    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<LargeLoanField> LargeLoanFields;

        public List<LargeLoanField> getLargeLoanFields() {
            return LargeLoanFields;
        }

    }

}
