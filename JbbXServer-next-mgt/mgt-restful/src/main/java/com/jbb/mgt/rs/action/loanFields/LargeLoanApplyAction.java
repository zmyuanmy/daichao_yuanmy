package com.jbb.mgt.rs.action.loanFields;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.MapContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.LargeLoanOrg;
import com.jbb.mgt.core.domain.UserEventLog;
import com.jbb.mgt.core.domain.UserProperty;
import com.jbb.mgt.core.service.LargeLoanApplyService;
import com.jbb.mgt.core.service.LargeLoanFieldService;
import com.jbb.mgt.core.service.UserPropertiesService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.IDCardUtil;
import com.jbb.server.common.util.StringUtil;

import net.sf.json.JSONObject;

@Service(LargeLoanApplyAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LargeLoanApplyAction extends BasicAction {
    public static final String ACTION_NAME = "LargeLoanApplyAction";

    private static Logger logger = LoggerFactory.getLogger(LargeLoanApplyAction.class);
    private static final JexlEngine JEXL = new JexlBuilder().cache(512).strict(true).silent(false).create();
    private Response response;
    @Autowired
    private LargeLoanApplyService largeLoanApplyService;
    @Autowired
    private LargeLoanFieldService largeLoanFieldService;
    @Autowired
    private UserPropertiesService userPropertiesService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void largeLoanApply(Object object) {
        JSONObject jsonObject = JSONObject.fromObject(object);
        @SuppressWarnings("unchecked")
        Map<String, Object> request = (Map<String, Object>)jsonObject;
        List<String> fieldKey = new ArrayList<String>();
        List<UserProperty> userProperties = new ArrayList<UserProperty>();
        JexlContext context = new MapContext();
        for (String key : request.keySet()) {
            fieldKey.add(key);
            UserProperty property = new UserProperty(this.user.getUserId(), key.toString(), request.get(key).toString(),
                false, DateUtil.getCurrentTimeStamp());
            userProperties.add(property);
            context.set(key.toString(), request.get(key).toString());
        }
        List<String> requiredFields = largeLoanFieldService.getRequiredFields();

        if (!fieldKey.containsAll(requiredFields)) {
            throw new WrongParameterValueException("jbb.mgt.exception.missing.field");
        }
        userPropertiesService.insertUserProperties(this.user.getUserId(), userProperties);
        List<LargeLoanOrg> largeLoanOrgs = largeLoanApplyService.getAllLargeLoanOrg();
        List<LargeLoanOrg> matchOrgs = new ArrayList<LargeLoanOrg>();
        for (int i = 0; i < largeLoanOrgs.size(); i++) {
            if (isMatchJexlExpress(largeLoanOrgs.get(i).getFilterScript(), context)) {
                largeLoanOrgs.get(i).setQualified(true);
                matchOrgs.add(largeLoanOrgs.get(i));
            }
        }

        this.response.largeLoanOrgs = matchOrgs;
    }

    private boolean isMatchJexlExpress(String jexlScript, JexlContext context) {
        if (StringUtil.isEmpty(jexlScript)) {
            return true;
        }

        if (context == null) {
            return false;
        }
        try {
            JexlScript jexlS = JEXL.createScript(jexlScript);
            boolean result = (Boolean)jexlS.execute(context);
            return result;
        } catch (Exception e) {
            return false;
        }
    }

    public void updateUserInfo(Request req) {

        if (req == null || StringUtil.isEmpty(req.username)) {
            throw new MissingParameterException("jbb.mgt.exception.username.empty");
        }
        if (StringUtil.isEmpty(req.idcard) || !IDCardUtil.validate(req.idcard)) {
            throw new MissingParameterException("jbb.mgt.exception.idcard.error");
        }
        String username = this.user.isRealnameVerified() == true ? this.user.getUserName() : req.username;
        String idcard = this.user.isRealnameVerified() == true ? this.user.getIdCard() : req.idcard;
        String occupation = req.occupation;
        largeLoanApplyService.updateUserInfo(this.user.getUserId(), username, idcard, occupation);

    }

    public void checkLargeLoanApply() {
        int days = PropertyManager.getIntProperty("jbb.mgt.bnh.largeLoanDays", 7);
        UserEventLog userEventLog = largeLoanApplyService.selectLargeLoanApplyLog(this.user.getUserId());
        if (userEventLog != null) {
            if ((DateUtil.getCurrentTime() - userEventLog.getCreationDate().getTime() <= days
                * DateUtil.DAY_MILLSECONDES)) {
                throw new WrongParameterValueException("jbb.bnh.error.LargeLoanApply.days");
            }
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String username;
        public String idcard;
        public String occupation;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<LargeLoanOrg> largeLoanOrgs;

        public List<LargeLoanOrg> getLargeLoanOrgs() {
            return largeLoanOrgs;
        }

    }

}
