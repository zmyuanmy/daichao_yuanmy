package com.jbb.server.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.server.common.Constants;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.HttpUtil.HttpResponse;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.dao.AccountDao;
import com.jbb.server.core.dao.UserEventLogDao;
import com.jbb.server.core.domain.User;
import com.jbb.server.core.domain.UserProperty;
import com.jbb.server.core.service.PushUserService;

@Service("cyPushUserService")
public class CYPushUserServiceIMpl implements PushUserService {

    private static Logger logger = LoggerFactory.getLogger(PushUserService.class);

    @Autowired
    private UserEventLogDao userEventLogDao;

    @Autowired
    private AccountDao accountDao;

    @Override
    public void postUserData(User user, String serverPath) {
        String sourceId = user.getSourceId();

        logger.debug(">postUserData");

        String url = serverPath + "?Mobile=" + user.getPhoneNumber();

        if (!StringUtil.isEmpty(user.getIdCardNo())) {
            url += "&IdentityCard=" + HttpUtil.encodeURLParam(user.getIdCardNo());
        }
        if (!StringUtil.isEmpty(user.getWechat())) {
            url += "&WebChat=" + HttpUtil.encodeURLParam(user.getWechat());
        }
        if (!StringUtil.isEmpty(user.getUserName())) {
            url += "&Name=" + HttpUtil.encodeURLParam(user.getUserName());
        }
        UserProperty pSesame =
            accountDao.selectUserPropertyByUserIdAndName(user.getUserId(), Constants.SESAME_CREDIT_SCORE);
        String sesame = pSesame != null ? pSesame.getValue() : null;
        if (!StringUtil.isEmpty(sesame)) {
            url += "&SesameScore=" + sesame;
        }
        try {

            logger.debug("url=" + url);

            HttpResponse response =
                HttpUtil.sendDataViaHTTPRepeat(HttpUtil.HTTP_POST, url, HttpUtil.CONTENT_TYPE_JSON, null, null,"cyPUshData");
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                userEventLogDao.insertEventLog(user.getUserId(), sourceId, "CY", "POST", "SUCCESS", null, null,
                    DateUtil.getCurrentTimeStamp());
                logger.debug("response = " + new String(response.getResponseBody()));
            } else {
                userEventLogDao.insertEventLog(user.getUserId(), sourceId, "CY", "POST", "ERROR", null, null,
                    DateUtil.getCurrentTimeStamp());
                logger.debug("response code = " + response.getResponseCode());
            }

        } catch (Exception e) {
            logger.error("postUserData error", e);
        }
        logger.debug("<postUserData");
    }

}
