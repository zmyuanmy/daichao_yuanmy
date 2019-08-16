package com.jbb.mgt.rs.action.pf;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.AreaPlatformVo;
import com.jbb.mgt.core.domain.LoanPlatformPublish;
import com.jbb.mgt.core.domain.LoanPlatformTag;
import com.jbb.mgt.core.domain.LoanTag;
import com.jbb.mgt.core.domain.Platform;
import com.jbb.mgt.core.domain.PlatformVo;
import com.jbb.mgt.core.service.IpAddressService;
import com.jbb.mgt.core.service.LoanPlatformPublishService;
import com.jbb.mgt.core.service.LoanPlatformService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.common.util.ThreeDESUtil;
import com.jbb.server.shared.rs.Util;

@Service(LoanPlatformAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoanPlatformAction extends BasicAction {

    public static final String ACTION_NAME = "LoanPlatformAction";

    private static Logger logger = LoggerFactory.getLogger(LoanPlatformAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private LoanPlatformService loanPlatformService;

    @Autowired
    private LoanPlatformPublishService loanPlatformPublishService;

    @Autowired
    private IpAddressService ipAddressService;

    public void getPlatformByTagId(int[] areaTagId, Boolean getConfig, String os) {
        logger.debug(">getPlatformByTagId()");
        getPlatformByTagId(areaTagId, getConfig, os, true);
        logger.debug("<getPlatformByTagId()");
    }

    public void getPlatformByTagId(int[] areaTagId, Boolean getConfig, String os, boolean isOld) {
        logger.debug(">getPlatformByTagId pre v2()");

        boolean getConfigF = (getConfig != null && getConfig == true);
        if (getConfigF) {
            String config = PropertyManager.getProperty("jbb.mgt.loan.platform.area9.config");
            this.response.area9Config = JSONObject.parseArray(config, TimeNumConfig.class);
        }
        List<AreaPlatformVo> apfs = loanPlatformService.getPlatformsFromUI(areaTagId, os);
        this.response.areaTags = apfs;
        logger.debug("<getPlatformByTagId pre v2()");
    }

    public void getPlatformByTagIdV2(int[] areaTagId, Boolean getConfig, String os) {
        logger.debug(">getPlatformByTagIdV2()");
        getPlatformByTagId(areaTagId, getConfig, os, false);
        // 处理返回数据，3DES加密，并base64编码
        String data = JSONObject.toJSONString(this.response.areaTags);
        this.response.areaTags = null;

        // 1. 对data进行3des加密
        // 2. base 64编码
        String encodeKey = PropertyManager.getProperty("app.3des.key");
        byte[] iv = "12345678".getBytes(); // 偏移量
        String encodeStr = ThreeDESUtil.encryptWithBase64(data.getBytes(), encodeKey.getBytes(), iv);

        this.response.data = encodeStr;

        logger.debug("<getPlatformByTagIdV2()");
    }

    public void getPlatformById(Integer platformId) {
        logger.debug(">getPlatformById()");
        if (platformId == null || platformId <= 0) {
            throw new WrongParameterValueException("jbb.pfmgt.error.platform.empty");
        }
        this.response.platform = loanPlatformService.getPlatformById(platformId);
        logger.debug("<getPlatformById()");
    }

    public void getPlatforms(Boolean isDeleted, String groupName, Integer type, String platformName, Integer salesId) {
        logger.debug(">getPlatformById()");
        this.response.platforms
            = loanPlatformService.getPlatforms(isDeleted, null, null, groupName, type, platformName, salesId);
        logger.debug("<getPlatformById()");

    }

    public void insertPlatform(Request request) {
        logger.debug(">insertPlatform()");
        if (null == request) {
            throw new MissingParameterException("jbb.mgt.exception.parameterObject.notFound", "zh", "request");
        }
        Platform platform = generatePlatform(null, request);
        platform.setCreator(this.account.getAccountId());
        loanPlatformService.insertPlatform(platform);
        logger.debug("<insertPlatform()");

    }

    public void updatePlatformById(Integer platformId, Request request) {
        logger.debug(">updatePlatformById()");
        if (platformId == null || platformId <= 0) {
            throw new WrongParameterValueException("jbb.pfmgt.error.platform.notFound");
        }
        Platform platform = loanPlatformService.getPlatformById(platformId);
        if (platform != null) {
            platform = generatePlatform(platform, request);
            loanPlatformService.updatePlatform(platform);
        } else {
            throw new WrongParameterValueException("jbb.pfmgt.error.platform.notFound");
        }
        logger.debug("<updatePlatformById()");

    }

    public void updatePlatformStatus(Integer platformId, boolean isDeleted) {
        logger.debug(">deletePlatformById()");
        if (platformId == null || platformId <= 0) {
            throw new WrongParameterValueException("jbb.pfmgt.error.platform.notFound");
        }
        Platform platform = loanPlatformService.getPlatformById(platformId);
        if (platform != null) {
            loanPlatformService.updatePlatformStatus(platformId, isDeleted);
        } else {
            throw new WrongParameterValueException("jbb.pfmgt.error.platform.notFound");
        }

        logger.debug("<deletePlatformById()");

    }

    public void getPlatformTags(Integer areaId, String tagName, Integer platformId, Integer pos) {
        logger.debug(">getPlatformTags()");
        this.response.platformTags = loanPlatformService.getPlatformTags(areaId, tagName, platformId, pos);
        logger.debug("<getPlatformTags()");
    }

    public void getLoanTagByAreaId(int areaId) {
        logger.debug(">getLoanTagByAreaId()");
        this.response.loanTags = loanPlatformService.getLoanTagByAreaId(areaId);
        logger.debug("<getLoanTagByAreaId()");
    }

    public void getPlatformPublishByDate(String startDate, String endDate) {
        logger.debug(">getPlatformPublishByDate() startDate:{} endDate:{}" + startDate + endDate);
        if (startDate == null) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "startDate");
        }
        long sysTime = DateUtil.getCurrentTimeStamp().getTime();
        Timestamp startDateTs = Util.parseTimestamp(startDate, getTimezone());
        Timestamp endDateTs = (StringUtil.isEmpty(endDate)
            || (!StringUtil.isEmpty(endDate) && Util.parseTimestamp(endDate, getTimezone()).getTime() > sysTime))
                ? DateUtil.getCurrentTimeStamp() : Util.parseTimestamp(endDate, getTimezone());

        long startDateTime = startDateTs.getTime();
        if (sysTime - DateUtil.DAY_MILLSECONDES * 3 > startDateTime) {
            startDateTs = new Timestamp(DateUtil.getTodayStartTs() - DateUtil.DAY_MILLSECONDES * 3);
        }

        this.response.platfomrs = loanPlatformPublishService.getPlatformPublishByDate(startDateTs, endDateTs, false);
        logger.debug("<getPlatformPublishByDate()");

    }

    public void getPlatformGroupName() {
        logger.debug(">getPlatformGroupName()");
        this.response.platformGroupName = loanPlatformService.getPlatformGroupName();
        logger.debug("<getPlatformGroupName");
    }

    private Platform generatePlatform(Platform platform, Request req) {
        platform = null == platform ? new Platform() : platform;
        Platform platform2 = loanPlatformService.getPlatformByShortName(req.shortName, platform.getPlatformId());
        if (platform2 != null) {
            throw new WrongParameterValueException("jbb.pfmgt.platform.shortName.duplicate", "zh", req.shortName);
        }
        checkRequest(req);
        platform.setName(req.name);
        platform.setShortName(req.shortName);
        platform.setUrl(req.url);
        platform.setLogo(req.logo);
        platform.setAdImgUrl(req.adImgUrl);
        platform.setDesc1(req.desc1);
        platform.setDesc2(req.desc2);
        platform.setDesc3(req.desc3);
        platform.setMinAmount(req.minAmount);
        platform.setMaxAmount(req.maxAmount);
        platform.setLoanPeriod(req.loanPeriod);
        platform.setInterestRate(req.interestRate);
        platform.setApprovalTime(req.approvalTime);
        platform.setNew(req.isNew);
        platform.setHot(req.isHot);
        platform.setDeleted(req.isDeleted);
        req.isSupportIos = req.isSupportIos == null ? true : req.isSupportIos;
        req.isSupportAndroid = req.isSupportAndroid == null ? true : req.isSupportAndroid;
        req.isHomePagePush = req.isHomePagePush == null ? false : req.isHomePagePush;
        platform.setSupportIos(req.isSupportIos);
        platform.setSupportAndroid(req.isSupportAndroid);
        platform.setHomePagePush(req.isHomePagePush);
        platform.setCpaPrice(req.cpaPrice);
        platform.setPriceMode(req.priceMode);
        platform.setUvPrice(req.uvPrice);
        platform.setEstimatedUvCnt(req.estimatedUvCnt);
        platform.setMinBalance(req.minBalance);
        platform.setType(req.type);
        platform.setGroupName(req.groupName);
        platform.setSalesId(req.salesId);
        return platform;
    }

    private void checkRequest(Request req) {
        if (req.priceMode == null) {
            throw new MissingParameterException("jbb.pfmgt.error.platform.priceMode");
        }
        if (req.type == null || (req.type != null && req.type != 1 && req.type != 2 && req.type != 3)) {
            throw new MissingParameterException("jbb.pfmgt.error.platform.type");
        }
        if (StringUtil.isEmpty(req.groupName)) {
            throw new MissingParameterException("jbb.pfmgt.error.platform.groupName");
        }
    }

    public static class TimeNumConfig {
        private int num;
        private String timeStr;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getTimeStr() {
            return timeStr;
        }

        public void setTimeStr(String timeStr) {
            this.timeStr = timeStr;
        }

    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<AreaPlatformVo> areaTags;
        private Platform platform;
        private List<PlatformVo> platforms;
        private List<LoanPlatformTag> platformTags;
        private List<LoanTag> loanTags;
        private List<TimeNumConfig> area9Config;
        private List<LoanPlatformPublish> platfomrs;
        public List<String> platformGroupName;

        private String data;

        public List<PlatformVo> getPlatforms() {
            return platforms;
        }

        public List<AreaPlatformVo> getAreaTags() {
            return areaTags;
        }

        public Platform getPlatform() {
            return platform;
        }

        public List<LoanPlatformTag> getPlatformTags() {
            return platformTags;
        }

        public void setPlatformTags(List<LoanPlatformTag> platformTags) {
            this.platformTags = platformTags;
        }

        public List<LoanTag> getLoanTags() {
            return loanTags;
        }

        public List<TimeNumConfig> getArea9Config() {
            return area9Config;
        }

        public List<LoanPlatformPublish> getPlatfomrs() {
            return platfomrs;
        }

        public List<String> getPlatformGroupName() {
            return platformGroupName;
        }

        public String getData() {
            return data;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String name;
        public String shortName;
        public String url;
        public String logo;
        public String adImgUrl;
        public String desc1;
        public String desc2;
        public String desc3;
        public int minAmount;
        public int maxAmount;
        public String loanPeriod;
        public String interestRate;
        public String approvalTime;
        public boolean isNew;
        public boolean isHot;
        public boolean isDeleted;
        public int cpaPrice;
        public String groupName;
        public Integer type;
        public Integer salesId;
        public Boolean isSupportIos;
        public Boolean isSupportAndroid;
        public Boolean isHomePagePush;
        public Integer priceMode;
        public int uvPrice;
        public Integer estimatedUvCnt;
        public Integer minBalance;
    }

}
