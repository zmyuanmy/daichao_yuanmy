package com.jbb.server.rs.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.core.domain.Iou;
import com.jbb.server.core.service.IouStatusService;
import com.jbb.server.core.service.IousService;
import com.jbb.server.rs.pojo.ActionResponse;

/**
 * Created by inspironsun on 2018/5/29
 */

@Service(UserMgtIouUpdateAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserMgtIouUpdateAction extends BasicAction {

    private static Logger logger = LoggerFactory.getLogger(UserMgtIouCreatAction.class);
    public static final String ACTION_NAME = "UserMgtIouUpdateAction";

    @Autowired
    private IousService iousService;

    @Autowired
    private IouStatusService iouStatusService;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {}

    public void changeMgtStatus(String iouCode, int status, String extentionDateTs) {
        logger.info(">changeMgtStatus");
        Iou iou = iousService.getIouByIouCode(iouCode);
        if (iou == null) {
            throw new ObjectNotFoundException("jbb.error.exception.iouNotFournd", "zh", iouCode);
        }

        if (iou.isDeleted() || iou.isLenderDeleted()) {
            throw new ObjectNotFoundException("jbb.error.exception.iouDeleted", "zh", iouCode);
        }

        int lenderId = iou.getLenderId();

        iouStatusService.updateStatus(iou, status, lenderId, extentionDateTs, lenderId);

        logger.info("<changeMgtStatus");

    }

}
