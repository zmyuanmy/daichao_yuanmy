package com.jbb.server.common.exception;

public class WrongIouCodeException extends BaseLogicalException {

    private static final long serialVersionUID = -3683190533858207753L;

    public WrongIouCodeException() {
        super(WRONG_IOUCODE, "jbb.error.exception.wrongIouCode", null);
    }
}
