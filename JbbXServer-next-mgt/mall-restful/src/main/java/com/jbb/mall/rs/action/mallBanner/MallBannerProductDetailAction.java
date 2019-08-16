package com.jbb.mall.rs.action.mallBanner;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mall.core.dao.domain.ProductDetailVo;
import com.jbb.mall.core.dao.domain.User;
import com.jbb.mall.core.service.MallProductsService;
import com.jbb.mall.server.rs.action.BasicAction;
import com.jbb.mall.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;

/**
 * @author ThinkPad
 * @date 2019/01/17
 */

@Service(MallBannerProductDetailAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MallBannerProductDetailAction extends BasicAction {

    public static final String ACTION_NAME = "MallBannerProductDetailAction";

    private static Logger logger = LoggerFactory.getLogger(MallBannerProductDetailAction.class);

    private Response response;

    @Autowired
    private MallProductsService mallProductsService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getMallProductDetail(String type, Integer productId) {
        logger.debug(">getMallProductDetail()");
        if (StringUtil.isEmpty(type)) {
            throw new WrongParameterValueException("jbb.mall.error.product.notFoundType");
        }
        User user = this.user;
        Integer userId;
        if (null == user) {
            userId = null;
        } else {
            userId = user.getUserId();
        }
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo = mallProductsService.selectProductDetail(type, productId, userId);

        List<String> imgList = mallProductsService.selectProductImgList(productId);
        if (CollectionUtils.isNotEmpty(imgList)) {
            productDetailVo.setProductImg(imgList);
        }
        response.productDetailVo = productDetailVo;
        logger.debug("<getMallProductDetail()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private ProductDetailVo productDetailVo;

        public ProductDetailVo getProductDetailVo() {
            return productDetailVo;
        }

    }

}
