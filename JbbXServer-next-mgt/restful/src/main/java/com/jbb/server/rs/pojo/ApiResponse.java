package com.jbb.server.rs.pojo;

public interface ApiResponse {
	String API_RESPONSE = "API_RESPONSE";
	String API_CALL = "API_CALL";

	int getResultCode();

	ApiCall getApiCall();
}
