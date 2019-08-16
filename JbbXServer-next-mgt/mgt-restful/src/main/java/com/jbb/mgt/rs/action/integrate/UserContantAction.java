package com.jbb.mgt.rs.action.integrate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.UserContact;
import com.jbb.mgt.core.domain.UserContant;
import com.jbb.mgt.core.service.UserContantService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.StringUtil;

@Component(UserContantAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserContantAction extends BasicAction {

    public static final String ACTION_NAME = "UserContantAction";

    private static Logger logger = LoggerFactory.getLogger(UserContantAction.class);

    @Autowired
    private UserContantService userContantService;

    public void insertContants(Request req, Integer jbbUserId) {
        logger.debug(">insertContants");
        if (CollectionUtils.isEmpty(req.contacts)) {
            logger.debug("<insertContants missing contacts");
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "userContants");
        }
        if (jbbUserId == null || jbbUserId == 0) {
            logger.debug("<insertContants missing jbbUserId");
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "jbbUserId");
        }

        List<UserContant> mgtContacts = new ArrayList<UserContant>();

        for (int i = 0; i < req.contacts.size(); i++) {
            UserContact uc = req.contacts.get(i);
            if (CommonUtil.isNullOrEmpty(uc.getPhoneNumbers())) {
                continue;
            }
            if (StringUtil.isEmpty(uc.getDisplayName()) || uc.getDisplayName().equals("null")) {
                uc.setDisplayName("");
            }
            uc.getPhoneNumbers().forEach(phoneNumber -> {
                mgtContacts.add(new UserContant(jbbUserId, phoneNumber, uc.getDisplayName()));
            });
        }

        userContantService.insertOrUpdateUserContant(mgtContacts);
        logger.debug("<insertContants");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public List<UserContact> contacts;
    }

}
