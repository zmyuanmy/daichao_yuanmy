package com.jbb.mgt.core.domain;

/**
 * 流量控制实体类
 * 
 * @author wyq
 * @date 2018/04/26
 */
public class DataFlowSetting {
    private int orgId;
    private int dataFlowId;
    private int minValue;
    private int maxValue;
    private boolean closed;

    private DataFlowBase dataFlowBase;

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getDataFlowId() {
        return dataFlowId;
    }

    public void setDataFlowId(int dataFlowId) {
        this.dataFlowId = dataFlowId;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public DataFlowBase getDataFlowBase() {
        return dataFlowBase;
    }

    public void setDataFlowBase(DataFlowBase dataFlowBase) {
        this.dataFlowBase = dataFlowBase;
    }

    @Override
    public String toString() {
        return "DataFlowSetting [orgId=" + orgId + ", dataFlowId=" + dataFlowId + ", minValue=" + minValue
            + ", maxValue=" + maxValue + ", closed=" + closed + ", dataFlowBase=" + dataFlowBase + "]";
    }

    public DataFlowSetting() {
        super();
    }

    public DataFlowSetting(int orgId, int dataFlowId, int minValue, int maxValue, boolean closed) {
        this.orgId = orgId;
        this.dataFlowId = dataFlowId;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.closed = closed;
    }
}
