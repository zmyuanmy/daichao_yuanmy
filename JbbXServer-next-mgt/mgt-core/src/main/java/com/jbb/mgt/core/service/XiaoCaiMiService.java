package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.JointUserLoginData;

public interface XiaoCaiMiService {

    // 检查渠道方传过来的手机号是否在库里
    boolean checkPhoneNumberMd5ExistUser(String mobilePhone);

    //联登
    JointUserLoginData jointUserLogin(String entryKey, String entryData) throws Exception;

}
