package com.jbb.mgt.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.EosDigHistory;

public interface EosDigHistoryMapper {

    // 上报挖矿数据
    void insertEosDigHistory(EosDigHistory eosDigHistory);

    // 获取最近50条挖矿数据
    List<EosDigHistory> selectEosDigHistory();

    // 获取token统计数据及获利返奖情况
    EosDigHistory selectEosDigHistoryProfit(@Param("tokenName") String tokenName);

    // 是否已存在挖矿日志表中
    int checkEosDigHistory(@Param("miner") String miner, @Param("txNo") String txNo);
}
