package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.EosDigHistory;

public interface EosDigHistoryService {

    // 上报挖矿数据
    void insertEosDigHistory(EosDigHistory eosDigHistory);

    // 获取最近50条挖矿数据
    List<EosDigHistory> getEosDigHistory();

    // 获取token统计数据及获利返奖情况
    EosDigHistory getEosDigHistoryProfit(String tokenName);
    
    //是否已存在挖矿日志表中
    boolean checkEosDigHistory(String miner,String txNo);
}
