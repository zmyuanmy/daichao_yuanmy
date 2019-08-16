package com.jbb.mgt.rs.action.XjlAntiFraudResult;

import com.jbb.mgt.core.service.XjlAntFraudResultService;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.mgt.server.rs.action.BasicAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service(XjlAntiFraudResultAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XjlAntiFraudResultAction extends BasicAction {

    public static final String ACTION_NAME = "XjlAntiFraudResultAction";

    @Autowired
    private XjlAntFraudResultService xjlAntFraudResultService;

    public ActionResponse editResult(int orderId, String resultJson){
        xjlAntFraudResultService.editXjlFraudResult(orderId,resultJson);
        ActionResponse actionResponse = new ActionResponse();
        actionResponse.setResultCodeAndMessage(0,"更新成功！");
        return actionResponse;
    }



}
