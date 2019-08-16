package com.jbb.mgt.core.service;

public interface FlashsdkLoginService {

    String flashsdkLogin(String appId, String accessToken, String telecom, String timestamp, String randoms,
        String sign, String version, String device, String platform) throws Exception;

}
