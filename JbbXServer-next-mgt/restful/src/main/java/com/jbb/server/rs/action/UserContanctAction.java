package com.jbb.server.rs.action;

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

import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.core.domain.UserContact;
import com.jbb.server.core.service.UserContactService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service(UserContanctAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserContanctAction extends BasicAction {

    private static Logger logger = LoggerFactory.getLogger(UserContanctAction.class);
    public static final String ACTION_NAME = "UserConstanctAction";

    @Autowired
    private UserContactService userContanctService;

    public void processContacts(JSONObject req) {
        logger.debug(">processContacts");
        JSONArray contacts = req.getJSONArray("contacts");
        if (contacts == null) {
            throw new WrongParameterValueException("jbb.error.exception.contacts.empty");
        }
        int size = contacts.size();
        if (size == 0) {
            throw new WrongParameterValueException("jbb.error.exception.contacts.empty");
        }
        logger.debug("length =  " + size);

        List<UserContact> userContacts = new ArrayList<UserContact>(size);
        for (int i = 0; i < contacts.size(); i++) {
            try {
                JSONObject contactIns = (JSONObject)contacts.get(i);
                JSONObject contact = (JSONObject)contactIns.get("_objectInstance");
                String displayName = contact.getString("displayName");
                if (displayName == null || "null".equals(displayName)) {
                    JSONObject name = (JSONObject)contact.get("name");
                    displayName = name.getString("formatted");
                }
                JSONArray phoneNumbers = contact.getJSONArray("phoneNumbers");
                Set<String> phoneNumberSet = new HashSet<String>();
                displayName = displayName.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
                //长度不超过50
                if(displayName.length() > 50){
                    displayName = displayName.substring(0, 50);
                }
                UserContact uc = new UserContact(displayName, phoneNumberSet);
                for (int j = 0; j < phoneNumbers.size(); j++) {
                    JSONObject phone = (JSONObject)phoneNumbers.get(j);
                    String phoneNubmer = phone.getString("value");
                    if (phoneNubmer == null || "null".equals(phoneNubmer)) {
                        continue;
                    }
                    phoneNubmer = phoneNubmer.replaceAll("[^0-9]","");
                    //长度不超过20
                    if(phoneNubmer.length() > 20){
                        phoneNubmer = phoneNubmer.substring(0, 20);
                    }
                    phoneNumberSet.add(phoneNubmer);
                }
                userContacts.add(uc);
            } catch (Exception e) {
                // nothing to do
            }
        }
        userContanctService.processUserContacts(this.user.getUserId(), userContacts, req);
        logger.debug("<processContacts()");
    }

}
