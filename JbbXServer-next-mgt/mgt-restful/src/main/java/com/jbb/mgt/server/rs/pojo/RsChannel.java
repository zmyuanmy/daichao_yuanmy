package com.jbb.mgt.server.rs.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.ChannelStatistic;
import com.jbb.server.shared.rs.Util;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RsChannel {
    private String channelCode;
    private String channelName;
    private String channelUrl;
    private String serviceQQ;
    private String serviceWechat;
    private int status;
    private int mode;
    private String sourcePhoneNumber;
    private Long creationDate;
    private boolean qqRequired;
    private boolean wechatRequired;
    private boolean zhimaRequired;
    private boolean idcardInfoRequired;
    private boolean idcardBackRequired;
    private boolean idcardRearRequired;
    private boolean headerRequired;
    private boolean mobileContract1Required;
    private boolean mobileContract2Required;
    private boolean mobileServiceInfoRequired;
    private int cpaPrice;
    private int cpsPrice;
    private int uvPrice;
    private int receiveMode;
    private boolean hidden;
    private boolean taobaoRequired;
    private boolean jingdongRequired;
    private boolean gjjRequired;
    private boolean sjRequired;
    private boolean chsiRequired;
    private boolean checkWool;
    private boolean checkMobile;
    private boolean delegate;
    private String redirectUrl;
    private Integer merchantId;
    private Integer merchantId2;
    private boolean delegateEnabled;
    private RsAccount creator;
    private ChannelStatistic statistic;
    private String channelAppName;
    private String h5Theme;
    private String downloadApp;
    private boolean testFlag;
    private String groupName;
    private boolean reduceEnable;
    private int reducePercent;
    public Integer delegateOrgId;
    private boolean companyRequired;

    public RsChannel() {

    }

    public RsChannel(Channel channel) {
        this.channelCode = channel.getChannelCode();
        this.channelName = channel.getChannelName();
        this.channelUrl = channel.getChannelUrl();
        this.serviceQQ = channel.getServiceQQ();
        this.serviceWechat = channel.getServiceWechat();
        this.status = channel.getStatus();
        this.mode = channel.getMode();
        this.sourcePhoneNumber = channel.getSourcePhoneNumber();
        this.creationDate = Util.getTimeMs(channel.getCreationDate());
        this.qqRequired = channel.isQqRequired();
        this.wechatRequired = channel.isWechatRequired();
        this.zhimaRequired = channel.isZhimaRequired();
        this.idcardInfoRequired = channel.isIdcardInfoRequired();
        this.idcardBackRequired = channel.isIdcardBackRequired();
        this.idcardRearRequired = channel.isIdcardRearRequired();
        this.headerRequired = channel.isHeaderRequired();
        this.mobileContract1Required = channel.isMobileContract1Required();
        this.mobileContract2Required = channel.isMobileContract2Required();
        this.mobileServiceInfoRequired = channel.isMobileServiceInfoRequired();
        this.cpaPrice = channel.getCpaPrice();
        this.cpsPrice = channel.getCpsPrice();
        this.uvPrice=channel.getUvPrice();
        this.receiveMode = channel.getReceiveMode();
        this.hidden = channel.isHidden();
        this.taobaoRequired = channel.isTaobaoRequired();
        this.jingdongRequired = channel.isJingdongRequired();
        this.gjjRequired = channel.isGjjRequired();
        this.sjRequired = channel.isSjRequired();
        this.chsiRequired = channel.isChsiRequired();
        this.checkWool = channel.isCheckWool();
        this.checkMobile = channel.isCheckMobile();
        this.delegate = channel.isDelegate();
        this.redirectUrl = channel.getRedirectUrl();
        this.merchantId = channel.getMerchantId();
        this.merchantId2 = channel.getMerchantId2();
        this.delegateEnabled = channel.isDelegateEnabled();
        this.channelAppName = channel.getChannelAppName();
        this.h5Theme = channel.getH5Theme();
        this.downloadApp = channel.getDownloadApp();
        this.testFlag = channel.isTestFlag();
        this.groupName = channel.getGroupName();
        this.reduceEnable = channel.isReduceEnable();
        this.reducePercent = channel.getReducePercent();
        this.delegateOrgId = channel.getDelegateOrgId();
        this.companyRequired = channel.isCompanyRequired();
        if (null != channel.getAccount()) {
            this.creator = new RsAccount();
            this.creator.setAccountId(channel.getAccount().getAccountId());
            this.creator.setUsername(channel.getAccount().getUsername());
            this.creator.setNickname(channel.getAccount().getNickname());
            this.creator.setPhoneNumber(channel.getAccount().getPhoneNumber());
            this.creator.setJbbUserId(channel.getAccount().getJbbUserId());
            this.creator.setPassword(channel.getAccount().getPassword());
            this.creator.setOrgId(channel.getAccount().getOrgId());
            this.creator.setCreator(channel.getAccount().getCreator());
            this.creator.setCreationDate(Util.getTimeMs(channel.getAccount().getCreationDate()));
            this.creator.setDeleted(channel.getAccount().isDeleted());
            this.creator.setFreeze(channel.getAccount().isFreeze());
        }
        if (null != channel.getStatistic()) {
            this.statistic = channel.getStatistic();
        }
    }

    public int getReceiveMode() {
        return receiveMode;
    }

    public void setReceiveMode(int receiveMode) {
        this.receiveMode = receiveMode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public String getServiceQQ() {
        return serviceQQ;
    }

    public void setServiceQQ(String serviceQQ) {
        this.serviceQQ = serviceQQ;
    }

    public String getServiceWechat() {
        return serviceWechat;
    }

    public void setServiceWechat(String serviceWechat) {
        this.serviceWechat = serviceWechat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getSourcePhoneNumber() {
        return sourcePhoneNumber;
    }

    public void setSourcePhoneNumber(String sourcePhoneNumber) {
        this.sourcePhoneNumber = sourcePhoneNumber;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isQqRequired() {
        return qqRequired;
    }

    public void setQqRequired(boolean qqRequired) {
        this.qqRequired = qqRequired;
    }

    public boolean isWechatRequired() {
        return wechatRequired;
    }

    public void setWechatRequired(boolean wechatRequired) {
        this.wechatRequired = wechatRequired;
    }

    public boolean isZhimaRequired() {
        return zhimaRequired;
    }

    public void setZhimaRequired(boolean zhimaRequired) {
        this.zhimaRequired = zhimaRequired;
    }

    public boolean isIdcardInfoRequired() {
        return idcardInfoRequired;
    }

    public void setIdcardInfoRequired(boolean idcardInfoRequired) {
        this.idcardInfoRequired = idcardInfoRequired;
    }

    public boolean isIdcardBackRequired() {
        return idcardBackRequired;
    }

    public void setIdcardBackRequired(boolean idcardBackRequired) {
        this.idcardBackRequired = idcardBackRequired;
    }

    public boolean isIdcardRearRequired() {
        return idcardRearRequired;
    }

    public void setIdcardRearRequired(boolean idcardRearRequired) {
        this.idcardRearRequired = idcardRearRequired;
    }

    public boolean isHeaderRequired() {
        return headerRequired;
    }

    public void setHeaderRequired(boolean headerRequired) {
        this.headerRequired = headerRequired;
    }

    public boolean isMobileContract1Required() {
        return mobileContract1Required;
    }

    public void setMobileContract1Required(boolean mobileContract1Required) {
        this.mobileContract1Required = mobileContract1Required;
    }

    public boolean isMobileContract2Required() {
        return mobileContract2Required;
    }

    public void setMobileContract2Required(boolean mobileContract2Required) {
        this.mobileContract2Required = mobileContract2Required;
    }

    public boolean isMobileServiceInfoRequired() {
        return mobileServiceInfoRequired;
    }

    public void setMobileServiceInfoRequired(boolean mobileServiceInfoRequired) {
        this.mobileServiceInfoRequired = mobileServiceInfoRequired;
    }

    public int getCpaPrice() {
        return cpaPrice;
    }

    public void setCpaPrice(int cpaPrice) {
        this.cpaPrice = cpaPrice;
    }

    public int getCpsPrice() {
        return cpsPrice;
    }

    public void setCpsPrice(int cpsPrice) {
        this.cpsPrice = cpsPrice;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isTaobaoRequired() {
        return taobaoRequired;
    }

    public void setTaobaoRequired(boolean taobaoRequired) {
        this.taobaoRequired = taobaoRequired;
    }

    public boolean isJingdongRequired() {
        return jingdongRequired;
    }

    public void setJingdongRequired(boolean jingdongRequired) {
        this.jingdongRequired = jingdongRequired;
    }

    public boolean isGjjRequired() {
        return gjjRequired;
    }

    public void setGjjRequired(boolean gjjRequired) {
        this.gjjRequired = gjjRequired;
    }

    public boolean isSjRequired() {
        return sjRequired;
    }

    public void setSjRequired(boolean sjRequired) {
        this.sjRequired = sjRequired;
    }

    public boolean isChsiRequired() {
        return chsiRequired;
    }

    public void setChsiRequired(boolean chsiRequired) {
        this.chsiRequired = chsiRequired;
    }

    public RsAccount getCreator() {
        return creator;
    }

    public void setCreator(RsAccount creator) {
        this.creator = creator;
    }

    public boolean isDelegate() {
        return delegate;
    }

    public void setDelegate(boolean delegate) {
        this.delegate = delegate;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public ChannelStatistic getStatistic() {
        return statistic;
    }

    public void setStatistic(ChannelStatistic statistic) {
        this.statistic = statistic;
    }

    public boolean isCheckWool() {
        return checkWool;
    }

    public void setCheckWool(boolean checkWool) {
        this.checkWool = checkWool;
    }

    public boolean isCheckMobile() {
        return checkMobile;
    }

    public void setCheckMobile(boolean checkMobile) {
        this.checkMobile = checkMobile;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public boolean isDelegateEnabled() {
        return delegateEnabled;
    }

    public void setDelegateEnabled(boolean delegateEnabled) {
        this.delegateEnabled = delegateEnabled;
    }

    public String getChannelAppName() {
        return channelAppName;
    }

    public void setChannelAppName(String channelAppName) {
        this.channelAppName = channelAppName;
    }

    public String getH5Theme() {
        return h5Theme;
    }

    public void setH5Theme(String h5Theme) {
        this.h5Theme = h5Theme;
    }

    public String getDownloadApp() {
        return downloadApp;
    }

    public void setDownloadApp(String downloadApp) {
        this.downloadApp = downloadApp;
    }

    public boolean isTestFlag() {
        return testFlag;
    }

    public void setTestFlag(boolean testFlag) {
        this.testFlag = testFlag;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isReduceEnable() {
        return reduceEnable;
    }

    public void setReduceEnable(boolean reduceEnable) {
        this.reduceEnable = reduceEnable;
    }

    public int getReducePercent() {
        return reducePercent;
    }

    public void setReducePercent(int reducePercent) {
        this.reducePercent = reducePercent;
    }

    public Integer getMerchantId2() {
        return merchantId2;
    }

    public void setMerchantId2(Integer merchantId2) {
        this.merchantId2 = merchantId2;
    }

    public Integer getDelegateOrgId() {
        return delegateOrgId;
    }

    public void setDelegateOrgId(Integer delegateOrgId) {
        this.delegateOrgId = delegateOrgId;
    }

    public boolean isCompanyRequired() {
        return companyRequired;
    }

    public void setCompanyRequired(boolean companyRequired) {
        this.companyRequired = companyRequired;
    }

    public int getUvPrice() {
        return uvPrice;
    }

    public void setUvPrice(int uvPrice) {
        this.uvPrice = uvPrice;
    }

}
