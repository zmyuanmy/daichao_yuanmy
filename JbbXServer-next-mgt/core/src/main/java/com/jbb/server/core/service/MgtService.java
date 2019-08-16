package com.jbb.server.core.service;

import java.util.List;

import com.jbb.server.core.domain.Iou;
import com.jbb.server.core.domain.User;
import com.jbb.server.core.domain.UserContact;

/**
 * Created by inspironsun on 2018/5/30
 */
public interface MgtService {

    void updateMgtUserInfo(User userId);
    
    void syncUserContacts(int userId, List<UserContact> contacts);

    boolean creatMgtIou(Iou iou);

    boolean updateMgtIou(String iouCode,String extentionDate,int status);
}
