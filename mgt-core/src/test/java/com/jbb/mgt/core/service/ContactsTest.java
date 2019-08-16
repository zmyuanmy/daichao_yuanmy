package com.jbb.mgt.core.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jbb.mgt.core.domain.UserContact;
import com.jbb.mgt.core.domain.UserContant;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.CommonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class ContactsTest {
    @Autowired
    private UserContantService userContantService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testAccountsService() {
        String jsonStr = "";
        int jbbUserId = 251;
        
        
        JSONArray contacts = JSONArray.parseArray(jsonStr);
        int size = contacts.size();

        List<UserContact> userContacts = new ArrayList<UserContact>(size);
        for (int i = 0; i < contacts.size(); i++) {
            JSONObject contactIns = (JSONObject)contacts.get(i);
            JSONObject contact = (JSONObject)contactIns.get("_objectInstance");
            String displyaName = contact.getString("displayName");
            JSONArray phoneNumbers = contact.getJSONArray("phoneNumbers");
            Set<String> phoneNumberSet = new HashSet<String>();
            UserContact uc = new UserContact(displyaName, phoneNumberSet);
            for (int j = 0; j < phoneNumbers.size(); j++) {
                JSONObject phone = (JSONObject)phoneNumbers.get(j);
                phoneNumberSet.add(phone.getString("value"));
            }
            userContacts.add(uc);
        }
        List<UserContant> mgtContacts = new ArrayList<UserContant>();

        for (int i = 0; i < userContacts.size(); i++) {
            UserContact uc = userContacts.get(i);
            if (CommonUtil.isNullOrEmpty(uc.getPhoneNumbers())) {
                continue;
            }
            if (uc.getDisplayName() == null || uc.getDisplayName().isEmpty()) {
                uc.setDisplayName("");
            }
            uc.getPhoneNumbers().forEach(phoneNumber -> {
                mgtContacts.add(new UserContant(jbbUserId, phoneNumber, uc.getDisplayName()));
            });
        }

        userContantService.insertOrUpdateUserContant(mgtContacts);

    }
}
