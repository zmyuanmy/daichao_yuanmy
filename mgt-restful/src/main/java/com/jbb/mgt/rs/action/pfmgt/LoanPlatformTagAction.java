package com.jbb.mgt.rs.action.pfmgt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.domain.LoanPlatformTag;
import com.jbb.mgt.core.domain.LoanTag;
import com.jbb.mgt.core.domain.Platform;
import com.jbb.mgt.core.service.LoanPlatformService;
import com.jbb.mgt.core.service.LoanPlatformTagService;
import com.jbb.mgt.rs.action.account.AccountAction.Response;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;

@Service(LoanPlatformTagAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoanPlatformTagAction extends BasicAction {

    public static final String ACTION_NAME = "LoanPlatformTagAction";

    private static Logger logger = LoggerFactory.getLogger(LoanPlatformTagAction.class);

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    private Response response;

    @Autowired
    private LoanPlatformTagService loanPlatformTagService;

    @Autowired
    private LoanPlatformService loanPlatformService;

    public void insertLoanPlatformTag(int platformId, int areaTagId, int pos) {
        logger.debug(">insertLoanPlatformTag(),platformId=" + platformId + ",areaTagId=" + areaTagId + ",pos=" + pos);
        verifyParam(platformId, areaTagId, pos);
        Platform platform = loanPlatformService.getPlatformById(platformId);
        if (platform == null) {
            throw new WrongParameterValueException("jbb.pfmgt.error.platform.empty");
        }
        LoanTag loanTag = loanPlatformService.getLoanTagByAreaTagId(areaTagId);
        if (loanTag == null) {
            throw new WrongParameterValueException("jbb.pfmgt.error.areaTag.empty");
        }
        LoanPlatformTag tag = new LoanPlatformTag(platformId, areaTagId, pos);
        LoanPlatformTag platformTag = loanPlatformTagService.getLoanPlatformTagById(platformId, areaTagId);
        if (platformTag != null) {
            loanPlatformTagService.updateLoanPlatformTag(tag);
        } else {
            loanPlatformTagService.insertLoanPlatformTag(tag);
        }
        logger.debug("<insertLoanPlatformTag()");
    }

    public void deleteLoanPlatformTag(int platformId, int areaTagId, int pos) {
        logger.debug(">deleteLoanPlatformTag(),platformId=" + platformId + ",areaTagId=" + areaTagId + ",pos=" + pos);
        verifyParam(platformId, areaTagId, pos);
        loanPlatformTagService.deleteLoanPlatformTag(platformId, areaTagId, pos);
        logger.debug(">deleteLoanPlatformTag()");
    }

    private void verifyParam(int platformId, int areaTagId, int pos) {
        if (platformId <= 0) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "platformId");
        }
        if (areaTagId <= 0) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "areaTagId");
        }
        if (pos <= 0) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "pos");
        }
    }

}
