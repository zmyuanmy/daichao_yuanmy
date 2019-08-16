package com.jbb.server.core.domain;

public class FaceRandomResult {

    private String requestId;
    private int timeUsed;
    private String bizNo;
    private String tokenRandomNumber;// 用于作为Raw-ValidateVideo接口的参数
    private String randomNumber;// 活体验证时随机码
    private String errorMessage;// 只有错误时才返回
    private String randomData;// 完整的json

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getTokenRandomNumber() {
        return tokenRandomNumber;
    }

    public void setTokenRandomNumber(String tokenRandomNumber) {
        this.tokenRandomNumber = tokenRandomNumber;
    }

    public String getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(String randomNumber) {
        this.randomNumber = randomNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getRandomData() {
        return randomData;
    }

    public void setRandomData(String randomData) {
        this.randomData = randomData;
    }

    @Override
    public String toString() {
        return "FaceRandomResult [requestId=" + requestId + ", timeUsed=" + timeUsed + ", bizNo=" + bizNo
            + ", tokenRandomNumber=" + tokenRandomNumber + ", randomNumber=" + randomNumber + ", errorMessage="
            + errorMessage + ", randomData=" + randomData + "]";
    }

}
