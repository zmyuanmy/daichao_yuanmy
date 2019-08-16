package com.jbb.mgt.rs.action.baseinfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.*;
import com.jbb.mgt.core.service.BaseInfoService;
import com.jbb.mgt.rs.action.alipay.AlipayAction;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(BaseInfoAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BaseInfoAction extends BasicAction {

    public static final String ACTION_NAME = "BaseInfoAction";

    private static Logger logger = LoggerFactory.getLogger(BaseInfoAction.class);

    private Response response;

    @Autowired
    private BaseInfoService baseInfoService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getBaseInfo(String education, String maritalStatus, String relationList, String loanPurpose,
        String provinceList,String professionType) {
        logger.debug("> getBaseInfo()");
        if (!StringUtil.isEmpty(education)) {
            List<EducationBase> educationBases = baseInfoService.selectEducationBases();
            this.response.educationList = educationBases;
        }

        if (!StringUtil.isEmpty(maritalStatus)) {
            List<MaritalStatusBase> maritalStatusBases = baseInfoService.selectMaritalStatusBases();
            this.response.maritalStatusList = maritalStatusBases;
        }

        if (!StringUtil.isEmpty(relationList)) {
            List<RelationBase> relationBases = baseInfoService.selectRelationBases();
            this.response.relationList = relationBases;
        }

        if (!StringUtil.isEmpty(loanPurpose)) {
            List<LoanPurposeBase> loanPurposeBases = baseInfoService.selectLoanPurposeBases();
            this.response.loanPurposeList = loanPurposeBases;
        }

        if (!StringUtil.isEmpty(professionType)) {
            List<ProfessionTypeBase> professionTypeBases = baseInfoService.selectProfessionTypeBases();
            this.response.professionTypeList = professionTypeBases;
        }

        if (!StringUtil.isEmpty(provinceList)) {

        }
        logger.debug("< getBaseInfo()");

    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public List<EducationBase> educationList;
        public List<MaritalStatusBase> maritalStatusList;
        public List<RelationBase> relationList;
        public List<LoanPurposeBase> loanPurposeList;
        public List<ProfessionTypeBase> professionTypeList;
    }
}
