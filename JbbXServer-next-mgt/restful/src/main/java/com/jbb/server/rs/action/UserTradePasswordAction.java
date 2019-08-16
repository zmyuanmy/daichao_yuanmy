package com.jbb.server.rs.action;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.service.UserTradePasswordService;
import com.jbb.server.core.util.PasswordUtil;
import com.jbb.server.rs.pojo.ActionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by inspironsun on 2018/6/6
 */

@Service(UserTradePasswordAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserTradePasswordAction extends BasicAction {

    private static Logger logger = LoggerFactory.getLogger(UserTradePasswordAction.class);

    public static final String ACTION_NAME = "UserTradePasswordAction";

    private Response response;

    @Autowired
    private UserTradePasswordService userTradePasswordService;

    @Override
    protected ActionResponse makeActionResponse() {
        return response = new Response();
    }

    public void setUserTradePassword(String password) {
        logger.debug(">setUserPassword password=" + password);
        if (!PasswordUtil.isValidTradePassword(password)) {
            throw new WrongParameterValueException("jbb.error.exception.tradePasswordError");
        }
        String tradePassword = this.user.getTradePassword();
        if (!StringUtil.isEmpty(tradePassword)) {
            throw new WrongParameterValueException("jbb.error.exception.tradePasswordExist");
        }

        userTradePasswordService.setUserTradePassword(this.user.getUserId(), password);

        logger.debug("<setUserTradePassword");

    }

    public void updateUserTradePassword(String password, String msgCode, String idcardNo, String step) {
        logger.debug(">updateUserTradePassword ");
        if (StringUtil.isEmpty(idcardNo)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "idcardNo");
        }
        if (StringUtil.isEmpty(msgCode)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "msgCode");
        }
        if (StringUtil.isEmpty(step)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "step");
        }
        userTradePasswordService.updateUserTradePassword(this.user.getUserId(), msgCode, idcardNo, password, step);
        logger.debug("<updateUserTradePassword ");
    }

    public void getUserHasTradePassword() {
        logger.debug(">getUserHasTradePassword ");
        String tradePassword = this.user.getTradePassword();
        if (!StringUtil.isEmpty(tradePassword)) {
            this.response.hasTradePassword = true;
        }else{
            this.response.hasTradePassword = false;
        }
        logger.debug("<getUserHasTradePassword ");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public Boolean hasTradePassword;
    }
}
