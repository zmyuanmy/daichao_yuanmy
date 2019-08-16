package com.jbb.mgt.core.domain;

public class FaceValidateVideoResult {
    private String requestId;
    private int timeUsed;
    private String bizNo;
    private String tokenVideo;// 返回的token仅供Raw-Verify API使用
    private String imageBest;
    private String errorMessage;// 错误时才返回
    private String validateVideoData;

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

    public String getTokenVideo() {
        return tokenVideo;
    }

    public void setTokenVideo(String tokenVideo) {
        this.tokenVideo = tokenVideo;
    }

    public String getImageBest() {
        return imageBest;
    }

    public void setImageBest(String imageBest) {
        this.imageBest = imageBest;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getValidateVideoData() {
        return validateVideoData;
    }

    public void setValidateVideoData(String validateVideoData) {
        this.validateVideoData = validateVideoData;
    }

    @Override
    public String toString() {
        return "FaceValidateVideoResult [requestId=" + requestId + ", timeUsed=" + timeUsed + ", bizNo=" + bizNo
            + ", tokenVideo=" + tokenVideo + ", imageBest=" + imageBest + ", errorMessage=" + errorMessage
            + ", validateVideoData=" + validateVideoData + "]";
    }

}
