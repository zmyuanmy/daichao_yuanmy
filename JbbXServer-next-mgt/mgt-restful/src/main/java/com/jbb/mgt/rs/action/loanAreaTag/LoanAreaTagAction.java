package com.jbb.mgt.rs.action.loanAreaTag;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.LoanTag;
import com.jbb.mgt.core.service.IpAddressService;
import com.jbb.mgt.core.service.LoanPlatformService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.WrongParameterValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wyq
 * @date 2018/8/31 17:46
 */
@Service(LoanAreaTagAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoanAreaTagAction extends BasicAction {
    public static final String ACTION_NAME = "LoanAreaTagAction";

    private static Logger logger = LoggerFactory.getLogger(LoanAreaTagAction.class);

    private Response response;

    @Autowired
    private LoanPlatformService service;

    @Autowired
    private IpAddressService ipAddressService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getLoanTags(String appName, String os) {
        logger.debug(">getLoanTags");
        List<LoanTag> loanTags = service.getLoanTags(false);
        // 合规产品处理 - 硬编码
        if ((null == appName || "jbb".equals(appName))
            && ipAddressService.isIpShowValidProduct(this.getRemoteAddress())) {
            List<LoanTag> newLoanTags = new ArrayList<LoanTag>();
            for (LoanTag loanTag : loanTags) {
                // 合规产品的areaId
                int areaId = loanTag.getAreaId();
                if (areaId >= 800 && areaId < 900) {
                    loanTag.setAreaId(areaId - 800);
                    newLoanTags.add(loanTag);
                }
            }
            this.response.areaTags = newLoanTags;
            return;
        }
        // 合规产品处理 - 硬编码 END
        this.response.areaTags = loanTags;
        logger.debug("<getLoanTags");
    }

    public void getLoanTagsForAdmin() {
        logger.debug(">getLoanTags");
        List<LoanTag> loanTags = service.getLoanTags(null);
        this.response.areaTags = loanTags;
        logger.debug("<getLoanTags");
    }

    public void updateLoanTags(Integer areaTagId, Request req) {
        logger.debug(">updateLoanTags,LoanTag=" + req);
        if (null == req) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "areaId");
        } else if (null == req.areaId && null == req.tagName && null == req.pos && null == req.logo) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "areaId");
        }
        LoanTag tag = service.getLoanTagByAreaTagId(areaTagId);
        if (null == tag) {
            throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "areaTagId");
        }
        tag = generageLoanAreaTag(tag, req);
        service.updateLoanTag(tag);
        logger.debug("<updateLoanTags");
    }

    public void saveLoanTags(Request req) {
        logger.debug(">saveLoanTags,LoanTag=" + req);
        if (null == req) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "areaId");
        } else if (null == req.areaId && null == req.tagName && null == req.pos && null == req.logo) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "areaId");
        }
        LoanTag tag = new LoanTag();
        tag = generageLoanAreaTag(tag, req);
        service.saveLoanTag(tag);
        this.response.areaTagId = tag.getAreaTagId();
        logger.debug("<saveLoanTags");
    }

    public void updateLoanTagsStatus(Integer areaTagId, boolean freeze) {
        if (null == areaTagId || areaTagId <= 0) {
            throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "areaTagId");
        } else {
            service.updateLoanTagsStatus(areaTagId, freeze);
        }

    }

    public void deleteLoanTags(Integer aTagId) {
        logger.debug(">deleteLoanTags,areaTagId=" + aTagId);
        if (null != aTagId && aTagId > 0) {
            service.deleteLoanTag(aTagId);
        } else if (null == aTagId) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "areaTagId");
        } else {
            throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "areaTagId");
        }
        logger.debug("<deleteLoanTags");
    }

    public void getLoanTagByLoanAreaTagId(Integer LoanAreaTagId) {
        logger.debug(">getLoanTagByLoanAreaTagId,LoanAreaTagId=" + LoanAreaTagId);
        if (null == LoanAreaTagId || LoanAreaTagId < 0) {
            throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "LoanAreaTagId");
        }
        LoanTag loan = service.getLoanTagByAreaTagId(LoanAreaTagId);
        this.response.areaTag = loan;
        logger.debug("<getLoanTagByLoanAreaTagId");
    }

    private LoanTag generageLoanAreaTag(LoanTag tag, Request req) {
        if (null != req.tagName) {
            tag.setTagName(req.tagName);
        }
        if (null != req.areaId) {
            tag.setAreaId(req.areaId);
        }
        if (null != req.pos) {
            tag.setPos(req.pos);
        }
        if (null != req.logo) {
            tag.setLogoUrl(req.logo);
        }
        return tag;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Integer areaTagId;
        public String tagName;
        public Integer areaId;
        public Integer pos;
        public String logo;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public List<LoanTag> areaTags;
        public Integer areaTagId;
        public LoanTag areaTag;
    }
}
