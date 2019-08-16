package com.jbb.server.rs.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.ApiCallLimitException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.IDCardUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.service.AliyunService;
import com.jbb.server.core.service.ProductService;
import com.jbb.server.rs.pojo.ActionResponse;

@Component(CheckIdCardAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CheckIdCardAction extends BasicAction {
    private static Logger logger = LoggerFactory.getLogger(CheckIdCardAction.class);
    public static final String ACTION_NAME = "CheckIdCardAction";

    private Response response;

    @Override
    protected ActionResponse makeActionResponse() {
        return response = new Response();
    }

    @Autowired
    private AliyunService aliyunService;

    @Autowired
    private ProductService productService;

    public void checkIdCard(String cardno, String username) {
        logger.debug(">checkIdCard ,idcardNumber=" + cardno + " ,username=" + username);

        if (StringUtil.isEmpty(cardno) || !IDCardUtil.validate(cardno)) {
            throw new WrongParameterValueException("jbb.error.exception.idcard.wrongIdCard","zh");
        }
        if (StringUtil.isEmpty(username)) {
            throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "username");
        }

//        if (this.coreAccountService.checkIdcardExist(cardno, this.user.getUserId())) {
//            throw new WrongParameterValueException("jbb.error.exception.idcardnoExist", "zh");
//        }
        //add by twh , 去除掉一个身份证只能绑定一个手机的限制 
        if(this.coreAccountService.checkUserVerified(this.user.getUserId(), Constants.VERIFY_TYPE_REALNAME, 2)){
            //如果用户进行了身份证认证，则用户不能再修改身份证信息
            //如果提交的身份证信息同库里不一样，再报错
            if(!cardno.equals(this.user.getIdCardNo())){
                throw new WrongParameterValueException("jbb.error.exception.idcardNotSame", "zh");
            }
        }

        int fee = PropertyManager.getIntProperty("jbb.wx.pay.auth.fee", 0);
        if (fee != 0 && productService.getProductCount(this.user.getUserId(), Constants.PRODUCT_AUTH) == 0) {
            throw new ApiCallLimitException("jbb.error.exception.auth.notPay");
        }
        this.coreAccountService.saveUserVerifyResult(this.user.getUserId(), Constants.VERIFY_TYPE_REALNAME, 1, true);
        logger.debug("<checkIdCard()");
    }

    public void checkFaceIdCard(String cardno, String username) {
        logger.debug(">checkIdCard ,idcardNumber=" + cardno + " ,username=" + username);

        checkIdCard(cardno, username);
        
        this.response.verifyResult = true;

        logger.debug("<checkIdCard()");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private boolean verifyResult;

        public boolean isVerifyResult() {
            return verifyResult;
        }

    }
}
