package com.jbb.mgt.core.domain;

public class UserFaceIdBizNo {
    private int userId;
    private String randomNumber;// 四位数随机码
    private String bizNo;// 业务流水号，三次请求为同一个流水号
    private String tokenRandomNumber; // Raw-ValidateVideo接口的参数
    private String tokenVideo;// Raw-Verify 接口的参数
    private String randomData; // 获取随机数得到的json
    private String validateVideoData; // 验证视频时得到的json
    private String verifyData;// 最后校验的时候得到的json

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(String randomNumber) {
        this.randomNumber = randomNumber;
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

    public String getTokenVideo() {
        return tokenVideo;
    }

    public void setTokenVideo(String tokenVideo) {
        this.tokenVideo = tokenVideo;
    }

    public String getRandomData() {
        return randomData;
    }

    public void setRandomData(String randomData) {
        this.randomData = randomData;
    }

    public String getValidateVideoData() {
        return validateVideoData;
    }

    public void setValidateVideoData(String validateVideoData) {
        this.validateVideoData = validateVideoData;
    }

    public String getVerifyData() {
        return verifyData;
    }

    public void setVerifyData(String verifyData) {
        this.verifyData = verifyData;
    }

    @Override
    public String toString() {
        return "UserFaceIdBizNo [userId=" + userId + ", randomNumber=" + randomNumber + ", bizNo=" + bizNo
            + ", tokenRandomNumber=" + tokenRandomNumber + ", tokenVideo=" + tokenVideo + ", randomData=" + randomData
            + ", validateVideoData=" + validateVideoData + ", verifyData=" + verifyData + "]";
    }

}
