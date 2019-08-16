package com.jbb.mgt.rs.action.h5Merchant;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.AppInfo;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.H5Merchant;
import com.jbb.mgt.core.service.ChannelService;
import com.jbb.mgt.core.service.H5MerchantService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.StringUtil;


@Service(H5MerchantAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class H5MerchantAction extends BasicAction {

    public static final String ACTION_NAME = "H5MerchantAction";

    private static Logger logger = LoggerFactory.getLogger(H5MerchantAction.class);

    private Response response;

    @Autowired
    private H5MerchantService service;

    @Autowired
    private ChannelService channelService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void insertH5Merchant(Request req) {
        logger.debug(">insertH5Merchant, req={}", req);
        if (null == req) {
            throw new MissingParameterException("jbb.mgt.exception.parameterObject.notFound", "zh", "H5Merchant");
        }
        H5Merchant h5Merchant = generateH5Merchant(null, req);
        h5Merchant.setCreator(this.account.getAccountId());
        service.insertH5Merchant(h5Merchant);
        logger.debug("<insertH5Merchant");
    }

    public void selectH5Merchants() {
        logger.debug(">selectH5Merchants");
        List<H5Merchant> list = service.selectH5Merchants();
        if (list.size() > 0) {
            this.response.list = new ArrayList<>(list.size());
            this.response.list.addAll(list);
        }
        logger.debug("<selectH5Merchants");
    }

    public void updateH5Merchant(Request req) {
        logger.debug(">updateH5Merchant, req={}", req);
        if (null == req || null == req.merchantId) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "merchantId");
        }
        H5Merchant h5Merchant = service.selectH5merchantById(req.merchantId);
        if (null == h5Merchant) {
            throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "merchantId");
        }
        h5Merchant = generateH5Merchant(h5Merchant, req);
        service.updateH5Merchant(h5Merchant);
        logger.debug("<updateH5Merchant");
    }

    public void selectH5ConfigByChannelCode(String channelCode, String platform) {
        logger.debug(">selectH5ConfigByChannelCode, channelCode =" + channelCode + ", platform =" + platform);
        if (StringUtil.isEmpty(channelCode)) {
            logger.debug("<selectH5ConfigByChannelCode with empty channelCode = ");
            return;
        }

        // 如果是测试手机号，则不推荐h5商家
        String[] ignoreRegisterMobiles = PropertyManager.getProperties("jbb.user.register.h5.ignore");
        if (user != null && CommonUtil.inArray(user.getPhoneNumber(), ignoreRegisterMobiles)) {
            return;
        }
        // end

        try {
            Channel channel = channelService.getChannelByCode(channelCode);

            if (null != channel) {
                this.response.testFlag = channel.isTestFlag();
                AppInfo appInfo = new AppInfo();
                this.response.downloadAppInfo = appInfo;
                String appId = "xhb";
                appInfo.setAppId(appId);
                appInfo.setAppId(PropertyManager.getProperty("h5.download.app." + appId + ".id"));
                appInfo.setDefaultUrl(PropertyManager.getProperty("h5.download.app." + appId + ".url.default"));
                appInfo.setIosUrl(PropertyManager.getProperty("h5.download.app." + appId + ".url.ios"));
                appInfo.setAndroidUrl(PropertyManager.getProperty("h5.download.app." + appId + ".url.android"));
            } else {
                this.response.testFlag = false;
            }

        } catch (Exception e) {
        }
        logger.debug("<selectH5MerchantByChannelCode");
    }

    private H5Merchant generateH5Merchant(H5Merchant hm, Request req) {
        hm = null == hm ? new H5Merchant() : hm;
        if (!StringUtil.isEmpty(req.name)) {
            hm.setName(req.name);
        }
        if (!StringUtil.isEmpty(req.url)) {
            hm.setUrl(req.url);
        }
        if (!StringUtil.isEmpty(req.logo)) {
            hm.setLogo(req.logo);
        }
        hm.setDesc1(req.desc1);
        hm.setDesc2(req.desc2);
        hm.setDesc3(req.desc3);
        if (!StringUtil.isEmpty(req.shortName)) {
            hm.setShortName(req.shortName);
        }
        return hm;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Integer merchantId;
        public String name;
        public String shortName;
        public String url;
        public String logo;
        public String desc1;
        public String desc2;
        public String desc3;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        public List<H5Merchant> list;
        // H5商家配置
        public H5Merchant h5;
        // 下载APP信息
        public AppInfo downloadAppInfo;
        // 是否测试
        public boolean testFlag;

    }
}
