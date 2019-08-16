 package com.jbb.server.common.exception;

 public class Call3rdApiException extends BaseLogicalException {

	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 8056118973922507360L;
	
	public Call3rdApiException() {
		super(Call3rdApi_REQUEST_ERROR, "jbb.error.exception.call3rdApi.error", "zh");
	}
	
	public Call3rdApiException(String message) {
        super(Call3rdApi_REQUEST_ERROR, message, "zh");
    }
	
	public Call3rdApiException(String messageId, String error) {
        super(Call3rdApi_REQUEST_ERROR, messageId, "zh", error);
    }

}
