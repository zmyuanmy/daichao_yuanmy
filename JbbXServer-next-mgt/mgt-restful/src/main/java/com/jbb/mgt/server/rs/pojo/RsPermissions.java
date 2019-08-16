package com.jbb.mgt.server.rs.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Permissions;

/**
 * 权限表response实体类
 * 
 * @author wyq
 * @date 2018/04/28
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RsPermissions {

	private Integer permissionId;

	private String description;

	public RsPermissions(Permissions permissions) {
		this.permissionId = permissions.getPermissionId();
		this.description = permissions.getDescription();
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RsPermissions() {
		super();
	}

}
