package com.jbb.server.rs.action;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.domain.User;
import com.jbb.server.rs.pojo.ActionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Created by inspironsun on 2018/6/12
 */
@Service(CheckIdOrNameAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CheckIdOrNameAction extends BasicAction {

    private static Logger logger = LoggerFactory.getLogger(CheckIdOrNameAction.class);
    public static final String ACTION_NAME = "CheckIdOrNameAction";
    private Response response;

    @Override
    protected ActionResponse makeActionResponse() {
        return response = new Response();
    }

    public void checkIdOrName(String value) {
        logger.debug(">checkIdOrName");
        if (StringUtil.isEmpty(value)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "value");
        }
        String reg = "[\\u4e00-\\u9fa5]+";
        Pattern pattern = Pattern.compile("[0-9]*");
        boolean isNumber = pattern.matcher(value).matches();
        boolean isName = value.matches(reg);
        if (!isName && !isNumber) {
            throw new WrongParameterValueException("jbb.error.exception.ioufill.idOrName");
        }
        if (isNumber) {
            User user = this.coreAccountService.getUser(Integer.parseInt(value));
            if (user == null) {
                throw new WrongParameterValueException("jbb.error.exception.ioufill.idOrName");
            }
            this.response.isJbbId = true;
        }
        if (isName) {
            if (value.length() > 5) {
                throw new WrongParameterValueException("jbb.error.exception.ioufill.idOrName");
            }
            this.response.isUsername = true;
        }
        logger.debug("<checkIdOrName");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public boolean isUsername;
        public boolean isJbbId;
    }

}
