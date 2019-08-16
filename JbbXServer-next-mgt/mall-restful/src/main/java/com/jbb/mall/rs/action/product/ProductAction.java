package com.jbb.mall.rs.action.product;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jbb.mall.core.dao.domain.Product;
import com.jbb.mall.core.dao.domain.User;
import com.jbb.mall.core.service.ProductService;
import com.jbb.mall.server.rs.action.BasicAction;
import com.jbb.mall.server.rs.pojo.ActionResponse;
import com.jbb.server.common.util.StringUtil;

/**
 * @author ThinkPad
 * @date 2019/01/18
 */
@Service(ProductAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductAction extends BasicAction {

    public static final String ACTION_NAME = "ProductAction";

    private static Logger logger = LoggerFactory.getLogger(ProductAction.class);

    private Response response;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private ProductService productService;

    public void getProductList(String type, Integer[] categoryId, String orderBy, int pageNo, int pageSize) {
        logger.debug(">getProductList() type=" + type + ",categoryId=" + categoryId + ",orderBy=" + orderBy + ",pageNo="
            + pageNo + ",pageSize=" + pageSize);
        pageNo = pageNo == 0 ? 1 : pageNo;
        pageSize = pageSize == 0 ? 10 : pageSize;
        orderBy = getOrderBy(orderBy);
        PageHelper.startPage(pageNo, pageSize, orderBy);
        User user = this.user == null ? new User() : this.user;
        List<Product> list = productService.getProductList(type, categoryId, user.getUserId());
        PageInfo<Product> pageInfo = new PageInfo<Product>(list);
        this.response.pageInfo = pageInfo;
        PageHelper.clearPage();
        logger.debug("<getProductList()");

    }

    private String getOrderBy(String orderBy) {
        if (StringUtil.isEmpty(orderBy)) {
            orderBy = " mp.creation_date ASC";
        } else {
            if (orderBy.equals("saleCount")) {
                orderBy = " mpsc.sale_count DESC";
            }
            if (orderBy.equals("price")) {
                orderBy = " filterPrice ASC";
            } else if (orderBy.equals("priceDesc")) {
                orderBy = " filterPrice DESC";
            }
        }
        return orderBy;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private PageInfo<Product> pageInfo;

        public PageInfo<Product> getPageInfo() {
            return pageInfo;
        }

    }

}
