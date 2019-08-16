package com.jbb.mgt.rs.action.eos;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.EosDigHistory;
import com.jbb.mgt.core.domain.EosTokenRefReward;
import com.jbb.mgt.core.service.EosDigHistoryService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.util.StringUtil;

@Service(EosDigHistoryAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EosDigHistoryAction extends BasicAction {
    public static final String ACTION_NAME = "EosDigHistoryAction";

    private static Logger logger = LoggerFactory.getLogger(EosDigHistoryAction.class);

    private Response response;

    @Autowired
    private EosDigHistoryService eosDigHistoryService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void insertEosDigHistory(Request req) {
        logger.debug(">insertEosDigHistory() request=" + req);
        boolean result = validateRequest(req);
        if (!result) {
            return;
        }
        if (eosDigHistoryService.checkEosDigHistory(req.miner, req.txNo)) {
            logger.error("EosDigHistory exist");
            return;
        }
        EosDigHistory digHistory = generateEosDigHistory(req);
        eosDigHistoryService.insertEosDigHistory(digHistory);
        logger.debug("<insertEosDigHistory()");
    }

    public void getEosDigHistory() {
        logger.debug(">getEosDigHistory()");
        this.response.eosDigHistories = eosDigHistoryService.getEosDigHistory();
        logger.debug(">getEosDigHistory()");
    }

    public void getEosDigHistoryProfit(String tokenName) {
        logger.debug(">getEosDigHistoryProfit() tokenName=" + tokenName);
        EosDigHistory eosDigHistory = eosDigHistoryService.getEosDigHistoryProfit(tokenName);
        EosTokenRefReward refReward = eosDigHistory.getEosTokenRefReward();
        int total = eosDigHistory.getTotal();
        this.response.token = eosDigHistory.getToken();
        this.response.refRewards = total * refReward.getRefPercent() / 100;
        this.response.digTokenQuantity = total * (refReward.getTokenPercent() + refReward.getRefTokenPercent()) / 100;
        this.response.quantity = eosDigHistory.getQuantity();
        this.response.winQuantity = eosDigHistory.getWinnerQuantity();
        logger.debug(">getEosDigHistoryProfit()");
    }

    private EosDigHistory generateEosDigHistory(Request req) {
        EosDigHistory digHistory = new EosDigHistory();
        digHistory.setMiner(req.miner);
        digHistory.setTxNo(req.txNo);
        digHistory.setToken(req.token);
        digHistory.setQuantity(req.quantity);
        digHistory.setWinner(req.winner);
        digHistory.setWinnerQuantity(req.winnerQuantity);
        digHistory.setTxData(req.txData);
        return digHistory;
    }

    private boolean validateRequest(Request req) {
        if (null == req) {
            logger.error("insertEosDigHistory req==null");
            return false;
        }
        if (StringUtil.isEmpty(req.miner)) {
            logger.error("insertEosDigHistory param 'miner' error");
            return false;
        }
        if (StringUtil.isEmpty(req.txNo)) {
            logger.error("insertEosDigHistory param 'txNo' error");
            return false;
        }
        return true;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String miner; // 矿工
        public String txNo; // 编号
        public String token; // token
        public Double quantity; // 币数量
        public Integer winner; // -1 输， 0-平， 1-赢
        public Double winnerQuantity; // 赢的数量
        public String txData; // 日志数据

    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private String token;
        private Double refRewards;
        private Double digTokenQuantity;
        private Double quantity;
        private Double winQuantity;
        private List<EosDigHistory> eosDigHistories;

        public String getToken() {
            return token;
        }

        public Double getRefRewards() {
            return refRewards;
        }

        public Double getDigTokenQuantity() {
            return digTokenQuantity;
        }

        public Double getQuantity() {
            return quantity;
        }

        public Double getWinQuantity() {
            return winQuantity;
        }

        public List<EosDigHistory> getEosDigHistories() {
            return eosDigHistories;
        }

    }

}
