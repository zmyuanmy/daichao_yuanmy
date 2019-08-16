package com.jbb.mgt.rs.action.xiaoCaiMi;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.ctc.wstx.util.DataUtil;
import com.jbb.mgt.core.dao.MessageCodeDao;
import com.jbb.mgt.core.domain.*;
import com.jbb.mgt.core.service.*;
import com.jbb.mgt.rs.action.integrate.SyncJbbUserIdThread;
import com.jbb.mgt.server.core.util.AESUtil;
import com.jbb.mgt.server.core.util.RSAUtil;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.MD5;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.server.common.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

@Service(XiaoCaiMiAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
public class XiaoCaiMiAction extends BasicAction {
    public static final String ACTION_NAME = "XiaoCaiMiAction";

    private static final String PLATFORM = "api";
    private static final int USERTYPE = 2;

    private static Logger logger = LoggerFactory.getLogger(XiaoCaiMiAction.class);

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private XiaoCaiMiService xiaoCaiMiService;

    @Autowired
    @Qualifier("UserService")
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private AliyunService aliyunService;

    @Autowired
    private UserApplyRecordService userApplyRecordService;

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private OrganizationUserService organizationUserService;

    @Autowired
    private UserLoginLogService userLoginLogService;

    @Autowired
    private OrgAppChannelUserService orgAppChannelUserService;

   

    @Autowired
    private PhoneBlacklistService phoneBlacklistService;

    @Autowired
    private UserEventLogService userEventLogService;

    private void rollbackTransaction(TransactionStatus txStatus) {
        if (txStatus == null) {
            return;
        }

        try {
            txManager.rollback(txStatus);
        } catch (Exception er) {
            logger.warn("Cannot rollback transaction", er);
        }
    }

    public JSONObject checkChannelUserMobilePhone(HttpServletRequest request, HttpServletResponse response) {
        log.debug(">checkChannelUserMobilePhone() request{}" + request);
        int code = 200;
        int result = 0;
        String msg = "请求成功";

        try {
            JSONObject json = getJson(request);
            String mobilePhone = !json.containsKey("mobilePhone") ? "" : json.getString("mobilePhone");
            String channelCode = !json.containsKey("channelCode") ? "" : json.getString("channelCode");
            userEventLogService.insertEventLog(null, channelCode, mobilePhone, "index", "init", "",
                String.valueOf(USERTYPE), this.getRemoteAddress(), DateUtil.getCurrentTimeStamp());
            log.debug(">checkChannelUserMobilePhone() json{}, mobilePhone{},channelCode{}" + json + "," + mobilePhone
                + "," + channelCode);
            // 参数异常直接返回
            if (StringUtil.isEmpty(channelCode)) {
                log.debug(">checkChannelUserMobilePhone() req{} channelCode is null");
                code = 301;
                result = -1;
                msg = "渠道不能为空";
            } else if (StringUtil.isEmpty(mobilePhone) || mobilePhone.length() != 32) {
                log.debug(">checkChannelUserMobilePhone() req{} is NULL");
                code = 301;
                result = -1;
                msg = "手机号不能为空并且需要MD5加密";
            } else {
                Channel channel = channelService.getChannelByCode(channelCode);
                if (channel == null) {
                    throw new Exception("渠道不存在");
                }
                if (channel.getStatus() == 1 || channel.getStatus() == 2) {
                    throw new Exception("请更换正确的渠道注册");
                }
                boolean flag = xiaoCaiMiService.checkPhoneNumberMd5ExistUser(mobilePhone);
                result = flag ? result : 1;
            }
        } catch (Exception e) {
            log.info(">checkChannelUserMobilePhone() error" + e.toString());
            code = 301;
            result = -1;
            msg = e.getMessage();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("msg", msg);
        map.put("result", result);
        JSONObject json = JSONObject.fromObject(map);
        log.debug("<checkChannelUserMobilePhone() request{}" + request);
        return json;

    }

    private JSONObject getJson(HttpServletRequest request) throws IOException {
        InputStreamReader reader = new InputStreamReader(request.getInputStream(), "UTF-8");
        char[] buff = new char[1024];
        int length = 0;
        String x = null;
        while ((length = reader.read(buff)) != -1) {
            x = new String(buff, 0, length);
        }
        JSONObject json = JSONObject.fromObject(x);
        return json;
    }

    public JSONObject jointUserLogin(@Context HttpServletRequest req) {
        log.debug(">jointUserLogin() req{}" + req);
        int code = 200;
        String msg = "请求成功";
        JointUserLoginData data = new JointUserLoginData();
        String publicEncryptStr = "";
        String encryptAESStr = "";
        try {
            JSONObject json = getJson(req);
            // 参数异常直接返回
            if (!json.containsKey("entryKey") || !json.containsKey("entryData")
                || StringUtil.isEmpty(json.getString("entryKey")) || StringUtil.isEmpty(json.getString("entryData"))) {
                log.debug(">jointUserLogin() req{} error");
                code = 301;
                msg = "entryKey or entryData不能为空";
                data = data;
            } else {
                // 调用联登
                log.debug(">checkChannelUserMobilePhone() json{}, entryKey{},entryData{}" + json + ","
                    + json.getString("entryKey") + "," + json.getString("entryData"));
                data = xiaoCaiMiService.jointUserLogin(json.getString("entryKey"), json.getString("entryData"));

                log.debug(">checkChannelUserMobilePhone() data{}" + data.getCode() + "," + data.getChannelCode() + ","
                    + data.getPhone());
                // 未注册时注册 并且返回H5链接
                if (data.getCode() == 305) {
                    Channel channel = channelService.getChannelByCode(data.getChannelCode());
                    if (channel == null) {
                        throw new Exception("渠道不存在");
                    }
                    if (channel.getStatus() == 1 || channel.getStatus() == 2) {
                        throw new Exception("请更换正确的渠道注册");
                    }
                    // 在库里创建相应的用户记录
                    this.insertUser(data.getPhone(), data.getChannelCode(), channel);

                    String publicKeyStr = PropertyManager.getProperty("jbb.pf.joint.user.login.RSA.publicKeyStr");
                    String h5Url = PropertyManager.getProperty("jbb.pf.joint.user.login.h5Url");

                    // 将Base64编码后的公钥转换成PublicKey对象
                    PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
                    // 生成AES秘钥，并Base64编码
                    String aesKeyStr = AESUtil.genKeyAES();

                    // 用公钥加密AES秘钥
                    byte[] publicEncrypt = RSAUtil.publicEncrypt(aesKeyStr.getBytes(), publicKey);
                    // 公钥加密AES秘钥后的内容Base64编码
                    publicEncryptStr = RSAUtil.byte2Base64(publicEncrypt);

                    // 将Base64编码后的AES秘钥转换成SecretKey对象
                    SecretKey aesKey = AESUtil.loadKeyAES(aesKeyStr);
                    // 用AES秘钥加密实际的内容
                    byte[] encryptAES = AESUtil.encryptAES(h5Url.getBytes(), aesKey);
                    // AES秘钥加密后的内容Base64编码
                    encryptAESStr = AESUtil.byte2Base64(encryptAES);
                }
            }
        } catch (Exception e) {
            log.info(">jointUserLogin() error" + e.toString());
            msg = e.getMessage();
            code = 301;
        }
        code = data.getCode() == 302 && data.getCode() != 305 ? data.getCode() : code;
        msg = data.getCode() == 302 ? "已注册" : msg;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("msg", msg);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("entryKey", publicEncryptStr);
        dataMap.put("entrydata", encryptAESStr);
        map.put("data", dataMap);
        JSONObject json = JSONObject.fromObject(map);
        log.debug("<jointUserLogin() req{}" + req);
        return json;
    }

    private void insertUser(String phoneNumber, String channelCode, Channel channel) {
        logger.debug(">jointUserLogin insertUser()");

        phoneBlacklistService.checkPhoneNumberExist(phoneNumber);
        User user = this.userService.selectUser(null, phoneNumber);

        Account creator = this.coreAccountService.getAccountById(channel.getCreator(), null, false);
        int orgId = channel.getAccount().getOrgId();
        boolean isNewUser = user == null ? true : false;
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            if (user == null) {
                user = new User();
                user.setPhoneNumber(phoneNumber);
                user.setCreationDate(DateUtil.getCurrentTimeStamp());
                user.setIpAddress(this.getRemoteAddress());
                user.setPlatform(PLATFORM);
                user.setFirstChannelCode(channel.getChannelCode());
                IPAddressInfo ipAddressInfo = aliyunService.getIPAddressInfo(this.getRemoteAddress());
                if (ipAddressInfo != null) {
                    user.setIpArea(ipAddressInfo.getIpArea());
                }
                // 插入用户
                this.userService.insertUser(user);
            }
            saveToOrgAndApplyRecord(user, creator.getOrgId(), null, channelCode, USERTYPE, false);
            // 更新jbbId
            new SyncJbbUserIdThread(user).start();

            // 创建用户成功
            log.info("jointUserLogin insertUser() success userId{},channelCode{},platform{},userType{}"
                + user.getUserId() + "," + channelCode + "," + PLATFORM + "," + USERTYPE);

            if (user.getUserId() != 0) {
                // 登录获取userId后 记录日志
                userEventLogService.insertEventLog(user.getUserId(), channelCode,
                    MD5.MD5Encode(phoneNumber).toUpperCase(), "index", "submit", "", String.valueOf(USERTYPE),
                    this.getRemoteAddress(), DateUtil.getCurrentTimeStamp());
            }

            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            rollbackTransaction(txStatus);
        }
        // 创建用户Key
        createData(user, orgId, isNewUser, false, Constants.ONE_DAY_MILLIS, 0, channel, PLATFORM, USERTYPE);
        logger.debug("<jointUserLogin insertUser()");
    }

    private void saveToOrgAndApplyRecord(User user, int orgId, Integer initAccountId, String channelCode,
        Integer userType, boolean jbbFlag) {

        int sUserType = (userType == null ? 1 : userType);
        // 插入组织
        OrganizationUser orgUser
            = new OrganizationUser(orgId, user.getUserId(), orgId, sUserType, channelCode, jbbFlag);
        organizationUserService.createOrganizationUser(orgUser);
        // 插入申请
        this.insertApplyRecord(user, orgId, initAccountId, channelCode, sUserType);

    }

    private void insertApplyRecord(User user, int orgId, Integer initAccountId, String channelCode, int userType) {

        // 从H5链接进来,组织存在,再把这个这个用户插入到mgt_user_apply_records
        if (orgId > 0) {
            UserApplyRecord userApplyRecord = new UserApplyRecord();
            userApplyRecord.setUserId(user.getUserId());
            userApplyRecord.setOrgId(orgId);
            userApplyRecord.setCreationDate(DateUtil.getCurrentTimeStamp());
            userApplyRecord.setStatus(1);
            userApplyRecord.setsUserType(userType);
            userApplyRecord.setsChannelCode(channelCode);
            userApplyRecord.setsOrgId(orgId);

            // initAccountId判断是否从电销,中介链接过来
            if (initAccountId != null && initAccountId > 0) {

                // 检查这份审核人是否存在,是否与渠道创建人为一个组织
                Account account = coreAccountService.getAccountById(initAccountId, null, false);
                if (account != null && account.getOrgId() == orgId) {
                    userApplyRecord.setAssignAccId(initAccountId);
                    userApplyRecord.setAssingDate(DateUtil.getCurrentTimeStamp());
                    userApplyRecord.setInitAccId(initAccountId);
                    userApplyRecord.setStatus(2);
                } else {
                    throw new WrongParameterValueException("jbb.mgt.exception.apply.initOrOrgIdError");
                }
            } else {

                // Start 获取组织自动分配设置，并实施自动分配
                Account acc
                    = this.coreAccountService.getOrgAutoAssignAccount(orgId, Constants.TYPE_USER_AUTO_SETTING_SELF);
                if (acc != null) {
                    userApplyRecord.setInitAccId(acc.getAccountId());
                    userApplyRecord.setAssingDate(DateUtil.getCurrentTimeStamp());
                    userApplyRecord.setStatus(2);
                }

                // End

            }
            userApplyRecordService.createUserApplyRecord(userApplyRecord);
        }
    }

    private void createData(User user, int orgId, boolean isNew, boolean hidden, long expiry, int applicationId,
        Channel channel, String platform, Integer userType) {
        int userId = user.getUserId();
        String channelCode = channel.getChannelCode();

        // 插入日志Start
        UserLoginLog log = new UserLoginLog(user.getUserId(), channelCode, applicationId, platform, userType,
            this.getRemoteAddress());
        userLoginLogService.insertUserLoginLog(log);
        
        // 插入登录日志END

        // 计算渠道结算量 start
        orgAppChannelUserService.saveOrgAppChannelUser(user, orgId, isNew, hidden, channel, applicationId, userType,
            this.getRemoteAddress());
        // end

        UserKey userKey = user.getKey();
        boolean createNewKey = (userKey == null);

        if (!createNewKey) {
            Timestamp keyExpiry = userKey.getExpiry();
            createNewKey
                = (keyExpiry == null) || (keyExpiry.getTime() < System.currentTimeMillis() + Constants.ONE_DAY_MILLIS);
        }

        if (createNewKey) {
            // create new user key
            userKey = userService.createUserKey(userId, applicationId, expiry, true);
        }
    }

}
