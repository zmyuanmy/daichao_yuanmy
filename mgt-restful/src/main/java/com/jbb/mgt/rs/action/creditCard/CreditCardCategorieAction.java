package com.jbb.mgt.rs.action.creditCard;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.CreditCard;
import com.jbb.mgt.core.domain.CreditCardCategorie;
import com.jbb.mgt.core.service.CreditCardCategorieService;
import com.jbb.mgt.rs.action.creditCard.CreditCardAction.Request;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;

/**
 * @author xiaoeach
 * @date 2018/12/27
 */

@Service(CreditCardCategorieAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CreditCardCategorieAction extends BasicAction {
    public static final String ACTION_NAME = "CreditCardCategorieAction";

    private static Logger logger = LoggerFactory.getLogger(CreditCardCategorieAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private CreditCardCategorieService cardCategorieService;

    public void getCreditCardCategorie() {
        logger.debug(">getCreditCardCategorie()");
        this.response.cardCategories = cardCategorieService.getCreditCardCategorie();
        logger.debug("<getCreditCardCategorie()");
    }

    public void getCreditCardCategorieById(Integer categoryId) {
        logger.debug(">getCreditCardCategorieById() categoryId=" + categoryId);
        if (categoryId == null || categoryId <= 0) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "categoryId");
        }
        this.response.cardCategorie = cardCategorieService.getCreditCardCategorieById(categoryId);
        logger.debug("<getCreditCardCategorieById()");
    }

    public void saveCreditCardCategorie(Request req) {
        logger.debug(">saveCreditCardCategorie() req=" + req);
        if (req == null) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "req");
        }
        CreditCardCategorie categorie = null;
        if (req.categoryId != null && req.categoryId > 0) {
            categorie = cardCategorieService.getCreditCardCategorieById(req.categoryId);
        }
        CreditCardCategorie cardCategorie = generageCardCategorie(req, categorie);
        cardCategorieService.saveCreditCardCategorie(cardCategorie);
        logger.debug("<saveCreditCardCategorie()");
    }

    private CreditCardCategorie generageCardCategorie(Request req, CreditCardCategorie cardCategorie) {
        cardCategorie = cardCategorie == null ? new CreditCardCategorie() : cardCategorie;
        cardCategorie.setCategoryId(req.categoryId);
        cardCategorie.setName(req.name = req.name == null ? cardCategorie.getName() : req.name);
        cardCategorie.setIconUrl(req.iconUrl = req.iconUrl == null ? cardCategorie.getIconUrl() : req.iconUrl);
        cardCategorie.setDesc(req.desc = req.desc == null ? cardCategorie.getDesc() : req.desc);
        cardCategorie
            .setDescColor(req.descColor = req.descColor == null ? cardCategorie.getDescColor() : req.descColor);
        cardCategorie.setDetailMessage(
            req.detailMessage = req.detailMessage == null ? cardCategorie.getDetailMessage() : req.detailMessage);
        return cardCategorie;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<CreditCardCategorie> cardCategories;
        private CreditCardCategorie cardCategorie;

        public List<CreditCardCategorie> getCardCategories() {
            return cardCategories;
        }

        public CreditCardCategorie getCardCategorie() {
            return cardCategorie;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Integer categoryId;// 分类ID
        public String name;// 类名
        public String desc;// 简要描述
        public String detailMessage;// 详细描述方案
        public String iconUrl;// 图标地址
        public String descColor;// 简要描述颜色，如#556655
    }
}
