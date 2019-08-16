package com.jbb.mgt.rs.action.usercard;

import java.util.List;

import com.jbb.mgt.changjiepay.service.ChangJieQuickPayService;
import com.jbb.mgt.core.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.UserCard;
import com.jbb.mgt.core.service.UserCardService;
import com.jbb.mgt.helipay.service.QuickPayService;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;

@Service(UserCardAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserCardAction extends BasicAction {

    public static final String ACTION_NAME = "UserCardAction";

    private static Logger logger = LoggerFactory.getLogger(UserCardAction.class);

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    private Response response;

    @Autowired
    private UserCardService userCardService;

    @Autowired
    private QuickPayService quickPayService;

    @Autowired
    private ChangJieQuickPayService changJieQuickPayService;

    public void getUserCards() {
        logger.debug(">getUserCards");
        String payProductType = PropertyManager.getProperty("jbb.mgt.pay.product.type");
        String payProductId = "";
        String productame = "";
        if (payProductType.equals("1")) {
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.helibao.id");
            productame = PropertyManager.getProperty("jbb.xjl.payProduct.helibao.name");
        } else {
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.changjie.id");
            productame = PropertyManager.getProperty("jbb.xjl.payProduct.changjie.name");
        }
        List<UserCard> userCards = userCardService.selectUserCards(this.user.getUserId(), payProductId);
        if (userCards != null && userCards.size() != 0) {
            this.response.cards = userCards;
        }
        this.response.payProductId = payProductId;
        this.response.payProductName = productame;
        logger.debug("<getUserCards");
    }

    public void insertUserCards(Request request, String orderId) throws Exception {
        logger.debug(">insertUserCards");
        if (StringUtil.isEmpty(request.bankCode)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "bankCode");
        }

        if (StringUtil.isEmpty(request.cardNo)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "cardNo");
        }

        if (StringUtil.isEmpty(request.phoneNumber)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "phoneNumber");
        }

        if (StringUtil.isEmpty(request.msgCode)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "msgCode");
        }

        if (StringUtil.isEmpty(orderId)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "orderId");
        }

        String payProductType = PropertyManager.getProperty("jbb.mgt.pay.product.type");
        String payProductId = "";
        if (payProductType.equals("1")) {
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.helibao.id");
        } else {
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.changjie.id");
        }
        UserCard userCardHistory
            = userCardService.selectUserCardByCardNo(this.user.getUserId(), request.cardNo, payProductId);
        List<UserCard> userCards = userCardService.selectUserCards(this.user.getUserId(), payProductId);
        if (userCardHistory != null) {
            // 曾经绑定过，更新删除状态
            userCardHistory.setDeleted(false);
            userCardHistory.setAcceptLoanCard(userCards.size() == 0 ? true : false);
            userCardService.updateUserCard(userCardHistory);
            return;
        }

        // 未绑定过 新增
        // 调用绑卡接口
        String productType = PropertyManager.getProperty("jbb.mgt.pay.product.type", "1");
        if (productType.equals("1")) {
            quickPayService.quickPayBindCard(orderId, String.valueOf(this.user.getUserId()), this.user.getIdCard(),
                this.user.getUserName(), request.cardNo, request.msgCode, request.phoneNumber);
        } else {
            changJieQuickPayService.apiAuthSms(orderId, request.msgCode);
        }

        UserCard userCardNew = new UserCard();
        userCardNew.setDeleted(false);
        userCardNew.setBankCode(request.bankCode);
        userCardNew.setCardNo(request.cardNo);
        userCardNew.setCreationDate(DateUtil.getCurrentTimeStamp());
        userCardNew.setPayProductId(payProductId);
        userCardNew.setPhoneNumber(request.phoneNumber);
        userCardNew.setUserId(this.user.getUserId());
        userCardNew.setAcceptLoanCard(userCards.size() == 0 ? true : false);
        userCardService.insertUserCard(userCardNew);

        logger.debug(">insertUserCards");
    }

    /**
     * 设置一张银行卡为接受贷款转账的卡 ，需要先将用户其他的卡的is_accept_loan_card 设为false
     * 
     * @param cardNo
     *
     */
    public void acceptUserCard(String cardNo) {
        logger.debug(">acceptUserCard");
        if (StringUtil.isEmpty(cardNo)) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "cardNo");
        }
        String payProductType = PropertyManager.getProperty("jbb.mgt.pay.product.type");
        String payProductId = "";
        if (payProductType.equals("1")) {
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.helibao.id");
        } else {
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.changjie.id");
        }
        userCardService.acceptUserCard(this.user.getUserId(), cardNo, payProductId);
        logger.debug("<acceptUserCard");
    }

    public void removeAcceptUserCard(String cardNo) {
        logger.debug(">removeAcceptUserCard");
        String payProductType = PropertyManager.getProperty("jbb.mgt.pay.product.type");
        String payProductId = "";
        if (payProductType.equals("1")) {
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.helibao.id");
        } else {
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.changjie.id");
        }
        UserCard userCardHistory = userCardService.selectUserCardByCardNo(this.user.getUserId(), cardNo, payProductId);
        if (userCardHistory.isAcceptLoanCard()) {
            throw new WrongParameterValueException("jbb.xjl.error.userCard.isAcceptLoanCard", "zh", "cardNo");
        }
        if (userCardHistory != null) {
            userCardHistory.setAcceptLoanCard(false);
            userCardHistory.setDeleteDate(DateUtil.getCurrentTimeStamp());
            userCardHistory.setDeleted(true);
            userCardService.updateUserCard(userCardHistory);
        }
        logger.debug("<removeAcceptUserCard");
    }

    /**
     * 获取绑卡验证码
     * 
     * @param request
     * @param type 1绑卡，2快捷支付
     */
    public void getMsgCode(Request request, String type) throws Exception {
        logger.debug(">getMsgCode");
        if (StringUtil.isEmpty(request.bankCode)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "bankCode");
        }

        if (StringUtil.isEmpty(request.cardNo)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "cardNo");
        }

        if (StringUtil.isEmpty(request.phoneNumber)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "phoneNumber");
        }

        if (StringUtil.isEmpty(type)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "type");
        }
        String orderId = HeliUtil.generateOrderId();

        String productType = PropertyManager.getProperty("jbb.mgt.pay.product.type", "1");
        if (productType.equals("1")) {
            quickPayService.agreementPayBindCardValidateCode(String.valueOf(this.user.getUserId()), orderId,
                request.phoneNumber, request.cardNo, user.getUserName(), user.getIdCard());
        } else {
            changJieQuickPayService.bizApiAuthReq(String.valueOf(this.user.getUserId()), orderId, user.getIdCard(),
                request.cardNo, request.phoneNumber, user.getUserName(), null, null);
        }
        this.response.orderId = orderId;
        logger.debug("<getMsgCode");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String msgCode;
        public String bankCode;
        public String cardNo;
        public String phoneNumber;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public String payProductName;
        public String payProductId;
        public String orderId;
        public List<UserCard> cards;
    }

}
