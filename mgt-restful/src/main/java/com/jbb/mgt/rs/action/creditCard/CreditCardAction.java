package com.jbb.mgt.rs.action.creditCard;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jbb.mgt.core.domain.City;
import com.jbb.mgt.core.domain.CreditCard;
import com.jbb.mgt.core.service.CreditCardService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

/**
 * @author ThinkPad
 * @date 2018/12/28
 */

@Service(CreditCardAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CreditCardAction extends BasicAction {
    public static final String ACTION_NAME = "CreditCardAction";

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    private static Logger logger = LoggerFactory.getLogger(CreditCardAction.class);

    private Response response;

    @Autowired
    private PlatformTransactionManager txManager;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    private void rollbackTransaction(TransactionStatus txStatus) {
        if (txStatus == null) {
            return;
        }

        try {
            txManager.rollback(txStatus);
        } catch (Exception er) {
            logger.warn("Cannot rollback transaction", er);
        }
    }

    @Autowired
    private CreditCardService creditCardService;

    public void getCreditCardByCategoryId(String cityName, Integer categoryId) {
        logger.debug(">getCreditCardByCategoryId() cityName=" + cityName + " categoryId=" + categoryId);
        if (categoryId == null || categoryId <= 0) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "categoryId");
        }
        this.response.creditCards = creditCardService.getCreditCardByCategoryId(cityName, categoryId);
        logger.debug("<getCreditCardByCategoryId()");
    }

    public void getCreditCardByCity(int pageNo, int pageSize, String cityName, Integer categoryId, Boolean isDelete,
        String startDate, String endDate) {
        logger.debug(">getCreditCardByCity() cityName=" + cityName + " categoryId=" + categoryId);
        pageSize = pageSize == 0 ? 10 : pageSize;
        PageHelper.startPage(pageNo, pageSize);

        Timestamp tsStartDate = StringUtil.isEmpty(startDate) ? null : Util.parseTimestamp(startDate, getTimezone());
        Timestamp tsEndDate = StringUtil.isEmpty(endDate) ? null : Util.parseTimestamp(endDate, getTimezone());
        List<CreditCard> list
            = creditCardService.getCreditCardByCity(cityName, categoryId, isDelete, tsStartDate, tsEndDate);
        PageInfo<CreditCard> pageInfo = new PageInfo<CreditCard>(list);

        this.response.pageInfo = pageInfo;
        PageHelper.clearPage();
        logger.debug("<getCreditCardByCity()");
    }

    public void getCreditCardById(Integer creditId) {
        logger.debug(">getCreditCardById() creditId=" + creditId);
        if (creditId == null || creditId <= 0) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "creditId");
        }
        this.response.creditCard = creditCardService.getCreditCardById(creditId);
        logger.debug("<getCreditCardById()");
    }

    public void insertCreditCard(Request req) {
        logger.debug(">insertCreditCard() req=" + req);
        if (req == null) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "req");
        }
        CreditCard creditCard = generageCreditCard(req, null);

        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);

            creditCardService.insertCreditCard(creditCard);
            req.creditId = creditCard.getCreditId();
            insertRelation(req, 1);

            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            rollbackTransaction(txStatus);
        }
        logger.debug("<insertCreditCard()");
    }

    private void insertRelation(Request req, int type) {
        int creditId = req.creditId;
        if (type == 2) {
            creditCardService.deleteCreditCategorieCard(creditId);
            /* creditCardService.deleteCreditCardArea(creditId);*/
        }
        /* if (req.citys != null && req.citys.length > 0) {
            for (int i = 0; i < req.citys.length; i++) {
                creditCardService.insertCreditCardArea(req.citys[i], creditId);
            }
        }*/
        if (req.categories != null && req.categories.length > 0) {
            for (int i = 0; i < req.categories.length; i++) {
                creditCardService.insertCreditCategorieCard(req.categories[i], creditId);
            }
        }
    }

    private CreditCard generageCreditCard(Request req, CreditCard creditCard) {
        creditCard = creditCard == null ? new CreditCard() : creditCard;
        creditCard.setCreditId(req.creditId);
        creditCard.setBankCode(req.bankCode = req.bankCode == null ? creditCard.getBankCode() : req.bankCode);
        creditCard.setBankName(req.bankName == null ? creditCard.getBankName() : req.bankName);
        creditCard.setBankUrl(req.bankUrl == null ? creditCard.getBankUrl() : req.bankUrl);
        creditCard.setCardImgUrl(req.cardImgUrl == null ? creditCard.getCardImgUrl() : req.cardImgUrl);
        creditCard.setTagName(req.tagName == null ? creditCard.getTagName() : req.tagName);
        creditCard.setTagColor(req.tagColor == null ? creditCard.getTagColor() : req.tagColor);
        creditCard
            .setCreditShortName(req.creditShortName == null ? creditCard.getCreditShortName() : req.creditShortName);
        creditCard.setCreditName(req.creditName == null ? creditCard.getCreditName() : req.creditName);
        creditCard.setCreditDesc(req.creditDesc == null ? creditCard.getCreditDesc() : req.creditDesc);
        creditCard.setGiftLogo(req.giftLogo == null ? creditCard.getGiftLogo() : req.giftLogo);
        creditCard.setGiftDesc(req.giftDesc == null ? creditCard.getGiftDesc() : req.giftDesc);
        creditCard.setDetailedDesc(req.detailedDesc == null ? creditCard.getDetailedDesc() : req.detailedDesc);
        creditCard.setWeight(req.weight == null ? creditCard.getWeight() : req.weight);
        creditCard.setIsDeleted(req.isDeleted == null ? creditCard.getIsDeleted() : req.isDeleted);
        return creditCard;
    }

    public void updateCreditCard(Request req) {
        logger.debug(">updateCreditCard() req=" + req);
        if (req == null || req.creditId <= 0) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "req");
        }
        CreditCard card = creditCardService.getCreditCardById(req.creditId);
        if (card == null) {
            throw new WrongParameterValueException("jbb.bnhmgt.error.creditcard.notFound");
        }
        CreditCard creditCard = generageCreditCard(req, card);

        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);

            creditCardService.updateCreditCard(creditCard);
            insertRelation(req, 2);

            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            rollbackTransaction(txStatus);
        }
        logger.debug("<updateCreditCard()");
    }

    public void selectCity() {
        logger.debug(">selectCity()");
        this.response.cityList = creditCardService.selectCity();
        logger.debug("<selectCity()");
    }

    public void deleteCreditCard(Integer creditId) {
        logger.debug(">deleteCreditCard()");
        CreditCard card = creditCardService.getCreditCardById(creditId);
        if (card == null) {
            throw new WrongParameterValueException("jbb.bnhmgt.error.creditcard.notFound");
        }
        creditCardService.deleteCreditCard(creditId);
        logger.debug("<deleteCreditCard()");

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<CreditCard> creditCards;
        private PageInfo<CreditCard> pageInfo;
        private CreditCard creditCard;
        private List<City> cityList;

        public List<CreditCard> getCreditCards() {
            return creditCards;
        }

        public PageInfo<CreditCard> getPageInfo() {
            return pageInfo;
        }

        public CreditCard getCreditCard() {
            return creditCard;
        }

        public List<City> getCityList() {
            return cityList;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public int creditId;// 自增 pk
        public String bankCode;// 银行编码缩写
        public String bankName;// 银行名称
        public String bankUrl;// 银行信用卡资料填写地址
        public String cardImgUrl;// 卡片图标地址
        public String tagName;// 标签
        public String tagColor;// 颜色
        public String creditShortName;// 卡片短名字
        public String creditName;// 卡片完整名字
        public String creditDesc;// 简要描述
        public String giftLogo;// 礼物logo地址
        public String giftDesc;// 礼物说明
        public String detailedDesc;// 详细说明
        public Integer weight;// 权重 -- 用于卡片筛选时排序，运营子系统可调节
        public Boolean isDeleted;// 是否删除
        public int[] categories;// 分类
        /* public int[] citys;// 地区
        */
    }
}
