package com.jbb.server.core.service;

import java.util.List;

import com.jbb.server.core.domain.UserContact;

import net.sf.json.JSONObject;

public interface UserContactService {

    void processUserContacts(int userId, List<UserContact> contacts, JSONObject req);

}
