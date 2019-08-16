package com.jbb.server.rs.action;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.ApiCallLimitException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.core.domain.Iou;
import com.jbb.server.core.domain.User;
import com.jbb.server.core.service.IousService;
import com.jbb.server.core.service.ProductService;
import com.jbb.server.rs.pojo.ActionResponse;
import com.jbb.server.rs.pojo.request.ReIouFill;
import com.jbb.server.rs.pojo.request.ReMgtIou;
import com.jbb.server.shared.rs.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Timestamp;
import java.util.TimeZone;

/**
 * Created by inspironsun on 2018/5/29
 */
@Service(UserMgtIouCreatAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserMgtIouCreatAction extends BasicAction {
    private static Logger logger = LoggerFactory.getLogger(UserMgtIouCreatAction.class);
    public static final String ACTION_NAME = "UserMgtIouCreatAction";

    private Response response;

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private ProductService productService;

    @Autowired
    private IousService iousService;

    @Override
    protected ActionResponse makeActionResponse() {
        return response = new Response();
    }

    /**
     * 出借人从MGT打借条
     */
    public void insertMgtIou(ReMgtIou iouMgt) {
        if (logger.isDebugEnabled()) {
            logger.debug(">insertMgtIou()");
        }

        // 检查利率是否大于24%
        if (iouMgt.getAnnualRate() == 0 || iouMgt.getAnnualRate() > 24) {
            throw new WrongParameterValueException("jbb.integrate.exception.annualrateerror", "zh", "annualRate");
        }

        boolean isLenderExist = this.coreAccountService.checkUserExist(iouMgt.getLenderId());
        if (!isLenderExist) {
            throw new WrongParameterValueException("jbb.error.exception.ioufill.userNotExist", "zh",
                    String.valueOf(iouMgt.getLenderId()));
        }

        boolean isBorrowerExist = this.coreAccountService.checkUserExist(iouMgt.getBorrowerId());
        if (!isBorrowerExist) {
            throw new WrongParameterValueException("jbb.error.exception.ioufill.userNotExist", "zh",
                    String.valueOf(iouMgt.getBorrowerId()));
        }

        Iou iou = copyIouFromMgt(iouMgt);

        // 检查借款人和出借人 是否实名认证
        User borrower = this.coreAccountService.getUser(iou.getBorrowerId());
        if (borrower == null || !this.coreAccountService.isVerified(iou.getBorrowerId(), false)) {
            throw new ApiCallLimitException("jbb.error.exception.ioufill.borrowerNotVerified");
        }

        User lender = this.coreAccountService.getUser(iou.getLenderId());
        if (lender == null || !this.coreAccountService.isVerified(iou.getLenderId(), true)) {
            throw new ApiCallLimitException("jbb.error.exception.ioufill.lenderNotVerified");
        }

        // 如果是借款人，检查是否有打借条的权益
        int fee = PropertyManager.getIntProperty("jbb.wx.pay.iou.fee", 0);
        if (fee != 0 && iouMgt.getLenderId() == iou.getBorrowerId()
            && productService.getProductCount(iouMgt.getLenderId(), Constants.PRODUCT_IOU) == 0) {
            throw new ApiCallLimitException("jbb.error.exception.ioufill.notPay");
        }

        if (iou.getBorrowerId() == iou.getLenderId()) {
            throw new WrongParameterValueException("jbb.error.exception.ioufill.userSame", "zh");
        }

        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);

            iousService.insertIouMGt(iou);
            this.response.iouCode = iou.getIouCode();
            txManager.commit(txStatus);
            txStatus = null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("insertIou error , error = " + e.getMessage());
        } finally {
            // roll back not committed transaction
            rollbackTransaction(txStatus);
        }

        logger.debug("<insertMgtIou()");
    }

    public Iou copyIouFromMgt(ReMgtIou mgtIou) {
        Iou iou = new Iou();
        TimeZone tz = getTimezone();
        Timestamp borrowingDate = Util.parseTimestamp(mgtIou.getBorrowingDate(), tz);
        Timestamp repaymentDate = Util.parseTimestamp(mgtIou.getRepaymentDate(), tz);
        iou.setRepaymentDate(repaymentDate);
        iou.setBorrowingDate(borrowingDate);
        iou.setAnnualRate(mgtIou.getAnnualRate());
        iou.setBorrowingAmount(mgtIou.getBorrowingAmount());
        iou.setLenderId(mgtIou.getLenderId());
        iou.setBorrowerId(mgtIou.getBorrowerId());
        iou.setStatus(30);
        Timestamp now = DateUtil.getCurrentTimeStamp();
        iou.setLastUpdateStatusDate(now);
        iou.setCreationDate(now);
        return iou;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public String iouCode;
    }

    private void rollbackTransaction(TransactionStatus txStatus) {
        if (txStatus == null) {
            return;
        }

        try {
            txManager.rollback(txStatus);
        } catch (Exception er) {
            logger.warn("Cannot rollback transaction", er);
        }
    }
}
