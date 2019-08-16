package com.jbb.server.core.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.dao.AccountDao;
import com.jbb.server.core.dao.UserVerifyDao;
import com.jbb.server.core.domain.Iou;
import com.jbb.server.core.domain.MgtIou;
import com.jbb.server.core.domain.User;
import com.jbb.server.core.domain.UserContact;
import com.jbb.server.core.domain.UserProperty;
import com.jbb.server.core.domain.UserSychronize;
import com.jbb.server.core.service.AccountService;
import com.jbb.server.core.service.MgtService;

/**
 * Created by inspironsun on 2018/5/30
 */
@Service("MgtService")
public class MgtServiceImpl implements MgtService {

    private static Logger logger = LoggerFactory.getLogger(MgtServiceImpl.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserVerifyDao userVerifyDao;

    @Autowired
    private AccountDao accountDao;

    private static DefaultTransactionDefinition NEW_TX_DEFINITION =
        new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private PlatformTransactionManager txManager;

    @Override
    public void updateMgtUserInfo(User user) {
        /**
         * 1.验证用户是mgt的用户 2.检查是否已经同步过 3.筛选list中已经实名认证的部分 4.check 剩下的list中，每个对象的身份证号跟姓名是否完全一致
         * 5.完全一致，选择时间最新的对象，更新数据入库，修改认证信息
         */
        logger.debug(">updateMgtUserInfo, userId = " + user.getUserId());

        boolean flag = false;
        // 检查 来源是否来自己mgt
        if (!user.getSourceId().equals("mgt")) {
            return; // 不做任何处理，非mgt来的数据
        }
        // 检查是否同步过
        UserProperty property = accountDao.selectUserPropertyByUserIdAndName(user.getUserId(), "mgt_sysch_data_time");
        if (property != null) {
            // 记录日志, return
            logger.info("user had been sychornized , userId=" + user.getUserId());
            return;
        }
        // 获取数据来源
        List<UserSychronize> userList = this.getMgtUserList(user.getPhoneNumber());
        if (userList == null || userList.size() == 0) {
            logger.info("Mgt userList is undefined");
            return;
        }
        // 数据判定逻辑
        this.getUserUpadteInfo(user, userList);
        // 同步更新数据
        this.updateUser(user);
        logger.debug("<updateMgtUserInfo");
    }

    @Override
    public boolean creatMgtIou(Iou iou) {

        // 检查用户在mgt平台中是否存在 check property accountId
        UserProperty property = accountService.searchUserPropertiesByUserIdAndName(iou.getLenderId(), "accountId");
        if (property == null) {
            logger.info("mgt user not found,creatMgtIou stop");
            return false;
        }

        MgtIou mgtIou = new MgtIou();
        mgtIou.setAnnualRate((int)(iou.getAnnualRate() * 100));
        mgtIou.setBorrowerId(iou.getBorrowerId());
        mgtIou.setLenderId(iou.getLenderId());
        mgtIou.setBorrowingAmount((int)(iou.getBorrowingAmount() * 100));
        mgtIou.setBorrowingDate(iou.getBorrowingDateStr());
        mgtIou.setIouCode(iou.getIouCode());
        mgtIou.setRepaymentDate(iou.getRepaymentDateStr());
        mgtIou.setStatus(iou.getStatus());

        // 打mgt借条
        logger.info(">creatMgtIou, integrate jbb create mgt iou start");
        boolean flag = false;
        String appkey = PropertyManager.getProperty("jbb.integrate.user.appkey");
        String url = PropertyManager.getProperty("jbb.integrate.user.url") + "/integrate/iou";
        String[][] heads = {{"API_KEY", appkey}};
        JSONObject jsonObject = (JSONObject)JSON.toJSON(mgtIou);
        try {
            HttpUtil.HttpResponse httpResponse = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url,
                HttpUtil.CONTENT_TYPE_JSON, jsonObject.toString(), heads);
            if (httpResponse.getResponseCode() == HttpUtil.STATUS_OK) {
                byte[] byteArray = httpResponse.getResponseBody();
                String strRead = new String(byteArray, "utf-8");
                JSONObject object = JSONObject.parseObject(strRead);
                if (object.getIntValue("resultCode") == 0) {
                    flag = true;
                }
            }
        } catch (IOException e) {
            logger.error("integrate jbb create mgt iou error", e);
            e.printStackTrace();
        }
        logger.info(">creatMgtIou, integrate jbb create mgt iou end");
        return flag;
    }

