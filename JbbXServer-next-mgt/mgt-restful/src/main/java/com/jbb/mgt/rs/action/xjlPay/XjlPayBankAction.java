package com.jbb.mgt.rs.action.xjlPay;

import java.util.List;

import com.jbb.server.common.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.XjlPayBank;
import com.jbb.mgt.core.service.XjlPayBankService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

@Service(XjlPayBankAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XjlPayBankAction extends BasicAction {
    public static final String ACTION_NAME = "XjlPayBankAction";

    private static Logger logger = LoggerFactory.getLogger(XjlPayBankAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private XjlPayBankService xjlPayBankService;

    public void getXjlPayBank() {
        logger.debug(">getXjlPayBank()");
        String payProductType = PropertyManager.getProperty("jbb.mgt.pay.product.type");
        String payProductId = "";
        if (payProductType.equals("1")) {
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.helibao.id");
        } else {
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.changjie.id");
        }
        this.response.banks = xjlPayBankService.selectXjlPayBank(payProductId);
        logger.debug("<getXjlPayBank()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private List<XjlPayBank> banks;

        public List<XjlPayBank> getBanks() {
            return banks;
        }
    }

}
