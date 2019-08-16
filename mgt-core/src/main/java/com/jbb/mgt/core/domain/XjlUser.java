package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class XjlUser {

    public static final int USER_APPLICATION_ID = 0;

    // 用户补充资料
    public static final int ADDINFO_APPLICATION_ID = 1;

    // 小金条
    public static final int XJL_APPLICATION_ID = 2;

    private int userId;
    private Integer jbbUserId;
    private String userName;
    private String phoneNumber;
    private String idCard;
    private String ipAddress;
    private String ipArea;
    private Integer zhimaScore;
    private String qq;
    private String wechat;
    private String channelCode;
    private boolean realnameVerified;
    private boolean mobileVerified;
    private boolean zhimaVerified;
    private boolean jingdongVerified;
    private boolean siVerified;
    private boolean gjjVerified;
    private boolean chsiVerified;
    private boolean taobaoVerified;
    private boolean videoVerified;
    private boolean hasContacts;
    private boolean ocrIdcardVerified;
    private boolean idcardVerified;
    private Timestamp creationDate;
    private String idcardRear;// 身份证正面
    private String idcardBack;// 身份证反面
    private String idcardInfo;// 手持身份证
    private String vidoeScreenShot; // 视频截图
    private String platform;// 注册平台
    private String mobileManufacture;// 手机型号
    private String idcardAddress;// 身份证地址
    private String race;// 民族
    private String sex;
    private Integer age;
    private String census;// 户籍
    private UserKey key;
    private Channel channels;
    private int userType;
    private Integer zhimaMin;
    private Integer zhimaMax;
    @JsonIgnore
    private String password;
    private Integer maritalStatus;// 婚姻状况
    private String education;// 教育
    private String contract1XjlRelation;// 联系人1关系
    private String contract1XjlUsername;// 联系人1名字
    private String contract1XjlPhonenumber;// 联系人1联系方式
    private String contract2XjlRelation;// 联系人2关系
    private String contract2XjlUsername;// 联系人2名字
    private String contract2XjlPhonenumber;// 联系人2联系方式
    private boolean xjlBasicInfoVerified;
    private UserAddresses liveAddress;
    private UserJob jobInfo;
    private boolean cardStatus;
    private XjlApplyRecord xjlApplyRecord;
    private Timestamp receiveDate;
    private int userStatus;
    private int applyCnt;
    private int loanCnt;
    private int repaymentCnt;
    private int overdueCnt;
    private Timestamp loginDate;
    private boolean appLoginStatus;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getJbbUserId() {
        return jbbUserId;
    }

    public void setJbbUserId(Integer jbbUserId) {
        this.jbbUserId = jbbUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpArea() {
        return ipArea;
    }

    public void setIpArea(String ipArea) {
        this.ipArea = ipArea;
    }

    public Integer getZhimaScore() {
        return zhimaScore;
    }

    public void setZhimaScore(Integer zhimaScore) {
        this.zhimaScore = zhimaScore;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public boolean isRealnameVerified() {
        return realnameVerified;
    }

    public void setRealnameVerified(boolean realnameVerified) {
        this.realnameVerified = realnameVerified;
    }

    public boolean isMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    public boolean isZhimaVerified() {
        return zhimaVerified;
    }

    public void setZhimaVerified(boolean zhimaVerified) {
        this.zhimaVerified = zhimaVerified;
    }

    public boolean isJingdongVerified() {
        return jingdongVerified;
    }

    public void setJingdongVerified(boolean jingdongVerified) {
        this.jingdongVerified = jingdongVerified;
    }

    public boolean isSiVerified() {
        return siVerified;
    }

    public void setSiVerified(boolean siVerified) {
        this.siVerified = siVerified;
    }

    public boolean isGjjVerified() {
        return gjjVerified;
    }

    public void setGjjVerified(boolean gjjVerified) {
        this.gjjVerified = gjjVerified;
    }

    public boolean isChsiVerified() {
        return chsiVerified;
    }

    public void setChsiVerified(boolean chsiVerified) {
        this.chsiVerified = chsiVerified;
    }

    public boolean isTaobaoVerified() {
        return taobaoVerified;
    }

    public void setTaobaoVerified(boolean taobaoVerified) {
        this.taobaoVerified = taobaoVerified;
    }

    public boolean isVideoVerified() {
        return videoVerified;
    }

    public void setVideoVerified(boolean videoVerified) {
        this.videoVerified = videoVerified;
    }

    public boolean isHasContacts() {
        return hasContacts;
    }

    public void setHasContacts(boolean hasContacts) {
        this.hasContacts = hasContacts;
    }

    public boolean isOcrIdcardVerified() {
        return ocrIdcardVerified;
    }

    public void setOcrIdcardVerified(boolean ocrIdcardVerified) {
        this.ocrIdcardVerified = ocrIdcardVerified;
    }

    public boolean isIdcardVerified() {
        return idcardVerified;
    }

    public void setIdcardVerified(boolean idcardVerified) {
        this.idcardVerified = idcardVerified;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getIdcardRear() {
        return idcardRear;
    }

    public void setIdcardRear(String idcardRear) {
        this.idcardRear = idcardRear;
    }

    public String getIdcardBack() {
        return idcardBack;
    }

    public void setIdcardBack(String idcardBack) {
        this.idcardBack = idcardBack;
    }

    public String getIdcardInfo() {
        return idcardInfo;
    }

    public void setIdcardInfo(String idcardInfo) {
        this.idcardInfo = idcardInfo;
    }

    public String getVidoeScreenShot() {
        return vidoeScreenShot;
    }

    public void setVidoeScreenShot(String vidoeScreenShot) {
        this.vidoeScreenShot = vidoeScreenShot;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getMobileManufacture() {
        return mobileManufacture;
    }

    public void setMobileManufacture(String mobileManufacture) {
        this.mobileManufacture = mobileManufacture;
    }

    public String getIdcardAddress() {
        return idcardAddress;
    }

    public void setIdcardAddress(String idcardAddress) {
        this.idcardAddress = idcardAddress;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCensus() {
        return census;
    }

    public void setCensus(String census) {
        this.census = census;
    }

    public UserKey getKey() {
        return key;
    }

    public void setKey(UserKey key) {
        this.key = key;
    }

    public Channel getChannels() {
        return channels;
    }

    public void setChannels(Channel channels) {
        this.channels = channels;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Integer getZhimaMin() {
        return zhimaMin;
    }

    public void setZhimaMin(Integer zhimaMin) {
        this.zhimaMin = zhimaMin;
    }

    public Integer getZhimaMax() {
        return zhimaMax;
    }

    public void setZhimaMax(Integer zhimaMax) {
        this.zhimaMax = zhimaMax;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getContract1XjlRelation() {
        return contract1XjlRelation;
    }

    public void setContract1XjlRelation(String contract1XjlRelation) {
        this.contract1XjlRelation = contract1XjlRelation;
    }

    public String getContract1XjlUsername() {
        return contract1XjlUsername;
    }

    public void setContract1XjlUsername(String contract1XjlUsername) {
        this.contract1XjlUsername = contract1XjlUsername;
    }

    public String getContract1XjlPhonenumber() {
        return contract1XjlPhonenumber;
    }

    public void setContract1XjlPhonenumber(String contract1XjlPhonenumber) {
        this.contract1XjlPhonenumber = contract1XjlPhonenumber;
    }

    public String getContract2XjlRelation() {
        return contract2XjlRelation;
    }

    public void setContract2XjlRelation(String contract2XjlRelation) {
        this.contract2XjlRelation = contract2XjlRelation;
    }

    public String getContract2XjlUsername() {
        return contract2XjlUsername;
    }

    public void setContract2XjlUsername(String contract2XjlUsername) {
        this.contract2XjlUsername = contract2XjlUsername;
    }

    public String getContract2XjlPhonenumber() {
        return contract2XjlPhonenumber;
    }

    public void setContract2XjlPhonenumber(String contract2XjlPhonenumber) {
        this.contract2XjlPhonenumber = contract2XjlPhonenumber;
    }

    public boolean isXjlBasicInfoVerified() {
        return xjlBasicInfoVerified;
    }

    public void setXjlBasicInfoVerified(boolean xjlBasicInfoVerified) {
        this.xjlBasicInfoVerified = xjlBasicInfoVerified;
    }

    public UserAddresses getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(UserAddresses liveAddress) {
        this.liveAddress = liveAddress;
    }

    public UserJob getJobInfo() {
        return jobInfo;
    }

    public void setJobInfo(UserJob jobInfo) {
        this.jobInfo = jobInfo;
    }

    public boolean isCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(boolean cardStatus) {
        this.cardStatus = cardStatus;
    }

    public XjlApplyRecord getXjlApplyRecord() {
        return xjlApplyRecord;
    }

    public void setXjlApplyRecord(XjlApplyRecord xjlApplyRecord) {
        this.xjlApplyRecord = xjlApplyRecord;
    }

    public Timestamp getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Timestamp receiveDate) {
        this.receiveDate = receiveDate;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public int getApplyCnt() {
        return applyCnt;
    }

    public void setApplyCnt(int applyCnt) {
        this.applyCnt = applyCnt;
    }

    public int getLoanCnt() {
        return loanCnt;
    }

    public void setLoanCnt(int loanCnt) {
        this.loanCnt = loanCnt;
    }

    public int getRepaymentCnt() {
        return repaymentCnt;
    }

    public void setRepaymentCnt(int repaymentCnt) {
        this.repaymentCnt = repaymentCnt;
    }

    public int getOverdueCnt() {
        return overdueCnt;
    }

    public void setOverdueCnt(int overdueCnt) {
        this.overdueCnt = overdueCnt;
    }

    public Timestamp getLoginDate() {
        return loginDate;
    }

    public boolean isAppLoginStatus() {
        return appLoginStatus;
    }

    @Override
    public String toString() {
        return "XjlUser [userId=" + userId + ", jbbUserId=" + jbbUserId + ", userName=" + userName + ", phoneNumber="
            + phoneNumber + ", idCard=" + idCard + ", ipAddress=" + ipAddress + ", ipArea=" + ipArea + ", zhimaScore="
            + zhimaScore + ", qq=" + qq + ", wechat=" + wechat + ", channelCode=" + channelCode + ", realnameVerified="
            + realnameVerified + ", mobileVerified=" + mobileVerified + ", zhimaVerified=" + zhimaVerified
            + ", jingdongVerified=" + jingdongVerified + ", siVerified=" + siVerified + ", gjjVerified=" + gjjVerified
            + ", chsiVerified=" + chsiVerified + ", taobaoVerified=" + taobaoVerified + ", videoVerified="
            + videoVerified + ", hasContacts=" + hasContacts + ", ocrIdcardVerified=" + ocrIdcardVerified
            + ", idcardVerified=" + idcardVerified + ", creationDate=" + creationDate + ", idcardRear=" + idcardRear
            + ", idcardBack=" + idcardBack + ", idcardInfo=" + idcardInfo + ", vidoeScreenShot=" + vidoeScreenShot
            + ", platform=" + platform + ", mobileManufacture=" + mobileManufacture + ", idcardAddress=" + idcardAddress
            + ", race=" + race + ", sex=" + sex + ", age=" + age + ", census=" + census + ", key=" + key + ", channels="
            + channels + ", userType=" + userType + ", zhimaMin=" + zhimaMin + ", zhimaMax=" + zhimaMax + ", password="
            + password + ", maritalStatus=" + maritalStatus + ", education=" + education + ", contract1XjlRelation="
            + contract1XjlRelation + ", contract1XjlUsername=" + contract1XjlUsername + ", contract1XjlPhonenumber="
            + contract1XjlPhonenumber + ", contract2XjlRelation=" + contract2XjlRelation + ", contract2XjlUsername="
            + contract2XjlUsername + ", contract2XjlPhonenumber=" + contract2XjlPhonenumber + ", xjlBasicInfoVerified="
            + xjlBasicInfoVerified + ", liveAddress=" + liveAddress + ", jobInfo=" + jobInfo + ", cardStatus="
            + cardStatus + ", xjlApplyRecord=" + xjlApplyRecord + ", receiveDate=" + receiveDate + ", userStatus="
            + userStatus + ", applyCnt=" + applyCnt + ", loanCnt=" + loanCnt + ", repaymentCnt=" + repaymentCnt
            + ", overdueCnt=" + overdueCnt + ", loginDate=" + loginDate + ", appLoginStatus=" + appLoginStatus + "]";
    }

}
