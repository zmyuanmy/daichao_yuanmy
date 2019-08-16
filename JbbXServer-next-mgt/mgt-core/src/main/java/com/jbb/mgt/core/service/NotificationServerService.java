package com.jbb.mgt.core.service;

import com.jbb.mgt.core.doman.notification.NotificationRequest;

public interface NotificationServerService {

    public String getConvSignature(String clientId, String sortedMemberIds, String nonce, long timestamp, String convId,
        String action);

    String getLoginSignature(String clientId, String nonce, long timestamp);

    void pushNotification(NotificationRequest notificationReq);
}
