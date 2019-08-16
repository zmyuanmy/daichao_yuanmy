package com.jbb.server.rs.action;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.domain.IouVerify;
import com.jbb.server.rs.pojo.ActionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by inspironsun on 2018/6/29
 */

@Service(IouVerifyAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class IouVerifyAction extends BasicAction {

    private static Logger logger = LoggerFactory.getLogger(IouVerifyAction.class);

    public static final String ACTION_NAME = "IouVerifyAction";

    private Response response;

    @Override
    protected ActionResponse makeActionResponse() {
        return response = new Response();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public IouVerify lender;
        public IouVerify borrower;
        public IouVerify userVerify;
    }

    public void iouVerify(Integer userId, Boolean isLender) {
        logger.debug(">iouVerify");
        // 如果userId 跟 isLender都不传，那么表示应用场景是验证重置交易密码。
        int fee = PropertyManager.getIntProperty("jbb.wx.pay.iou.fee", 0);
        boolean hasTradPassword = false;

        if (!StringUtil.isEmpty(this.user.getTradePassword())) {
            hasTradPassword = true;
        }

        IouVerify userVerifyNow = this.coreAccountService.getIouVerifyByUserId(this.user.getUserId());
        userVerifyNow.setHasTradePassword(hasTradPassword);

        if (userId != null && isLender != null) {
            //验证用户是否存在
            boolean isUserExist = this.coreAccountService.checkUserExist(userId);
            if (!isUserExist) {
                throw new WrongParameterValueException("jbb.error.exception.ioufill.userNotExist", "zh",
                        String.valueOf(userId));
            }

            if (isLender) {
                // 出借人打借条
                IouVerify borrower = this.coreAccountService.getIouVerifyByUserId(userId);
                this.response.lender = userVerifyNow;
                this.response.borrower = borrower;
            } else {
                // 借款人打借条
                IouVerify Lender = this.coreAccountService.getIouVerifyByUserId(userId);
                this.response.lender = Lender;
                this.response.borrower = userVerifyNow;
            }

        }

        if (userId == null && isLender == null) {
            // 验证重置交易密码
            this.response.userVerify = userVerifyNow;
        }
        logger.debug("<iouVerify");
    }

}
