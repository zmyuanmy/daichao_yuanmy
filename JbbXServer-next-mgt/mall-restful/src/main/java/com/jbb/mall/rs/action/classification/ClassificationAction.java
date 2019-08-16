package com.jbb.mall.rs.action.classification;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mall.core.dao.domain.Classification;
import com.jbb.mall.core.service.CategorieService;
import com.jbb.mall.server.rs.action.BasicAction;
import com.jbb.mall.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;

/**
 * @author wyq
 * @date 2019/1/21 10:03
 */

@Service(ClassificationAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ClassificationAction extends BasicAction {

    public static final String ACTION_NAME = "ClassificationAction";

    private static Logger logger = LoggerFactory.getLogger(ClassificationAction.class);

    private Response response;

    @Autowired
    private CategorieService service;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getClassificationsByclassification(String type, String[] classification, Boolean getDetail) {
        logger.debug(">getMallBanner()");
        classification = classification.length == 0 ? null : classification;
        getDetail = null == getDetail ? false : getDetail;
        List<Classification> list = service.getClassificationsByclassification(type, classification, getDetail);
        this.response.classifications = list;
        logger.debug("<getMallBanner()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public List<Classification> classifications;
    }
}
