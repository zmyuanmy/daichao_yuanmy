package com.jbb.mall.rs.action.userFavor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mall.core.dao.domain.Product;
import com.jbb.mall.server.rs.pojo.ActionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jbb.mall.core.dao.domain.UserFavor;
import com.jbb.mall.core.service.MallProductsService;
import com.jbb.mall.core.service.UserFavorService;
import com.jbb.mall.rs.action.mallBanner.MallBannerAction.Response;
import com.jbb.mall.server.rs.action.BasicAction;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;

import java.util.List;

/**
 * @author ThinkPad
 * @date 2019/01/21
 */

@Service(UserFavorAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserFavorAction extends BasicAction {

    public static final String ACTION_NAME = "UserFavorAction";

    private static Logger logger = LoggerFactory.getLogger(UserFavorAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private UserFavorService userFavorService;

    @Autowired
    private MallProductsService mallProductsService;

    public void updateUserFavor(String type, Integer productId) {
        logger.debug(">updateUserFavor() type=" + type + " productId=" + productId);
        int userId = this.user.getUserId();
        if (!mallProductsService.checkProduct(type, productId)) {
            throw new WrongParameterValueException("jbb.mall.error.product.notFound");
        }
        if (userFavorService.checkUserFavor(userId, productId)) {
            throw new WrongParameterValueException("jbb.mall.exception.userFavor.exist");
        }
        UserFavor userFavor = new UserFavor();
        userFavor.setUserId(userId);
        userFavor.setProductId(productId);
        userFavor.setCreationDate(DateUtil.getCurrentTimeStamp());
        userFavor.setStatus(1);
        userFavorService.saveUserFavor(userFavor);
        logger.debug("<updateUserFavor()");
    }

    public void updateUserUnfavor(String type, Integer productId) {
        logger.debug(">updateUserUnfavor() type=" + type + " productId=" + productId);
        int userId = this.user.getUserId();
        if (!mallProductsService.checkProduct(type, productId)) {
            throw new WrongParameterValueException("jbb.mall.error.product.notFound");
        }
        if (!userFavorService.checkUserFavor(userId, productId)) {
            throw new WrongParameterValueException("jbb.mall.exception.userFavor.notExist");
        }
        userFavorService.updateUserFavor(userId, productId);
        logger.debug("<updateUserUnfavor()");
    }

    public void getUserFavor(String type) {
        logger.debug(">getUserFavor()");
        List<Product> list = userFavorService.getUserFavor(this.user.getUserId(), type);
        if (list.size() > 0) {
            this.response.products = list;
        }
        logger.debug("<getUserFavor()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public List<Product> products;
    }

}
