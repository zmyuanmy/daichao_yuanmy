package com.jbb.mgt.rs.action.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserContact;
import com.jbb.mgt.core.domain.UserContant;
import com.jbb.mgt.core.service.AliyunService;
import com.jbb.mgt.core.service.UserContantService;
import com.jbb.mgt.core.service.UserService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.PhoneNumberUtil;
import com.jbb.server.common.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service(UserContanctAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserContanctAction extends BasicAction {

    public static final String ACTION_NAME = "UserContanctAction";

    private static Logger logger = LoggerFactory.getLogger(UserContanctAction.class);

    @Autowired
    private UserContantService userContantService;
    @Autowired
    private AliyunService aliyunService;
    @Autowired
    private UserService userService;

    public void processContacts(JSONObject req) {
        logger.debug(">processContacts");
        if (req == null) {
            throw new WrongParameterValueException("jbb.error.exception.contacts.empty");
        }
        JSONArray contacts = req.getJSONArray("contacts");
        if (contacts == null) {
            throw new WrongParameterValueException("jbb.error.exception.contacts.empty");
        }
        int size = contacts.size();
        if (size == 0) {
            throw new WrongParameterValueException("jbb.error.exception.contacts.empty");
        }
        logger.debug("length =  " + size);
        if (CommonUtil.isNullOrEmpty(contacts)) {
            return;
        }
        List<UserContact> userContacts = new ArrayList<UserContact>(size);
        for (int i = 0; i < contacts.size(); i++) {
            JSONObject contactIns = (JSONObject)contacts.get(i);
            JSONObject contact = (JSONObject)contactIns.get("_objectInstance");

            String displyaName = contact.getString("displayName");
            JSONArray phoneNumbers = contact.optJSONArray("phoneNumbers");
            if (phoneNumbers != null) {
                Set<String> phoneNumberSet = new HashSet<String>();
                UserContact uc = new UserContact(displyaName, phoneNumberSet);

                for (int j = 0; j < phoneNumbers.size(); j++) {
                    JSONObject phone = (JSONObject)phoneNumbers.get(j);
                    phoneNumberSet.add(phone.getString("value"));
                }

                userContacts.add(uc);
            }
        }
        // 上传阿里云
        String bucket = PropertyManager.getProperty("jbb.aliyu.oss.bucket.contacts");
        aliyunService.putObject(bucket, String.valueOf(this.user.getJbbUserId()), req.toString());
        List<UserContant> mgtContacts = new ArrayList<UserContant>();
        for (int i = 0; i < userContacts.size(); i++) {
            UserContact uc = userContacts.get(i);
            if (CommonUtil.isNullOrEmpty(uc.getPhoneNumbers())) {
                continue;
            }
            if (StringUtil.isEmpty(uc.getDisplayName()) || uc.getDisplayName().equals("null")) {
                uc.setDisplayName("");
            }
            uc.getPhoneNumbers().forEach(phoneNumber -> {
                String phoneStr = PhoneNumberUtil.trimTelNum(phoneNumber);
                mgtContacts.add(new UserContant(this.user.getJbbUserId(),
                    phoneStr.length() > 20 ? phoneStr.substring(0, 20) : phoneStr,
                    uc.getDisplayName().replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "")));
            });
        }
        // 更改状态
        userContantService.insertOrUpdateUserContant(mgtContacts);
        User user = userService.selectUserById(this.user.getUserId());
        if (!user.isHasContacts()) {
            user.setHasContacts(true);
            userService.updateUser(user);
        }
        logger.debug("<processContacts()");
    }

}
