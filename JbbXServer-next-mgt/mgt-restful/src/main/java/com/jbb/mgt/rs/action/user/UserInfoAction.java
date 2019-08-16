package com.jbb.mgt.rs.action.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserCard;
import com.jbb.mgt.core.service.UserCardService;
import com.jbb.mgt.core.service.UserJobService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.PropertyManager;

@Service(UserInfoAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserInfoAction extends BasicAction {

    public static final String ACTION_NAME = "UserInfoAction";

    private static Logger logger = LoggerFactory.getLogger(UserInfoAction.class);

    private Response response;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private UserJobService userJobService;

    @Autowired
    private UserCardService userCardService;

    public void selectUserInfo() {
        logger.debug(">selectUserInfo()");
        String payProductType = PropertyManager.getProperty("jbb.mgt.pay.product.type");
        String payProductId = "";
        if(payProductType.equals("1")){
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.helibao.id");
        }else{
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.changjie.id");
        }
        List<UserCard> userCards = userCardService.selectUserCards(this.user.getUserId(), payProductId);
        boolean cardStatus = userCards == null ? false : true;
        User userInfo = this.user;
        userInfo.setKey(null);
        userInfo.setJobInfo(userJobService.selectJobInfoByAddressId(this.user.getUserId()));
        userInfo.setCardStatus(cardStatus);
        response.user = userInfo;
        logger.debug(">selectUserInfo()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private User user;

        public User getUser() {
            return user;
        }

    }

}
