package com.jbb.server.common.exception;

public class WrongParameterValueException extends BaseLogicalException {
    private static final long serialVersionUID = -5069199845759846285L;
    
    public WrongParameterValueException(String message) {
        super(WRONG_PARAM_VALUE, message,"zh");
    }
    
    public WrongParameterValueException(String messageId, String language) {
        super(WRONG_PARAM_VALUE, messageId, language);
    }
    
    public WrongParameterValueException(String messageId, String language, String parameter0) {
        super(WRONG_PARAM_VALUE, messageId, language, parameter0);
    }
}
