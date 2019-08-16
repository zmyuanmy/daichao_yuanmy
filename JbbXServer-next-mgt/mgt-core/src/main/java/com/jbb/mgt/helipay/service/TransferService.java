package com.jbb.mgt.helipay.service;

import java.io.UnsupportedEncodingException;

import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserCard;
import com.jbb.mgt.core.domain.XjlApplyRecord;

public interface TransferService {

    /**
     * 单笔代付
     * @param accountId 
     */
    void transfer(User user, UserCard userCard, XjlApplyRecord apply, int accountId) throws UnsupportedEncodingException;

    /**
     * 单笔代付查询
     */
    void tranferQuery(String orderId) throws UnsupportedEncodingException;

}
