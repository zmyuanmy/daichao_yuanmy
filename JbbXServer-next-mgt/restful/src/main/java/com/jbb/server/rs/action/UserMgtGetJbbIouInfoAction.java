package com.jbb.server.rs.action;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.domain.Iou;
import com.jbb.server.core.service.IousService;
import com.jbb.server.rs.pojo.ActionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by inspironsun on 2018/5/29
 */


@Service(UserMgtGetJbbIouInfoAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserMgtGetJbbIouInfoAction extends BasicAction {
    private static Logger logger = LoggerFactory.getLogger(UserMgtGetJbbIouInfoAction.class);
    public static final String ACTION_NAME = "UserMgtGetJbbIouInfoAction";

    private Response response;

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
            = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private IousService iouService;

    public void getIouInfo(String iouCode){
        logger.info(">getIouInfo() iouCode" + iouCode);
        if(StringUtil.isEmpty(iouCode)){
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "iouCode");
        }
        Iou iou = iouService.getIouByIouCode(iouCode);
        this.response.iouMessage = iou;
        logger.info("<getIouInfo() ");

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public Iou iouMessage;
    }
}
