package com.jbb.mgt.rs.action.channel;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.ChannelPromotion;
import com.jbb.mgt.core.domain.ChannelSimpleInfo;
import com.jbb.mgt.core.domain.ChannelStatistic;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.OrganizationDelegateInfo;
import com.jbb.mgt.core.service.ChannelService;
import com.jbb.mgt.core.service.OrgDelegateService;
import com.jbb.mgt.core.service.OrganizationService;
import com.jbb.mgt.server.core.util.PasswordUtil;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.mgt.server.rs.pojo.RsAccount;
import com.jbb.mgt.server.rs.pojo.RsChannel;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

/**
 * 渠道操作action
 * 
 * @author wyq
 * @date 2018/04/25
 */
@Service(ChannelAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ChannelAction extends BasicAction {

    public static final String ACTION_NAME = "ChannelAction";

    private static Logger logger = LoggerFactory.getLogger(ChannelAction.class);

    @Autowired
    private ChannelService channelService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrgDelegateService orgDelegateService;

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void createChannel(Request req) {
        logger.debug(">createChannel, channle={}", req);
        Channel channel = generateChannel(null, req);
        // 检查渠道code 是否存在
        Channel channelByCode = channelService.getChannelByCode(channel.getChannelCode());
        if (null != channelByCode) {
            throw new MissingParameterException("jbb.error.exception.duplicateChannel", "zh", "channelCode");
        }

        // 检查账户是否被渠道占用
        Channel chnnelE = channelService.getChannelBySourcePhoneNumber(channel.getSourcePhoneNumber().trim());
        if (chnnelE != null) {
            throw new WrongParameterValueException("jbb.mgt.exception.channel.PhonenumberError");
        }

        validateRequest(channel);

        if (StringUtil.isEmpty(req.channelCode)) {
            channel.setChannelCode(StringUtil.randomAlphaNum(6));
        }
        channel.setCreationDate(DateUtil.getCurrentTimeStamp());

        String h5Server = PropertyManager.getProperty("jbb.mgt.org.h5.server.address");

        channel.setChannelUrl(h5Server + "?s=" + channel.getChannelCode());

        channel.setCreator(this.account.getAccountId());
        if (this.account.getOrgId() == Constants.JBB_ORG) {
            int times = PropertyManager.getIntProperty("jbb.mgt.channel.msg.times", 5);
            channel.setMsgTimes(times);
        }
        try {
            channelService.createChannal(channel);
        } catch (DuplicateKeyException e) {
            // 重新尝试一次
            channel.setChannelCode(StringUtil.randomAlphaNum(6));
            channelService.createChannal(channel);
        }
        this.response.channelCode = channel.getChannelCode();
        logger.debug("<createChannel");
    }

    public void getChannels(String searchText, String startDate, String endDate, Integer zhima, Integer userType,
        Boolean includeWool, Boolean includeEmptyMobile, Boolean includeHiddenChannel, Boolean simple,
        Boolean getAdStatistic, String groupName, Boolean getReducePercent, Boolean isReduce, Integer accountId,
        Integer mode) {
        logger.debug(">getChannels");

        if (StringUtil.isEmpty(searchText)) {
            searchText = null;
        }
        Timestamp startTs = Util.parseTimestamp(startDate, this.getTimezone());
        Timestamp endTs = Util.parseTimestamp(endDate, this.getTimezone());
        boolean includeHiddenChannelF = (includeHiddenChannel != null && includeHiddenChannel == true);
        boolean simpleF = simple == null ? false : simple;
        boolean getAdStatisticF = getAdStatistic == null ? false : getAdStatistic;
        boolean isReduceF = isReduce == null ? false : isReduce;
        getReducePercent = null == getReducePercent ? false : getReducePercent;
        accountId = null == accountId || accountId <= 0 ? null : accountId;
        if (!this.checkChannelAndSale(Constants.MGT_CHANNELS_SALE_CHECK)) {
            accountId = this.account.getAccountId();
        }
        if (StringUtil.isEmpty(groupName)) {
            groupName = null;
        } else {
            if (groupName.equals("all")) {
                groupName = null;
            } else if (groupName.equals("undefined")) {
                groupName = "undefined";
            }
        }
        if (simpleF) {
            this.response.channelsSimpleInfo
                = channelService.getChannelNamesAndCodes(this.account.getOrgId(), includeHiddenChannel, groupName);
        } else {

            List<Channel> channels = channelService.getChannels(searchText, this.account.getOrgId(), startTs, endTs,
                zhima, userType, includeWool, includeEmptyMobile, null, includeHiddenChannelF, getAdStatisticF,
                groupName, getReducePercent, isReduceF, accountId, mode);

            if (!CommonUtil.isNullOrEmpty(channels)) {
                this.response.channels = new ArrayList<RsChannel>(channels.size());
                for (Channel channel : channels) {
                    RsChannel rsChannel = new RsChannel(channel);
                    this.response.channels.add(rsChannel);
                }
            }
        }
        logger.debug("<getChannels");
    }

    public void getXjlChannels(String searchText, String startDate, String endDate) {
        logger.debug(">getXjlChannels");
        if (StringUtil.isEmpty(searchText)) {
            searchText = null;
        }
        Timestamp startTs = StringUtil.isEmpty(startDate) ? null : Util.parseTimestamp(startDate, this.getTimezone());

        Timestamp endTs = StringUtil.isEmpty(endDate) ? null : Util.parseTimestamp(endDate, this.getTimezone());
        List<Channel> channels = channelService.getXjlChannels(searchText, this.account.getOrgId(), startTs, endTs);
        if (!CommonUtil.isNullOrEmpty(channels)) {
            this.response.channels = new ArrayList<RsChannel>(channels.size());
            for (Channel channel : channels) {
                RsChannel rsChannel = new RsChannel(channel);
                this.response.channels.add(rsChannel);
            }
        }
        logger.debug("<getXjlChannels");
    }

    public void getChannelByCode(String channelCode, Boolean simple) {
        logger.debug(">getChannelByCode, channelCode=" + channelCode + ", simple=" + simple);
        if (StringUtil.isEmpty(channelCode)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "channelCode");
        }
        Channel channel = channelService.getChannelByCode(channelCode);
        if (channel != null) {
            Organization org = organizationService.getOrganizationById(channel.getAccount().getOrgId(), false);
            this.response.organizationId = org.getOrgId();
            this.response.orgName = org.getName();
            this.response.companyName = org.getCompanyName();
        }

        boolean simpleF = simple != null && simple == true;

        if (!simpleF) {
            RsChannel rs = new RsChannel();
            Account cAccount = null;
            if (this.account == null && null != channel) {
                rs.setChannelCode(channel.getChannelCode());
                rs.setChannelName(channel.getChannelName());
                rs.setChannelUrl(channel.getChannelUrl());
                rs.setServiceQQ(channel.getServiceQQ());
                rs.setServiceWechat(channel.getServiceWechat());
                rs.setCreationDate(Util.getTimeMs(channel.getCreationDate()));
                rs.setQqRequired(channel.isQqRequired());
                rs.setWechatRequired(channel.isWechatRequired());
                rs.setZhimaRequired(channel.isZhimaRequired());
                rs.setIdcardInfoRequired(channel.isIdcardInfoRequired());
                rs.setIdcardBackRequired(channel.isIdcardBackRequired());
                rs.setIdcardRearRequired(channel.isIdcardRearRequired());
                rs.setHeaderRequired(channel.isHeaderRequired());
                rs.setMobileContract1Required(channel.isMobileContract1Required());
                rs.setMobileContract2Required(channel.isMobileContract2Required());
                rs.setMobileServiceInfoRequired(channel.isMobileServiceInfoRequired());
                rs.setReceiveMode(channel.getReceiveMode());
                rs.setHidden(channel.isHidden());
                rs.setTaobaoRequired(channel.isTaobaoRequired());
                rs.setJingdongRequired(channel.isJingdongRequired());
                rs.setGjjRequired(channel.isGjjRequired());
                rs.setSjRequired(channel.isSjRequired());
                rs.setChsiRequired(channel.isChsiRequired());
                rs.setCompanyRequired(channel.isCompanyRequired());
                cAccount = this.coreAccountService.getAccountById(channel.getCreator(), null, false);
                RsAccount rsAccount = new RsAccount(new Account());
                rsAccount.setOrgId(cAccount.getOrgId());
                rs.setCreator(rsAccount);
            } else if (this.account != null && null != channel) {
                // 没有登录的时候不需要验证
                cAccount = this.coreAccountService.getAccountById(channel.getCreator(), null, false);
                rs = new RsChannel(channel);
                rs.setCreator(new RsAccount(cAccount));
            }
            this.response.channel = rs;
        }
        logger.debug("<getChannelByCode");
    }

    public void getRedirectUrl(String channelCode, HttpServletResponse response, UriInfo ui) throws IOException {
        String defaultUrl = PropertyManager.getProperty("jbb.mgt.h5.redirect.url.default");
        MultivaluedMap<String, String> map = ui.getQueryParameters();
        String newUi = "";
        for (String s : map.keySet()) {
            int i = map.get(s).toString().length();
            String code = "channelCode";
            if (!s.equals(code)) {
                newUi += "&" + s + "=" + URLEncoder.encode(map.get(s).toString().substring(1, i - 1), "UTF-8");
            }
        }
        String url = defaultUrl;
        // + "?s=jbbd" + newUi;
        if (StringUtil.isEmpty(channelCode)) {
            response.sendRedirect(url);
            return;
        }
        Channel channel = channelService.getChannelByCode(channelCode);
        if (channel == null) {
            url = defaultUrl;
            // + "?s=jbbd" + newUi;
        }
        // else if (channel.isDelegateEnabled()) {
        // // 如果渠道的直接代理模式打开，则直接跳转组织的代理链接
        // Integer delegateOrgId = channel.getDelegateOrgId();
        // OrganizationDelegateInfo delegateInfo = orgDelegateService.recommandDelegateChannelCode(delegateOrgId);
        // if (delegateInfo == null || delegateInfo.getOrg() == null || delegateInfo.getChannel() == null) {
        // // 如果没有任何组织开代理模式, 则走 重定向链接 模式
        // url = generateRedirectUrl(channel, channelCode, newUi);
        // } else {
        // String delegateChannelCode = delegateInfo.getChannel().getChannelCode();
        // int type = delegateInfo.getOrg().getDelegateH5Type();
        // String delegateUrl = null;
        // if (type == 1) {
        // delegateUrl
        // = delegateOrgId == null ? PropertyManager.getProperty("jbb.mgt.h5.delegate.url.default.1")
        // : PropertyManager.getProperty("jbb.mgt.h5.delegate.url.default.101");
        // } else {
        // delegateUrl
        // = delegateOrgId == null ? PropertyManager.getProperty("jbb.mgt.h5.delegate.url.default.2")
        // : PropertyManager.getProperty("jbb.mgt.h5.delegate.url.default.102");
        // }
        //
        // String theme = null;
        //
        // if (type == 1) {
        // theme = delegateOrgId == null ? PropertyManager.getProperty("jbb.mgt.h5.delegate.theme.default.1")
        // : PropertyManager.getProperty("jbb.mgt.h5.delegate.theme.default.101");
        // } else {
        // theme = delegateOrgId == null ? PropertyManager.getProperty("jbb.mgt.h5.delegate.theme.default.2")
        // : PropertyManager.getProperty("jbb.mgt.h5.delegate.theme.default.102");
        // }
        // url = delegateUrl + "?s=" + channelCode + "&dCode=" + delegateChannelCode + "&theme=" + theme;
        // }
        // }
        else if (StringUtil.isEmpty(channel.getRedirectUrl())) {
            // 如果跳转链接为空，直接返回默认的url
            url = defaultUrl + "?s=" + channelCode + newUi;
        } else {
            url = generateRedirectUrl(channel, channelCode, newUi);
        }
        response.sendRedirect(url);
    }

    private String generateRedirectUrl(Channel channel, String channelCode, String newUi) {
        String rUrl = channel.getRedirectUrl();
        String url = null;
        String theme = channel.getH5Theme();
        if (!isJbbDomain(rUrl)) {
            url = channel.getRedirectUrl();
        } else {
            if (rUrl.indexOf("?") == -1) {
                url = channel.getRedirectUrl() + "?s=" + channelCode;
            } else {
                url = channel.getRedirectUrl() + "&s=" + channelCode;
            }
            // 设置主题样式
            if (!StringUtil.isEmpty(theme)) {
                url += "&theme=" + theme;
            }
            url += newUi;
        }
        return url;
    }

    private boolean isJbbDomain(String url) {
        String[] h5Domains = PropertyManager.getProperties("jbb.mgt.h5.domains");
        for (int i = 0; i < h5Domains.length; i++) {
            if (url.indexOf(h5Domains[i]) != -1) {
                return true;
            }
        }
        return false;
    }

    public void checkChannelPhoneNumberAndPassword(String sourcePhoneNumber, String sourcePassword) {
        logger.debug(">checkChannelPhoneNumberAndPassword(), sourcePhoneNumber=" + sourcePhoneNumber);
        Channel channel = channelService.checkPhoneNumberAndPassword(sourcePhoneNumber, sourcePassword);
        if (channel != null) {
            this.response.channelName = channel.getChannelName();
        }
        logger.debug("<checkChannelPhoneNumberAndPassword(), sourcePhoneNumber=" + sourcePhoneNumber);
    }

    public void updateChannelByCode(String channelCode, Request req) {
        logger.debug(">updateChannelByCode");
        if (StringUtil.isEmpty(channelCode)) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.missing.param", "zh", "channelCode");
        }
        // 组织管理员和渠道所属市场员 可以编辑渠道
        Channel channel = channelService.getChannelByCode(channelCode);

        String h5Server = PropertyManager.getProperty("jbb.mgt.org.h5.server.address");
        Channel channel2 = generateChannel(channel, req);
        // 检查账户是否被渠道占用
        Channel channelByCode = null;
        if (null == channel2 && null != channel.getSourcePhoneNumber()) {
            channelByCode = channelService.getChannelBySourcePhoneNumber(channel.getSourcePhoneNumber().trim());
        }
        if (channelByCode != null) {
            throw new WrongParameterValueException("jbb.mgt.exception.channel.PhonenumberError");
        }
        if (channel == null) {
            // 验证字段
            validateRequest(channel2);

            channel2.setCreator(this.account.getAccountId());
            channel2.setChannelCode(channelCode);
            channel2.setChannelUrl(h5Server + "?s=" + channel2.getChannelCode());
            channelService.createChannal(channel2);
        } else {
            validateOpRight(channel2);
            // 验证字段
            validateRequest(channel2);
            channel.setChannelUrl(h5Server + "?s=" + channel.getChannelCode());
            // 更新
            channelService.updateChannle(channel2);
        }
        logger.debug("<updateChannelByCode");
    }

    public void updateXjlChannelByCode(String channelCode, String sourcePassword) {
        logger.debug(">updateXjlChannelByCode");
        if (StringUtil.isEmpty(channelCode)) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.missing.param", "zh", "channelCode");
        }
        if (StringUtil.isEmpty(sourcePassword)) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.missing.param", "zh", "sourcePassword");
        }
        // 组织管理员和渠道所属市场员 可以编辑渠道
        Channel channel = channelService.getChannelByCode(channelCode);
        if (channel != null) {
            channel.setSourcePassword(PasswordUtil.passwordHash(sourcePassword));
            channelService.updateChannle(channel);
        } else {
            throw new WrongParameterValueException("jbb.mgt.exception.channel.NameOrPwError", "zh", "channelCode");
        }
        logger.debug("<updateXjlChannelByCode");
    }

    public void deleteChannelInfo(String channelCode) {
        logger.debug(">deleteChannelInfo, channelCode=" + channelCode);
        if (StringUtil.isEmpty(channelCode)) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.missing.param", "zh", "channelCode");
        }
        // 组织管理员和渠道所属市场员 可以编辑渠道
        Channel channel = channelService.getChannelByCode(channelCode);
        if (channel == null) {
            throw new MissingParameterException("jbb.error.exception.channelFrozenOrDelete", "zh", "channelCode");
        }
        validateOpRight(channel);

        channelService.deleteChannel(channelCode);
        logger.debug("<deleteChannelInfo");
    }

    public void freezeChannelInfo(String channelCode) {
        logger.debug(">freezeChannelInfo, channelCode=" + channelCode);
        if (StringUtil.isEmpty(channelCode)) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.missing.param", "zh", "channelCode");
        }
        // 组织管理员和渠道所属市场员 可以编辑渠道
        Channel channel = channelService.getChannelByCode(channelCode);
        if (channel == null) {
            throw new MissingParameterException("jbb.error.exception.channelFrozenOrDelete", "zh", "channelCode");
        }
        validateOpRight(channel);

        channelService.frozeChannel(channelCode);
        logger.debug("<freezeChannelInfo");
    }

    public void thawChannelInfo(String channelCode) {
        logger.debug(">thawChannelInfo, channelCode=" + channelCode);
        if (StringUtil.isEmpty(channelCode)) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.missing.param", "zh", "channelCode");
        }
        // 组织管理员和渠道所属市场员 可以编辑渠道
        Channel channel = channelService.getChannelByCode(channelCode);
        if (channel == null) {
            throw new MissingParameterException("jbb.mgt.exception.channel.notFound");
        }
        validateOpRight(channel);

        channelService.thawChannel(channelCode);
        logger.debug("<thawChannelInfo");
    }

    public void selectChannelStatisticS(String channelCode, String startDate, String endDate, Integer zhima,
        Integer userType, Boolean includeWool, Boolean includeEmptyMobile, boolean includeHiddenChannel) {
        logger.debug(
            ">selectChannelStatisticS, channelCode=" + channelCode + "startDate=" + startDate + "endDate=" + endDate);

        if (StringUtil.isEmpty(channelCode)) {
            channelCode = null;
        } else {
            Channel channel = channelService.getChannelByCode(channelCode);
            if (null == channel || channel.getStatus() == 1 || channel.isHidden()) {
                throw new MissingParameterException("jbb.mgt.exception.channel.notFound");
            } else {
                if (!(channel.getAccount().getOrgId() == this.account.getOrgId() || this.account.getOrgId() == 1)) {
                    throw new MissingParameterException("jbb.mgt.exception.notAcrossTheOrganization");
                }
            }
        }

        Timestamp startTs = Util.parseTimestamp(startDate, this.getTimezone());
        Timestamp endTs = Util.parseTimestamp(endDate, this.getTimezone());
        if (zhima == null) {
            zhima = 0;
        }

        List<Channel> channels
            = channelService.getChannels(null, this.account.getOrgId(), startTs, endTs, zhima, userType, includeWool,
                includeEmptyMobile, channelCode, includeHiddenChannel, false, null, false, false, null, null);

        this.response.statistic = new ChannelStatistic();

        if (CommonUtil.isNullOrEmpty(channels)) {
            return;
        }

        Channel channel = channels.get(0);
        this.response.statistic = channel.getStatistic();

        logger.debug("<selectChannelStatisticS");
    }

    public void delegateChannel() {
        logger.debug(">delegateChannel");
        // 检查渠道code 是否存在
        boolean isDelegate = channelService.selectExistsDelegate(this.account.getOrgId());
        if (isDelegate) {
            throw new WrongParameterValueException("jbb.mgt.exception.existsDelegate");
        }
        Channel channel = new Channel();
        String channelCode = StringUtil.randomAlphaNum(6);
        channel.setChannelCode(channelCode);
        channel.setChannelName(Constants.CHANNEL_DELEGATE_NAME);
        String delegateH5Url = PropertyManager.getProperty("jbb.mgt.org.h5.delegate.address");
        channel.setChannelUrl(delegateH5Url + "?s=" + channelCode);
        channel.setCreator(this.account.getAccountId());
        channel.setHidden(true);
        channel.setDelegate(true);
        try {
            channelService.createChannal(channel);
        } catch (DuplicateKeyException e) {
            // 重新尝试一次
            channel.setChannelCode(StringUtil.randomAlphaNum(6));
            channelService.createChannal(channel);
        }
        this.response.channelCode = channel.getChannelCode();
        logger.debug("<delegateChannel");
    }

    public void getChannelGroupName() {
        logger.debug(">delegateChannel");
        this.response.list = channelService.getChannelGroupName();
        logger.debug("<delegateChannel");
    }

    public void getChannelPromotion(String sourcePhoneNumber, String sourcePassword, String startDate, String endDate) {
        logger.debug(">getChannelPromotion");
        if (StringUtil.isEmpty(sourcePhoneNumber)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "sourcePhoneNumber");
        }
        if (StringUtil.isEmpty(sourcePassword)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "sourcePassword");
        }
        Channel channel = channelService.checkPhoneNumberAndPassword(sourcePhoneNumber, sourcePassword);
        Timestamp starts = StringUtil.isEmpty(startDate) ? null : Util.parseTimestamp(startDate, getTimezone());
        Timestamp ends = StringUtil.isEmpty(endDate) ? null : Util.parseTimestamp(endDate, getTimezone());
        this.response.promotion = channelService.getChannelPromotion(channel.getChannelCode(), starts, ends,
            channel.getAccount().getOrgId());
        logger.debug("<getChannelPromotion");
    }

    private void validateOpRight(Channel channel) {
        // if (!(channel.getCreator() == this.account.getAccountId() || this.isOrgAdmin())) {
        // throw new AccessDeniedException("jbb.mgt.exception.accountAccessDenied");
        // }
    }

    private void validateRequest(Channel channel) {
        if (StringUtil.isEmpty(channel.getChannelName())) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "channelName");
        }
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private String channelCode;

        private String channelName;

        private RsChannel channel;

        private List<ChannelSimpleInfo> channelsSimpleInfo;

        private List<RsChannel> channels;

        public List<String> list;

        private String orgName;

        private Integer organizationId;

        private String companyName;

        private ChannelStatistic statistic;

        public ChannelPromotion promotion;

        public Integer getOrganizationId() {
            return organizationId;
        }

        public String getChannelCode() {
            return channelCode;
        }

        public RsChannel getChannel() {
            return channel;
        }

        public List<RsChannel> getChannels() {
            return channels;
        }

        public String getOrgName() {
            return orgName;
        }

        public String getCompanyName() {
            return companyName;
        }

        public ChannelStatistic getStatistic() {
            return statistic;
        }

        public String getChannelName() {
            return channelName;
        }

        public List<ChannelSimpleInfo> getChannelsSimpleInfo() {
            return channelsSimpleInfo;
        }

    }

    private Channel generateChannel(Channel channel, Request req) {
        channel = channel == null ? new Channel() : channel;
        if (!StringUtil.isEmpty(req.channelCode)) {
            channel.setChannelCode(req.channelCode);
        }
        if (null != req && !StringUtil.isEmpty(req.channelName)) {
            channel.setChannelName(req.channelName);
        }
        if (null != req && !StringUtil.isEmpty(req.channelUrl)) {
            channel.setChannelUrl(req.channelUrl);
        }
        if (null != req && !StringUtil.isEmpty(req.serviceQQ)) {
            channel.setServiceQQ(req.serviceQQ);
        }
        if (null != req && !StringUtil.isEmpty(req.serviceWechat)) {
            channel.setServiceWechat(req.serviceWechat);
        }
        if (null != req && null != req.mode) {
            channel.setMode(req.mode);
        }
        if (null != req && null != req.sourcePhoneNumber && !StringUtil.isEmpty(req.sourcePhoneNumber.trim())) {
            channel.setSourcePhoneNumber(req.sourcePhoneNumber.trim());
        }
        if (null != req && !StringUtil.isEmpty(req.sourcePassword)) {
            channel.setSourcePassword(PasswordUtil.passwordHash(req.sourcePassword));
        }
        if (null != req && null != req.qqRequired) {
            channel.setQqRequired(req.qqRequired);
        }
        if (null != req && null != req.wechatRequired) {
            channel.setWechatRequired(req.wechatRequired);
        }
        if (null != req && null != req.zhimaRequired) {
            channel.setZhimaRequired(req.zhimaRequired);
        }
        if (null != req && null != req.idcardInfoRequired) {
            channel.setIdcardInfoRequired(req.idcardInfoRequired);
        }
        if (null != req && null != req.idcardBackRequired) {
            channel.setIdcardBackRequired(req.idcardBackRequired);
        }
        if (null != req && null != req.idcardRearRequired) {
            channel.setIdcardRearRequired(req.idcardRearRequired);
        }
        if (null != req && null != req.headerRequired) {
            channel.setHeaderRequired(req.headerRequired);
        }
        if (null != req && null != req.mobileContract1Required) {
            channel.setMobileContract1Required(req.mobileContract1Required);
        }
        if (null != req && null != req.mobileContract2Required) {
            channel.setMobileContract2Required(req.mobileContract2Required);
        }
        if (null != req && null != req.mobileServiceInfoRequired) {
            channel.setMobileServiceInfoRequired(req.mobileServiceInfoRequired);
        }
        if (null != req && null != req.cpaPrice) {
            channel.setCpaPrice(req.cpaPrice);
        }
        if (null != req && null != req.cpsPrice) {
            channel.setCpsPrice(req.cpsPrice);
        }
        if (null != req && null != req.uvPrice) {
            channel.setUvPrice(req.uvPrice);
        }
        if (null != req && null != req.receiveMode) {
            channel.setReceiveMode(req.receiveMode);
        }
        if (null != req && null != req.hidden) {
            channel.setHidden(req.hidden);
        }
        if (null != req && null != req.taobaoRequired) {
            channel.setTaobaoRequired(req.taobaoRequired);
        }
        if (null != req && null != req.jingdongRequired) {
            channel.setJingdongRequired(req.jingdongRequired);
        }
        if (null != req && null != req.gjjRequired) {
            channel.setGjjRequired(req.gjjRequired);
        }
        if (null != req && null != req.sjRequired) {
            channel.setSjRequired(req.sjRequired);
        }
        if (null != req && null != req.chsiRequired) {
            channel.setChsiRequired(req.chsiRequired);
        }
        if (null != req && null != req.checkWool) {
            channel.setCheckWool(req.checkWool);
        }
        if (null != req && null != req.checkMobile) {
            channel.setCheckMobile(req.checkMobile);
        }
        if (null != req && !StringUtil.isEmpty(req.redirectUrl)) {
            channel.setRedirectUrl(req.redirectUrl);
        }
        if (null != req && null != (req.merchantId)) {
            channel.setMerchantId(req.merchantId);
        }
        if (null != req && null != (req.merchantId2)) {
            channel.setMerchantId2(req.merchantId2);
        }
        if (null != req && null != req.delegateEnabled) {
            channel.setDelegateEnabled(req.delegateEnabled);
        }
        if (null != req && !StringUtil.isEmpty(req.channelAppName)) {
            channel.setChannelAppName(req.channelAppName);
        }
        if (null != req && !StringUtil.isEmpty(req.h5Theme)) {
            channel.setH5Theme(req.h5Theme);
        }
        if (null != req && !StringUtil.isEmpty(req.downloadApp)) {
            channel.setDownloadApp(req.downloadApp);
        }
        if (null != req && null != req.testFlag && null != req.testFlag) {
            channel.setTestFlag(req.testFlag);
        } else if (null != req && null != req.testFlag && null == req.testFlag) {
            channel.setTestFlag(false);
        }
        if (null != req && !StringUtil.isEmpty(req.groupName)) {
            channel.setGroupName(req.groupName);
        }
        if (null != req && null != req.reduceEnable) {
            channel.setReduceEnable(req.reduceEnable);
        }
        if (null != req && null != req.reducePercent) {
            channel.setReducePercent(req.reducePercent);
        } else if (null != req && null != req.reducePercent && req.reducePercent <= 0) {
            channel.setReducePercent(0);
        }
        if (null != req && null != req.deleteMerchantId && req.deleteMerchantId) {
            channel.setMerchantId(null);
        }
        if (null != req && null != req.deleteMerchantId2 && req.deleteMerchantId2) {
            channel.setMerchantId2(null);
        }
        if (null != req && null != req.deleteGroupName && req.deleteGroupName) {
            channel.setGroupName(null);
        }
        if (null != req && null != req.delegateOrgId && req.delegateOrgId > 0) {
            channel.setDelegateOrgId(req.delegateOrgId);
        }
        if (null != req && null != req.deleteOrgId && req.deleteOrgId) {
            channel.setDelegateOrgId(null);
        }

        if (null != req && null != req.companyRequired) {
            channel.setCompanyRequired(req.companyRequired);
        }
        return channel;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String channelCode;
        public String channelName;
        public String channelUrl;
        public String serviceQQ;
        public String serviceWechat;
        public Integer mode;
        public String sourcePhoneNumber;
        public String sourcePassword;
        public Boolean qqRequired;
        public Boolean wechatRequired;
        public Boolean zhimaRequired;
        public Boolean idcardInfoRequired;
        public Boolean idcardBackRequired;
        public Boolean idcardRearRequired;
        public Boolean headerRequired;
        public Boolean mobileContract1Required;
        public Boolean mobileContract2Required;
        public Boolean mobileServiceInfoRequired;
        public Integer cpaPrice;
        public Integer cpsPrice;
        public Integer uvPrice;
        public Integer receiveMode;
        public Boolean hidden;
        public Boolean taobaoRequired;
        public Boolean jingdongRequired;
        public Boolean gjjRequired;
        public Boolean sjRequired;
        public Boolean chsiRequired;
        public Boolean checkWool;
        public Boolean checkMobile;
        public String redirectUrl;
        public Integer merchantId;
        public Integer merchantId2;
        public Boolean delegateEnabled;
        public String channelAppName;
        public String h5Theme;
        public String downloadApp;
        public Boolean testFlag;
        public String groupName;
        public Boolean reduceEnable;
        public Integer reducePercent;
        public Boolean deleteMerchantId;
        public Boolean deleteMerchantId2;
        public Boolean deleteGroupName;
        public Integer delegateOrgId;
        public Boolean deleteOrgId;
        public Boolean companyRequired;
    }

}