    @Override
    public boolean updateMgtIou(String iouCode, String extentionDate, int status) {
        // 更新mgt借条状态
        logger.info(">updateMgtIou, integrate jbb update mgt iou start");
        boolean flag = false;
        String appkey = PropertyManager.getProperty("jbb.integrate.user.appkey");
        String url = PropertyManager.getProperty("jbb.integrate.user.url") + "/integrate/iou";
        String[][] heads = {{"API_KEY", appkey}};

        JSONObject o = new JSONObject();
        o.put("iouCode", iouCode);
        o.put("status", status);
        if (!StringUtil.isEmpty(extentionDate)) {
            o.put("extentionDate", extentionDate);
        }
        String body = o.toJSONString();
        try {
            HttpUtil.HttpResponse httpResponse =
                HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_PUT, url, HttpUtil.CONTENT_TYPE_JSON, body, heads);
            if (httpResponse.getResponseCode() == HttpUtil.STATUS_OK) {
                byte[] byteArray = httpResponse.getResponseBody();
                String strRead = new String(byteArray, "utf-8");
                JSONObject object = JSONObject.parseObject(strRead);
                if (object.getIntValue("resultCode") == 0) {
                    flag = true;
                }
            }
        } catch (IOException e) {
            logger.error("integrate jbb update mgt iou error", e);
            e.printStackTrace();
        }
        logger.info("<updateMgtIou, integrate jbb update mgt iou end");
        return flag;
    }

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

    private List<UserSychronize> getMgtUserList(String phonenumber) {
        logger.info(">getMgtUserList, integrate jbb getmgtuserinfo start");
        String data = "";
        String url =
            PropertyManager.getProperty("jbb.integrate.user.url") + "/integrate/user?phoneNumber=" + phonenumber;
        HttpUtil.HttpResponse response = null;
        String[][] requestProperties = {{"API_KEY", PropertyManager.getProperty("jbb.integrate.user.appkey")}};
        try {
            response =
                HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url, HttpUtil.CONTENT_TYPE_JSON, data, requestProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String returnResult = "";
        List<UserSychronize> userList = new ArrayList<UserSychronize>();
        if (response.getResponseCode() == HttpUtil.STATUS_OK) {
            byte[] byteArray = response.getResponseBody();
            try {
                returnResult = new String(byteArray, "UTF-8");
                JSONObject jsonObject = JSONObject.parseObject(returnResult);
                String list = jsonObject.getString("list");
                userList = JSONObject.parseArray(list, UserSychronize.class);
            } catch (UnsupportedEncodingException e) {
                logger.error("integrate jbb getmgtuserinfo error", e);
                e.printStackTrace();
            }
        }
        logger.info("<getMgtUserList, integrate jbb getmgtuserinfo end");
        return userList;
    }

    private void getUserUpadteInfo(User user, List<UserSychronize> userInfoList) {

        List<UserSychronize> userListNew = new ArrayList<UserSychronize>();
        for (UserSychronize usernew : userInfoList) {
            if (usernew.isRealnameVerified()) {
                userListNew.add(usernew);
            }
        }

        if (userListNew.size() == 0) {
            logger.info("Mgt userinfo check fail");
            return;
        }

        String userName = userListNew.get(0).getUserName();
        String idCard = userListNew.get(0).getIdCard();
        boolean checkflag = true;
        for (UserSychronize userSychronize : userListNew) {
            if (!userSychronize.getUserName().equals(userName) || !userSychronize.getIdCard().equals(idCard)) {
                checkflag = false;
                break;
            }
        }

        if (checkflag == false) {
            logger.info("Mgt userinfo check fail");
            return;
        }

        UserSychronize userfinal = userListNew.get(0);
        userName = userfinal.getUserName();
        idCard = userfinal.getIdCard();
        String qq = userfinal.getQq();
        String wechat = userfinal.getWechat();
        user.setQqNumber(qq);
        user.setIdCardNo(idCard);
        user.setUserName(userName);
        user.setWechat(wechat);
    }

    private void updateUser(User user) {
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            accountService.updateUser(user);
            // 增加同步状态时间
            accountService.saveUserProperty(user.getUserId(), "mgt_sysch_data_time",
                DateUtil.getCurrentTimeStamp().toString());
            // 修改认证状态
            userVerifyDao.saveUserVerifyResult(user.getUserId(), Constants.VERIFY_TYPE_REALNAME, 1, true);
            userVerifyDao.saveUserVerifyResult(user.getUserId(), Constants.VERIFY_TYPE_REALNAME, 2, true);
            userVerifyDao.saveUserVerifyResult(user.getUserId(), Constants.VERIFY_TYPE_MOBILE, 1, true);
            txManager.commit(txStatus);
            txStatus = null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("userSychronize error , error = " + e.getMessage());
        } finally {
            // roll back not committed transaction
            rollbackTransaction(txStatus);
        }
    }

    @Override
    public void syncUserContacts(int userId, List<UserContact> contacts) {
        logger.info(">syncUserContacts(), userId = " + userId + " , contacts size =" + contacts.size());
        String data = "{\"contacts\": "+JSONObject.toJSONString(contacts)+"}";
        String url =
            PropertyManager.getProperty("jbb.integrate.user.url") + "/integrate/contacts?jbbUserId=" + userId;
        HttpUtil.HttpResponse response = null;
        String[][] requestProperties = {{"API_KEY", PropertyManager.getProperty("jbb.integrate.user.appkey")}};
        try {
            response = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url, HttpUtil.CONTENT_TYPE_JSON,
                data, requestProperties);
            logger.debug(new String(response.getResponseBody()));
        } catch (IOException e) {
            logger.error("syncUserContacts error , error = " + e.getMessage());
        }

        logger.info("<syncUserContacts()");

    }

}
