package com.jbb.mall.rs.action.order;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mall.core.dao.domain.Order;
import com.jbb.mall.core.service.MallProductsService;
import com.jbb.mall.core.service.OrderService;
import com.jbb.mall.server.rs.action.BasicAction;
import com.jbb.mall.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

/**
 * @author ThinkPad
 * @date 2019/01/22
 */

@Service(OrderAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OrderAction extends BasicAction {

    public static final String ACTION_NAME = "OrderAction";

    private static Logger logger = LoggerFactory.getLogger(OrderAction.class);

    private Response response;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MallProductsService mallProductsService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getOrderList(String type, Integer status) {
        logger.debug(">getOrderList() type=" + type + "status=" + status);
        this.response.orders = orderService.getOrderList(type, this.user.getUserId(), status);
        logger.debug("<getOrderList()");
    }

    public void insertOrder(String type, Request req) {
        logger.debug(">insertOrder() req=" + req);
        validateRequest(req);
        if (!mallProductsService.checkProduct(type, req.productId)) {
            throw new WrongParameterValueException("jbb.mall.error.product.notFound");
        }
        Order order = generateOrder(req, null);
        orderService.insertOrder(order);
        this.response.orderId = order.getOrderId();
        logger.debug("<insertOrder()");
    }

    private void validateRequest(Request req) {
        if (null == req) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "req");
        }
        if (null == req.productId || req.productId == 0) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "productId");
        }
        if (null == req.price) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "price");
        }
        if (StringUtil.isEmpty(req.deliveryDate)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "deliveryDate");
        }
        if (StringUtil.isEmpty(req.address)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "address");
        }
        if (StringUtil.isEmpty(req.customeName)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "customeName");
        }
    }

    private Order generateOrder(Request req, Order order) {
        order = order == null ? new Order() : order;
        Timestamp deliveryDateTs = Util.parseTimestamp(req.deliveryDate, this.getTimezone());
        order.setUserId(this.user.getUserId());
        order.setProductId(req.productId);
        order.setPrice(req.price);
        order.setCreationDate(DateUtil.getCurrentTimeStamp());
        order.setStatus(1);
        order.setDeliveryDate(deliveryDateTs);
        order.setAddress(req.address);
        order.setCustomeName(req.customeName);
        order.setCardMsg(req.cardMsg);
        order.setComment(req.comment);
        return order;
    }

    public void deleteOrder(String type, Integer orderId) {
        logger.debug(">deleteOrder() orderId=" + orderId);
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            throw new WrongParameterValueException("jbb.mall.error.order.notFound");
        }
        if (order.getIsDeleted()) {
            throw new WrongParameterValueException("jbb.mall.exception.order.isDeleted");
        }
        if (!mallProductsService.checkProduct(type, order.getProductId())) {
            throw new WrongParameterValueException("jbb.mall.error.product.notFound");
        }
        orderService.deleteOder(orderId);
        logger.debug("<deleteOrder()");
    }

    public void updateOrderStatus(String type, Integer orderId, Integer status) {
        logger.debug(">updateOrderStatus() type=" + type + " orderId=" + orderId + " status=" + status);
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            throw new WrongParameterValueException("jbb.mall.error.order.notFound");
        }
        if (order.getIsDeleted()) {
            throw new WrongParameterValueException("jbb.mall.exception.order.isDeleted");
        }
        if (!mallProductsService.checkProduct(type, order.getProductId())) {
            throw new WrongParameterValueException("jbb.mall.error.product.notFound");
        }
        orderService.updateOrderStatus(orderId, status);
        logger.debug("<updateOrderStatus()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<Order> orders;

        private Integer orderId;

        public List<Order> getOrders() {
            return orders;
        }

        public Integer getOrderId() {
            return orderId;
        }
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Request {
        public Integer productId;
        public Integer price;
        public String deliveryDate;
        public String address;
        public String customeName;
        public String cardMsg;
        public String comment;
    }

}
