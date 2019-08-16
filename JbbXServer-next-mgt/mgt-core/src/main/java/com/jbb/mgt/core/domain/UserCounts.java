package com.jbb.mgt.core.domain;

public class UserCounts {
    private String statisDate;
    private int entryCnt;
    private int regCnt;
    private int idCnt;
    private int noidCnt;

    public String getStatisDate() {
        return statisDate;
    }

    public void setStatisDate(String statisDate) {
        this.statisDate = statisDate;
    }

    public int getEntryCnt() {
        return entryCnt;
    }

    public void setEntryCnt(int entryCnt) {
        this.entryCnt = entryCnt;
    }

    public int getRegCnt() {
        return regCnt;
    }

    public void setRegCnt(int regCnt) {
        this.regCnt = regCnt;
    }

    public int getIdCnt() {
        return idCnt;
    }

    public void setIdCnt(int idCnt) {
        this.idCnt = idCnt;
    }

    public int getNoidCnt() {
        return noidCnt;
    }

    public void setNoidCnt(int noidCnt) {
        this.noidCnt = noidCnt;
    }

    @Override
    public String toString() {
        return "UserCounts [statisDate=" + statisDate + ", entryCnt=" + entryCnt + ", regCnt=" + regCnt + ", idCnt="
            + idCnt + ", noidCnt=" + noidCnt + "]";
    }

}
