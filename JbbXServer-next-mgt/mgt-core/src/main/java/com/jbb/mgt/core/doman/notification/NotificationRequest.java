package com.jbb.mgt.core.doman.notification;

import com.jbb.mgt.core.domain.Message;

public class NotificationRequest {
    private Message message;

    public NotificationRequest(Message message) {
        super();
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
