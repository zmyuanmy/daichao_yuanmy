/**
 * 
 */
package com.jbb.mgt.core.domain;

/**
 * @author zcp
 *
 * 2019年1月24日 下午7:06:39
 */
public class UserComplaint {

	private Integer  id;
	
	private Integer  userId;
	
	private String content;
	
	private String appName;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public UserComplaint() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public UserComplaint(Integer userId, String content, String appName) {
		super();
		this.userId = userId;
		this.content = content;
		this.appName = appName;
	}
	
	
	
	
}
