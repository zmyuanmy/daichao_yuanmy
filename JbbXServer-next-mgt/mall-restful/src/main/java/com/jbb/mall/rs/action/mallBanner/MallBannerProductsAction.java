package com.jbb.mall.rs.action.mallBanner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import com.jbb.mall.core.dao.domain.FlowerProduct;
import com.jbb.mall.core.dao.domain.MallBannersVo;
import com.jbb.mall.core.dao.domain.MallCategoriesVo;
import com.jbb.mall.core.dao.domain.User;
import com.jbb.mall.core.service.MallBannerService;
import com.jbb.mall.core.service.MallCategoriesService;
import com.jbb.mall.core.service.MallProductsService;
import com.jbb.mall.server.rs.action.BasicAction;
import com.jbb.mall.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;

/**
 * @author ThinkPad
 * @date 2019/01/17
 */

@Service(MallBannerProductsAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MallBannerProductsAction extends BasicAction {

    public static final String ACTION_NAME = "MallBannerProductsAction";

    private static Logger logger = LoggerFactory.getLogger(MallBannerProductsAction.class);

    private Response response;

    @Autowired
    private MallBannerService mallBannerService;
    
    @Autowired
    private MallCategoriesService mallCategoriesService;
    
    @Autowired
    private MallProductsService mallProductsService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getMallBannerHome(String type) {
        logger.debug(">getMallBannerHome()");
        
        if(StringUtil.isEmpty(type)) {
        	throw new WrongParameterValueException("jbb.mall.error.product.notType");      	
        }
        User user = this.user;
        Integer userId;
        if(null == user) {
        	userId = null;
        }else {
        	userId = user.getUserId();
        }
        List <MallBannersVo> mallBannersVoList = Lists.newArrayList();
        List<MallCategoriesVo> mallCategoriesVoList = Lists.newArrayList();
        List<FlowerProduct> lists = Lists.newArrayList();
        
        mallBannersVoList =	mallBannerService.selectMallBannerVoList(type);
        
        mallCategoriesVoList = mallCategoriesService.selectMallCategoriesVoList(type);
        
        lists =  mallProductsService.selectFlowerProductList(type,userId);
        
        response.banner = mallBannersVoList;
        response.categories = mallCategoriesVoList;
        response.lists = lists; 
        logger.debug("<getMallBannerHome()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<MallBannersVo> banner;
        
        private List<MallCategoriesVo> categories;
        
        private List<FlowerProduct> lists;

		public List<MallBannersVo> getBanner() {
			return banner;
		}

		public List<MallCategoriesVo> getCategories() {
			return categories;
		}

		public List<FlowerProduct> getLists() {
			return lists;
		}
        
        

       

    }

}
