package com.jbb.server.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.core.dao.AccountDao;
import com.jbb.server.core.domain.UserContact;
import com.jbb.server.core.service.AliyunService;
import com.jbb.server.core.service.MgtService;
import com.jbb.server.core.service.UserContactService;

import net.sf.json.JSONObject;

@Service("UserContactService")
public class UserContactServiceImpl implements UserContactService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AliyunService aliyunService;

    @Autowired
    MgtService mgtService;

    @Override
    public void processUserContacts(int userId, List<UserContact> contacts, JSONObject req) {

        if (CommonUtil.isNullOrEmpty(contacts)) {
            return;
        }

        accountDao.updateUserHasContacts(userId);
        String bucket = PropertyManager.getProperty("jbb.aliyu.oss.bucket.contacts");
        aliyunService.putObject(bucket, String.valueOf(userId), req.toString());

        // 请求MGT服务器，同步至MGT DB
        mgtService.syncUserContacts(userId, contacts);

    }

}
