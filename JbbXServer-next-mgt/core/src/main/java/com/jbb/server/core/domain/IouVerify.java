package com.jbb.server.core.domain;

/**
 * Created by inspironsun on 2018/6/29
 */
public class IouVerify {

    private boolean infoCheck;
    private boolean realName1;
    private boolean realName2;
    private boolean mobile;
    private boolean video;
    private boolean hasContacts;
    private Boolean hasTradePassword;
    private Integer authCount;
    private Integer authFee;
    private String authDetail;
    private String authBody;
    private Integer iouCount;
    private Integer iouFee;
    private String iouDetail;
    private String iouBody;
    private String errorMsg;
    private String userName;

    public boolean isRealName1() {
        return realName1;
    }

    public void setRealName1(boolean realName1) {
        this.realName1 = realName1;
    }

    public boolean isRealName2() {
        return realName2;
    }

    public void setRealName2(boolean realName2) {
        this.realName2 = realName2;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public Boolean getHasTradePassword() {
        return hasTradePassword;
    }

    public void setHasTradePassword(Boolean hasTradePassword) {
        this.hasTradePassword = hasTradePassword;
    }

    public Integer getAuthCount() {
        return authCount;
    }

    public void setAuthCount(Integer authCount) {
        this.authCount = authCount;
    }

    public Integer getAuthFee() {
        return authFee;
    }

    public void setAuthFee(Integer authFee) {
        this.authFee = authFee;
    }

    public String getAuthDetail() {
        return authDetail;
    }

    public void setAuthDetail(String authDetail) {
        this.authDetail = authDetail;
    }

    public String getAuthBody() {
        return authBody;
    }

    public void setAuthBody(String authBody) {
        this.authBody = authBody;
    }

    public Integer getIouCount() {
        return iouCount;
    }

    public void setIouCount(Integer iouCount) {
        this.iouCount = iouCount;
    }

    public Integer getIouFee() {
        return iouFee;
    }

    public void setIouFee(Integer iouFee) {
        this.iouFee = iouFee;
    }

    public String getIouDetail() {
        return iouDetail;
    }

    public void setIouDetail(String iouDetail) {
        this.iouDetail = iouDetail;
    }

    public String getIouBody() {
        return iouBody;
    }

    public void setIouBody(String iouBody) {
        this.iouBody = iouBody;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isInfoCheck() {
        return infoCheck;
    }

    public void setInfoCheck(boolean infoCheck) {
        this.infoCheck = infoCheck;
    }

    public boolean isHasContacts() {
        return hasContacts;
    }

    public void setHasContacts(boolean hasContacts) {
        this.hasContacts = hasContacts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "IouVerify{" + "infoCheck=" + infoCheck + ", realName1=" + realName1 + ", realName2=" + realName2
            + ", mobile=" + mobile + ", video=" + video + ", hasContacts=" + hasContacts + ", hasTradePassword="
            + hasTradePassword + ", authCount=" + authCount + ", authFee=" + authFee + ", authDetail='" + authDetail
            + '\'' + ", authBody='" + authBody + '\'' + ", iouCount=" + iouCount + ", iouFee=" + iouFee
            + ", iouDetail='" + iouDetail + '\'' + ", iouBody='" + iouBody + '\'' + ", errorMsg='" + errorMsg + '\''
            + ", userName='" + userName + '\'' + '}';
    }
}
