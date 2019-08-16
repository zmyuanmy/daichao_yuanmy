
package com.jbb.server.common.exception;

public class WrongIouStatusException extends BaseLogicalException {

    private static final long serialVersionUID = -3683190533858207753L;

    public WrongIouStatusException() {
        super(WRONG_IOU_INFO, "");
    }

    public WrongIouStatusException(String messageId) {
        super(WRONG_IOU_INFO, messageId, "zh");
    }

    public WrongIouStatusException(String messageId, String parameter0) {
        super(WRONG_IOU_INFO, messageId, "zh", parameter0);
    }
}
