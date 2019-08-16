package com.jbb.mgt.rs.action.boss;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.BossOrder;
import com.jbb.mgt.core.service.BossOrderService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.BossException;
import com.jbb.server.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Service(BossAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BossAction extends BasicAction {

    public static final String ACTION_NAME = "BossAction";
    private static Logger logger = LoggerFactory.getLogger(BossAction.class);

    private Response response;

    @Autowired
    private BossOrderService bossOrderService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public String notifyBoss(HttpServletRequest request) throws IOException {
        logger.debug(">notifyBoss()");
        BufferedReader br = request.getReader();
        String str, result = "";
        while ((str = br.readLine()) != null) {
            result += str;
        }
        if (result == null || result.equals("")) {
            return null;
        }
        logger.info(" Boss Notify Result = " + result);
        JSONObject jsonObject = JSON.parseObject(result);
        if (jsonObject == null) {
            return null;
        }

        JSONObject msgBody = JSON.parseObject(jsonObject.getString("msg_body"));

        if (msgBody == null) {
            return null;
        }

        int status = msgBody.getInteger("status");
        String orderId = msgBody.getString("order_id");

        BossOrder bossOrder =bossOrderService.selectBossOrderByOrderId(orderId);

        if(bossOrder==null){
            logger.error(" Boss Notify Error BossOrder Is Null");
            throw new BossException("boss order is null");
        }

        if(status!=0){
            //回调异常
            logger.error(" Boss Notify Error  status = " + status);
            bossOrder.setStatus(String.valueOf(status));
            bossOrder.setNotifyStatus("1");
            bossOrder.setUpdateDate(DateUtil.getCurrentTimeStamp());
            bossOrderService.updateBossOrder(bossOrder);
            return null;
        }

        JSONObject resultJson = JSON.parseObject(msgBody.getString("result"));

        if (resultJson == null) {
            return null;
        }

        int decision = resultJson.getInteger("decision");
        String detail = resultJson.getString("detail");
        bossOrder.setStatus(String.valueOf(status));
        bossOrder.setNotifyStatus("1");
        bossOrder.setUpdateDate(DateUtil.getCurrentTimeStamp());
        bossOrder.setDecision(String.valueOf(decision));
        bossOrder.setResult(detail);
        bossOrderService.updateBossOrder(bossOrder);

        logger.debug("<notifyBoss()");
        return "{\"message\": \"回调处理成功\", \"code\": 0}";
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {}
}
