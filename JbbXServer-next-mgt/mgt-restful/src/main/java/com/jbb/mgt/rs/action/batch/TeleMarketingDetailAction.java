package com.jbb.mgt.rs.action.batch;

import java.util.ArrayList;
import java.util.List;

import com.jbb.server.common.exception.MissingParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.TeleMarketing;
import com.jbb.mgt.core.domain.TeleMarketingDetail;
import com.jbb.mgt.core.service.TeleMarketingDetailService;
import com.jbb.mgt.core.service.TeleMarketingService;
import com.jbb.mgt.rs.action.batch.TeleMarketingAction.Response;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.mgt.server.rs.pojo.RsTeleMarketing;
import com.jbb.mgt.server.rs.pojo.RsTeleMarketingDetail;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

/**
 * 批次明细数据Action
 * 
 * @author wyq
 * @date 2018-4-30 10:19:03
 * 
 */
@Service(TeleMarketingDetailAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TeleMarketingDetailAction extends BasicAction {

    public static final String ACTION_NAME = "TeleMarketingDetailAction";

    private static Logger logger = LoggerFactory.getLogger(TeleMarketingDetailAction.class);

    @Autowired
    private TeleMarketingDetailService service;

    @Autowired
    private TeleMarketingService teleService;

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getTeleMarketingDetailS(String batchId) {
        logger.debug(">getTeleMarketingDetailS");
        List<TeleMarketingDetail> list = null;
        TeleMarketing teleMarketing = null;
        if (!StringUtil.isEmpty(batchId)) {
            // teleMarketing = teleService.selectTeleMarkByBatchId(Integer.parseInt(batchId),true);
            list = service.selectTeleMarketingDetails(Integer.valueOf(batchId));
        } else {
            // teleMarketing = teleService.selectMaxTeleMarketings(this.account.getAccountId());
            list = service.selectRecentTeleMarketingDetail(this.account.getAccountId());
        }
        if (list.size() > 0) {
            this.response.mobiles = new ArrayList<RsTeleMarketingDetail>(list.size());
            this.response.validNum = 0;
            this.response.uncheckNum = 0;
            this.response.invalidNum = 0;
            for (TeleMarketingDetail detail : list) {
                RsTeleMarketingDetail rsTeleMarketingDetail = new RsTeleMarketingDetail(detail);
                this.response.mobiles.add(rsTeleMarketingDetail);
                if (detail.getStatus() == 1) {
                    this.response.validNum += 1;
                } else if (detail.getStatus() == 5) {
                    this.response.uncheckNum += 1;
                } else {
                    this.response.invalidNum += 1;
                }
            }
            teleMarketing = teleService.selectTeleMarkByBatchId(list.get(0).getBatchId(), false);
            this.response.batchStatus = teleMarketing.getStatus();
            this.response.batchId = list.get(0).getBatchId();
        }
        this.response.total = list.size();
        logger.debug("<getTeleMarketingDetailS");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private Integer batchId;
        private Integer total;
        private Integer validNum;
        private Integer invalidNum;
        private Integer uncheckNum;
        private Integer batchStatus;
        private List<RsTeleMarketingDetail> mobiles;

        public Integer getBatchId() {
            return batchId;
        }

        public Integer getTotal() {
            return total;
        }

        public Integer getValidNum() {
            return validNum;
        }

        public Integer getInvalidNum() {
            return invalidNum;
        }

        public Integer getUncheckNum() {
            return uncheckNum;
        }

        public Integer getBatchStatus() {
            return batchStatus;
        }

        public List<RsTeleMarketingDetail> getMobiles() {
            return mobiles;
        }
    }

}
