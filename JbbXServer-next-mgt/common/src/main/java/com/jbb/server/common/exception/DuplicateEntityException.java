 package com.jbb.server.common.exception;

 public class DuplicateEntityException extends BaseLogicalException {

   
    /**
     *
     */
    private static final long serialVersionUID = -2072597998961054030L;
    
    public DuplicateEntityException() {
        super(DUPLICATE_ENTITY, "jbb.error.exception.duplicateEntity", "zh");
    }
    
    public DuplicateEntityException(String messageId) {
        super(DUPLICATE_ENTITY, messageId, "zh");
    }

}
