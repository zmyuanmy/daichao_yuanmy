package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class EosDigHistory {
    private String miner; // 矿工
    private String txNo; // 编号
    private String token; // token
    private Double quantity; // 币数量
    private Integer winner; // -1 输， 0-平， 1-赢
    private Double winnerQuantity; // 赢的数量
    private String txData; // 日志数据
    private Timestamp creationDate; // 创建时间
    private Integer total;// 总下注数
    private EosTokenRefReward eosTokenRefReward;

    public EosDigHistory() {}

    public EosDigHistory(String miner, String txNo, String token, Double quantity, Integer winner,
        Double winnerQuantity, String txData) {
        super();
        this.miner = miner;
        this.txNo = txNo;
        this.token = token;
        this.quantity = quantity;
        this.winner = winner;
        this.winnerQuantity = winnerQuantity;
        this.txData = txData;
    }

    public EosTokenRefReward getEosTokenRefReward() {
        return eosTokenRefReward;
    }

    public void setEosTokenRefReward(EosTokenRefReward eosTokenRefReward) {
        this.eosTokenRefReward = eosTokenRefReward;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getMiner() {
        return miner;
    }

    public void setMiner(String miner) {
        this.miner = miner;
    }

    public String getTxNo() {
        return txNo;
    }

    public void setTxNo(String txNo) {
        this.txNo = txNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public Double getWinnerQuantity() {
        return winnerQuantity;
    }

    public void setWinnerQuantity(Double winnerQuantity) {
        this.winnerQuantity = winnerQuantity;
    }

    public String getTxData() {
        return txData;
    }

    public void setTxData(String txData) {
        this.txData = txData;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "EosDigHistory [miner=" + miner + ", txNo=" + txNo + ", token=" + token + ", quantity=" + quantity
            + ", winner=" + winner + ", winnerQuantity=" + winnerQuantity + ", txData=" + txData + ", creationDate="
            + creationDate + "]";
    }

}
