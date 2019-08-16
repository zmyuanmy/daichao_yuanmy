package com.jbb.mgt.rs.action.xjlPay;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.changjiepay.util.BaseConstant;
import com.jbb.mgt.changjiepay.util.ChangJieUtil;
import com.jbb.mgt.core.domain.UserCard;
import com.jbb.mgt.core.service.PayService;
import com.jbb.mgt.core.service.UserCardService;
import com.jbb.mgt.core.service.XjlUserOrderService;
import com.jbb.mgt.core.service.XjlUserService;
import com.jbb.mgt.helipay.vo.AsynchronousNotificationVo;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.mgt.server.rs.pojo.RsHelipayNotifyVo;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.RSA;
import com.jbb.server.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.PublicKey;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service(QuickPayAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class QuickPayAction extends BasicAction {

    public static final String ACTION_NAME = "QuickPayAction";

    private static Logger logger = LoggerFactory.getLogger(QuickPayAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    @Qualifier("ChangJiePayServices")
    private PayService changJiePayServices;

    @Autowired
    @Qualifier("HeLiPayServices")
    private PayService heLiPayServices;

    @Autowired
    private UserCardService userCardService;

    @Autowired
    private XjlUserService xjlUserService;

    @Autowired
    private XjlUserOrderService xjlUserOrderService;

    public void sendUserOrderMesCode(Request request, String ipAddress) throws Exception {
        logger.debug(">sendUserOrderMesCode");
        if (request.applyId == null ) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "applyId");
        }

        if (StringUtil.isEmpty(request.cardNo)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "cardNo");
        }

        if (StringUtil.isEmpty(ipAddress)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "ipAddress");
        }

        if (StringUtil.isEmpty(request.terminalType)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "terminalType");
        }

        if (StringUtil.isEmpty(request.terminalId)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "terminalId");
        }
        String orderId = "";
        String productType = PropertyManager.getProperty("jbb.mgt.pay.product.type", "1");
        String productId = "";
        if(productType.equals("1")){
            productId = PropertyManager.getProperty("jbb.xjl.payProduct.helibao.id");
        }else{
            productId = PropertyManager.getProperty("jbb.xjl.payProduct.changjie.id");
        }
        UserCard userCard = userCardService.selectUserCardByCardNo(this.user.getUserId(), request.cardNo,productId );
        if(productType.equals("1")){
            orderId =  heLiPayServices.sendPayMsgCode(this.user, request.applyId, request.cardNo, request.terminalId, request.terminalType, ipAddress);
        }else{
            if(productId.equals("cj")){
                changJiePayServices.directPay(this.user, request.applyId, request.cardNo, request.terminalId, request.terminalType, ipAddress );
            }
            orderId =  changJiePayServices.sendPayMsgCode(this.user, request.applyId, request.cardNo, request.terminalId, request.terminalType, ipAddress);
        }
        this.response.orderId = orderId;
        logger.debug("<sendUserOrderMesCode");
    }

    public void quickPayConfirmPay(String orderId, String msgCode, String ipAddress) throws Exception {
        logger.debug(">quickPayConfirmPay");

        if (StringUtil.isEmpty(orderId)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "orderId");
        }
        if (StringUtil.isEmpty(msgCode)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "msgCode");
        }
        String productType = PropertyManager.getProperty("jbb.mgt.pay.product.type", "1");
        if (productType.equals("1")) {
            heLiPayServices.payConfirm(orderId, msgCode, ipAddress);
        } else {
            changJiePayServices.payConfirm(orderId, msgCode, ipAddress);
        }

        logger.debug(">quickPayConfirmPay");
    }

    public String notifyHeLiPayResult(RsHelipayNotifyVo reqVo, HttpServletRequest request,
        HttpServletResponse response) {
        logger.debug("> notifyHeLiPayResult()");

        AsynchronousNotificationVo rspVo = reqVo.createAsynchronousNotificationVo();
        PublicKey cerPubKey = RSA.getCerPublicKey();
        boolean verified = RSA.verifySign(rspVo.getSigned(), rspVo.getSign(), cerPubKey);

        // 检查签名
        if (!verified) {
            logger.error("合利宝回调参数验签失败, reqVo = " + reqVo);
        }

        // 处理后续逻辑
        String retCode = reqVo.getRt2_retCode();
        if (!"0000".equals(retCode)) {
            logger.error("合利宝回调失败, code is not '0000',  reqVo = " + reqVo);
            return "fail";
        }
        String orderId = reqVo.getRt5_orderId();
        boolean verify_result = xjlUserOrderService.notifyHeLiPay(orderId);
        return verify_result ? "success" : "fail";
    }

    public void notifyChangJieResult(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("> notifyChangJieResult()");
        Enumeration enu = request.getParameterNames();
        String requestString = "";
        while (enu.hasMoreElements()) {
            String paraName = (String)enu.nextElement();
            requestString += paraName + ": " + request.getParameter(paraName) + " , ";
        }
        logger.info("ChangJie RequestString " + requestString);
        String sign = request.getParameter("sign");
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("notify_id", request.getParameter("notify_id"));
        paramMap.put("notify_type", request.getParameter("notify_type"));
        paramMap.put("notify_time", request.getParameter("notify_time"));
        paramMap.put("_input_charset", request.getParameter("_input_charset"));
        paramMap.put("version", request.getParameter("version"));
        paramMap.put("outer_trade_no", request.getParameter("outer_trade_no"));
        paramMap.put("inner_trade_no", request.getParameter("inner_trade_no"));
        paramMap.put("trade_status", request.getParameter("trade_status"));
        paramMap.put("trade_amount", request.getParameter("trade_amount"));
        paramMap.put("gmt_create", request.getParameter("gmt_create"));
        paramMap.put("gmt_payment", request.getParameter("gmt_payment"));
        paramMap.put("extension", request.getParameter("extension"));
        String text = ChangJieUtil.createLinkString(paramMap, false);
        boolean verified = true;
        String publicKey = PropertyManager.getProperty("jbb.pay.changjie.public.key");
        try {
            verified = com.jbb.mgt.changjiepay.util.RSA.verify(text, sign, publicKey, BaseConstant.CHARSET);
        } catch (Exception e) {
            logger.error("verify error e = " + e);
        }
        // 检查签名
        if (!verified) {
            logger.error("畅捷回调参数验签失败, requestString = " + requestString);
            return;
        }
        // 处理后续逻辑
        String tradeStatus = request.getParameter("trade_status");
        if (!tradeStatus.equals("TRADE_SUCCESS")) {
            logger.error("畅捷回调失败, tradeStatus is not 'TRADE_SUCCESS',  requestString = " + requestString);
            return;
        }
        String orderId = request.getParameter("outer_trade_no");
        boolean verify_result = xjlUserOrderService.notifyHeLiPay(orderId);

        if (!verify_result) {
            logger.error("畅捷状态更新失败, orderId = " + orderId);
            return;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String applyId;
        public String cardNo;
        public String terminalId;
        public String terminalType;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public String orderId;
    }

}
