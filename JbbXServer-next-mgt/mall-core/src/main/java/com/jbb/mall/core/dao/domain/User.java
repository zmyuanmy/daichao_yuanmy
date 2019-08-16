package com.jbb.mall.core.dao.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

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
    private String contract1Relation;// 联系人1关系
    private String contract1Username;// 联系人1名字
    private String contract1Phonenumber;// 联系人1联系方式
    private String contract2Relation;// 联系人2关系
    private String contract2Username;// 联系人2名字
    private String contract2Phonenumber;// 联系人2联系方式
    private String vidoeScreenShot; // 视频截图
    private String platform;// 注册平台
    private String mobileManufacture;// 手机型号
    private String idcardAddress;// 身份证地址
    private String race;// 民族
    private String sex;
    private Integer age;
    private String census;// 户籍
    private Integer applyId;
    private String initMarks;
    private String applyOrgIds;
    private String applyOrgNames;
    private boolean isHidden;
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

    private boolean cardStatus;
    private String avatarPic;
    private String occupation;

    public boolean isCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(boolean cardStatus) {
        this.cardStatus = cardStatus;
    }

    public boolean isIdcardVerified() {
        return idcardVerified;
    }

    public void setIdcardVerified(boolean idcardVerified) {
        this.idcardVerified = idcardVerified;
    }

    public boolean isOcrIdcardVerified() {
        return ocrIdcardVerified;
    }

    public void setOcrIdcardVerified(boolean ocrIdcardVerified) {
        this.ocrIdcardVerified = ocrIdcardVerified;
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

    public boolean isTaobaoVerified() {
        return taobaoVerified;
    }

    public void setTaobaoVerified(boolean taobaoVerified) {
        this.taobaoVerified = taobaoVerified;
    }

    public boolean isXjlBasicInfoVerified() {
        return xjlBasicInfoVerified;
    }

    public void setXjlBasicInfoVerified(boolean xjlBasicInfoVerified) {
        this.xjlBasicInfoVerified = xjlBasicInfoVerified;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
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

    public String getVidoeScreenShot() {
        return vidoeScreenShot;
    }

    public void setVidoeScreenShot(String vidoeScreenShot) {
        this.vidoeScreenShot = vidoeScreenShot;
    }

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

    public String getContract1Relation() {
        return contract1Relation;
    }

    public void setContract1Relation(String contract1Relation) {
        this.contract1Relation = contract1Relation;
    }

    public String getContract1Username() {
        return contract1Username;
    }

    public void setContract1Username(String contract1Username) {
        this.contract1Username = contract1Username;
    }

    public String getContract1Phonenumber() {
        return contract1Phonenumber;
    }

    public void setContract1Phonenumber(String contract1Phonenumber) {
        this.contract1Phonenumber = contract1Phonenumber;
    }

    public String getContract2Relation() {
        return contract2Relation;
    }

    public void setContract2Relation(String contract2Relation) {
        this.contract2Relation = contract2Relation;
    }

    public String getContract2Username() {
        return contract2Username;
    }

    public void setContract2Username(String contract2Username) {
        this.contract2Username = contract2Username;
    }

    public String getContract2Phonenumber() {
        return contract2Phonenumber;
    }

    public void setContract2Phonenumber(String contract2Phonenumber) {
        this.contract2Phonenumber = contract2Phonenumber;
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

    public String getInitMarks() {
        return initMarks;
    }

    public void setInitMarks(String initMarks) {
        this.initMarks = initMarks;
    }

    public String getApplyOrgIds() {
        return applyOrgIds;
    }

    public void setApplyOrgIds(String applyOrgIds) {
        this.applyOrgIds = applyOrgIds;
    }

    public String getApplyOrgNames() {
        return applyOrgNames;
    }

    public void setApplyOrgNames(String applyOrgNames) {
        this.applyOrgNames = applyOrgNames;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
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

    public String getAvatarPic() {
        return avatarPic;
    }

    public void setAvatarPic(String avatarPic) {
        this.avatarPic = avatarPic;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

}
