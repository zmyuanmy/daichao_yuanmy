package com.jbb.mgt.server.rs.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.DataFlowSetting;

/**
 * 流量控制的response对象
 * 
 * @author wyq
 * @date 2018/04/27
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RsDataFlowSetting {
    private int orgId;
    private int dataFlowId;
    private int minValue;
    private int maxValue;
    private boolean closed;

    public RsDataFlowSetting(DataFlowSetting dfc) {
        this.orgId = dfc.getOrgId();
        this.dataFlowId = dfc.getDataFlowId();
        this.minValue = dfc.getMinValue();
        this.maxValue = dfc.getMaxValue();
        this.closed = dfc.isClosed();
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getDataFlowId() {
        return dataFlowId;
    }

    public void setDataFlowId(int dataFlowId) {
        this.dataFlowId = dataFlowId;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public boolean isClosed() {
        return closed;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public RsDataFlowSetting(int orgId, int dataFlowId, int minValue, int maxValue) {
        super();
        this.orgId = orgId;
        this.dataFlowId = dataFlowId;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public RsDataFlowSetting() {
        super();
    }

    @Override
    public String toString() {
        return "RsDataFlowSetting{" + "orgId=" + orgId + ", dataFlowId=" + dataFlowId + ", minValue=" + minValue
            + ", maxValue=" + maxValue + ", closed=" + closed + '}';
    }
}
