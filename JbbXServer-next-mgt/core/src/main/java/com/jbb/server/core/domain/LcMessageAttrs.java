package com.jbb.server.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LcMessageAttrs {
    private int cmdType;

    private String action;

    private LcMessageExchangeInfo fromClientInfo;

    private LcMessageExchangeInfo toClientInfo;

    public int getCmdType() {
        return cmdType;
    }

    public void setCmdType(int cmdType) {
        this.cmdType = cmdType;
    }

    public LcMessageExchangeInfo getFromClientInfo() {
        return fromClientInfo;
    }

    public void setFromClientInfo(LcMessageExchangeInfo fromClientInfo) {
        this.fromClientInfo = fromClientInfo;
    }

    public LcMessageExchangeInfo getToClientInfo() {
        return toClientInfo;
    }

    public void setToClientInfo(LcMessageExchangeInfo toClientInfo) {
        this.toClientInfo = toClientInfo;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "LcMessageAttrs [cmdType=" + cmdType + ", action=" + action + ", fromClientInfo=" + fromClientInfo
            + ", toClientInfo=" + toClientInfo + "]";
    }

}
