package com.jbb.server.common.exception;

import java.io.Serializable;

public class HeLiPayException extends BaseLogicalException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -696030283999211776L;

    public HeLiPayException() {
        super(HeLiPay_ERROR, "jbb.xjl.error.exception.hlb.error", "zh");
    }

    public HeLiPayException(String message) {
        super(HeLiPay_ERROR, message, "zh");
    }

    public HeLiPayException(String messageId, String language) {
        super(HeLiPay_ERROR, messageId, language);
    }

    public HeLiPayException(String messageId, String language, String parameter0) {
        super(HeLiPay_ERROR, messageId, language, parameter0);
    }
}
