package com.jbb.server.common.exception;

public class ParsingException extends BaseLogicalException {
    private static final long serialVersionUID = -4840370266387467359L;

    public ParsingException() {
        super(PARSING_ERROR, null);
    }
}
