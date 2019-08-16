package com.jbb.mgt.rs.action.OrgAssignSettings;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.OrgAssignSetting;
import com.jbb.mgt.core.service.OrgAssignSettingService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.AccessDeniedException;
import com.jbb.server.common.exception.WrongParameterValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *
 * @author wyq
 * @date 2018/7/12 17:18
 */
@Service(OrgAssignSettingAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OrgAssignSettingAction extends BasicAction {

    public static final String ACTION_NAME = "OrgAssignSettingAction";

    private static Logger logger = LoggerFactory.getLogger(OrgAssignSettingAction.class);

    private Response response;

    @Autowired
    private OrgAssignSettingService service;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void selectOrgAssignSetting(Integer assignType) {
        logger.debug(">selectOrgAssignSetting,assignType=" + assignType);
        List<OrgAssignSetting> list = service.selectOrgAssignSetting(this.account.getOrgId(), assignType);
        if (null != list) {
            this.response.assignSettings = list;
        }
        logger.debug("<selectOrgAssignSetting");
    }

    public void saveOrgAssignSetting(Request request) {
        logger.debug(
            ">saveOrgAssignSetting,request=" + request);
        if (!(this.isOrgAdmin() || this.isSysAdmin())){
            throw new AccessDeniedException("jbb.mgt.exception.HaveNoRightToAccess");
        }
        OrgAssignSetting oas = generationOrgAssignSetting(request);
        service.saveOrgAssignSetting(oas);
        logger.debug("<saveOrgAssignSetting");
    }

    public OrgAssignSetting generationOrgAssignSetting(Request req){
        OrgAssignSetting oas = new OrgAssignSetting();
        oas.setOrgId(this.account.getOrgId());
        if(null == req.assignType){
            throw new WrongParameterValueException("jbb.error.exception.missing.param","assignType");
        }
        oas.setAssignType(req.assignType);
        oas.setStatus(req.status);
        oas.setAccounts(req.accounts);
        return oas;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<OrgAssignSetting> assignSettings;

        public List<OrgAssignSetting> getAssignSettings() {
            return assignSettings;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Integer assignType;
        public Boolean status;
        public String accounts;
    }
}
