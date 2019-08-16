package com.jbb.mall.server.rs.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mall.rs.action.appinfo.AppInfoAction;
import com.jbb.mall.rs.action.classification.ClassificationAction;
import com.jbb.mall.rs.action.mallBanner.MallBannerAction;
import com.jbb.mall.rs.action.mallBanner.MallBannerProductDetailAction;
import com.jbb.mall.rs.action.mallBanner.MallBannerProductsAction;
import com.jbb.mall.rs.action.order.OrderAction;
import com.jbb.mall.rs.action.product.ProductAction;
import com.jbb.mall.rs.action.userFavor.UserFavorAction;
import com.jbb.mall.server.rs.pojo.ActionResponse;
import com.jbb.server.common.util.StringUtil;

@Path("mall")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JbbMgtMallServices extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JbbMgtMallServices.class);

    /**
     * 查询测试
     * 
     * @return
     */
    @GET
    @Path("/get")
    public ActionResponse getMallBanner() {
        logger.debug(">getMallBanner() ");
        MallBannerAction action = getBean(MallBannerAction.ACTION_NAME);
        action.getMallBanner();
        logger.debug(">getMallBanner()");
        return action.getActionResponse();
    }

    /**
     * 首页banner、分类及各类的明细列表
     *
     * @param
     * @return
     */
    @GET
    @Path("/{type}/homepage")
    public ActionResponse getMallBannerHome(@HeaderParam("API_KEY") String userKey, @PathParam("type") String type) {
        logger.debug(">getFlowerHom() ");
        MallBannerProductsAction action = getBean(MallBannerProductsAction.ACTION_NAME);
        if (!StringUtil.isEmpty(userKey)) {
            action.validateEntryUserKey(userKey);
        }
        action.getMallBannerHome(type);
        logger.debug(">getFlowerHom()");
        return action.getActionResponse();
    }

    /**
     * 商品详情
     *
     * @param
     * @return
     */
    @GET
    @Path("/{type}/products/{productId}")
    public ActionResponse getProductDetail(@HeaderParam("API_KEY") String userKey, @PathParam("type") String type,
        @PathParam("productId") Integer productId) {
        logger.debug(">getProductDetail() ");
        MallBannerProductDetailAction action = getBean(MallBannerProductDetailAction.ACTION_NAME);
        if (!StringUtil.isEmpty(userKey)) {
            action.validateEntryUserKey(userKey);
        }
        action.getMallProductDetail(type, productId);
        logger.debug(">getProductDetail()");
        return action.getActionResponse();
    }

    /**
     * APP服务相关信息接口
     * 
     * @param
     * @return
     */
    @GET
    @Path("/{type}/appInfo")
    public ActionResponse getAppinfo(@PathParam("type") String type, @QueryParam("appName") String appName) {
        logger.debug(">getAppinfo()");
        AppInfoAction action = getBean(AppInfoAction.ACTION_NAME);
        action.getAppinfo(type, appName);
        logger.debug(">getAppinfo()");
        return action.getActionResponse();
    }

    /**
     * 获取商品分类方式和分类列表
     *
     * @param
     * @return
     */
    @GET
    @Path("/{type}/classifications")
    public ActionResponse getClassificationsByclassification(@PathParam("type") String type,
        @QueryParam("classification") String[] classification, @QueryParam("getDetail") Boolean getDetail) {
        logger.debug(">getClassificationsByclassification() ");
        ClassificationAction action = getBean(ClassificationAction.ACTION_NAME);
        action.getClassificationsByclassification(type, classification, getDetail);
        logger.debug(">getClassificationsByclassification()");
        return action.getActionResponse();
    }

    /**
     * 用户收藏产品
     *
     * @param
     * @return
     */
    @PUT
    @Path("/{type}/favor/{productId}")
    public ActionResponse updateUserFavor(@HeaderParam("API_KEY") String userKey, @PathParam("type") String type,
        @PathParam("productId") Integer productId) {
        logger.debug(">updateUserFavor() ");
        UserFavorAction action = getBean(UserFavorAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.updateUserFavor(type, productId);
        logger.debug(">updateUserFavor()");
        return action.getActionResponse();
    }

    /**
     * 用户解除收藏产品
     *
     * @param
     * @return
     */
    @PUT
    @Path("/{type}/unfavor/{productId}")
    public ActionResponse updateUserUnfavor(@HeaderParam("API_KEY") String userKey, @PathParam("type") String type,
        @PathParam("productId") Integer productId) {
        logger.debug(">updateUserUnfavor() ");
        UserFavorAction action = getBean(UserFavorAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.updateUserUnfavor(type, productId);
        logger.debug(">updateUserUnfavor()");
        return action.getActionResponse();
    }

    /**
     * 订单列表
     * 
     * @param type
     * @param status
     * @return
     */
    @GET
    @Path("/{type}/orders")
    public ActionResponse getOrderList(@HeaderParam("API_KEY") String userKey, @PathParam("type") String type,
        @QueryParam("status") Integer status) {
        logger.debug(">getOrderList() ");
        OrderAction action = getBean(OrderAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.getOrderList(type, status);
        logger.debug(">getOrderList()");
        return action.getActionResponse();
    }

    /**
     * 订单创建
     * 
     * @param type
     * @param req
     * @return
     */
    @POST
    @Path("/{type}/order")
    public ActionResponse insertOrder(@HeaderParam("API_KEY") String userKey, @PathParam("type") String type,
        OrderAction.Request req) {
        logger.debug(">insertOrder() ");
        OrderAction action = getBean(OrderAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.insertOrder(type, req);
        logger.debug(">insertOrder()");
        return action.getActionResponse();
    }

    /**
     * 修改订单状态
     *
     * @param type
     * @param orderId
     * @param status
     * @return
     */
    @PUT
    @Path("/{type}/order/{orderId}/{status}")
    public ActionResponse updateOrderStatus(@HeaderParam("API_KEY") String userKey, @PathParam("type") String type,
        @PathParam("orderId") Integer orderId, @PathParam("status") Integer status) {
        logger.debug(">updateOrderStatus() ");
        OrderAction action = getBean(OrderAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.updateOrderStatus(type, orderId, status);
        logger.debug(">updateOrderStatus()");
        return action.getActionResponse();
    }

    /**
     * 删除订单
     * 
     * @param type
     * @return
     */
    @DELETE
    @Path("/{type}/order/{orderId}")
    public ActionResponse deleteOrder(@HeaderParam("API_KEY") String userKey, @PathParam("type") String type,
        @PathParam("orderId") Integer orderId) {
        logger.debug(">deleteOrder() ");
        OrderAction action = getBean(OrderAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.deleteOrder(type, orderId);
        logger.debug(">deleteOrder()");
        return action.getActionResponse();
    }

    /**
     * 获取商品列表
     * 
     * @param type 商品类型，传flower
     * @param categoryId 分类
     * @param orderBy
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GET
    @Path("/{type}/products")
    public ActionResponse getProductList(@HeaderParam("API_KEY") String userKey, @PathParam("type") String type,
        @QueryParam("categoryId") Integer[] categoryId, @QueryParam("orderBy") String orderBy,
        @QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize) {
        logger.debug(">getProductList() ");
        ProductAction action = getBean(ProductAction.ACTION_NAME);
        if (null != userKey) {
            action.validateEntryUserKey(userKey);
        }
        action.getProductList(type, categoryId, orderBy, pageNo, pageSize);
        logger.debug(">getProductList()");
        return action.getActionResponse();
    }

    /**
     * 获取收藏商品列表
     *
     * @param userKey
     * @param type
     * @return
     */
    @GET
    @Path("/{type}/favor")
    public ActionResponse getUserFavors(@HeaderParam("API_KEY") String userKey, @PathParam("type") String type) {
        logger.debug(">getUserFavors() ");
        UserFavorAction action = getBean(UserFavorAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.getUserFavor(type);
        logger.debug(">getUserFavors()");
        return action.getActionResponse();
    }

}
