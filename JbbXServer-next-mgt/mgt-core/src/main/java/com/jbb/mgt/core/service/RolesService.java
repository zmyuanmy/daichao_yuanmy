package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.Roles;

/**
 * 角色service接口类
 * 
 * @author wyq
 * @date 2018/04/28
 */
public interface RolesService {

    List<Roles> selectRoles(int applicationId);

    Roles selectRolesByRoleId(Integer roleId, int applicationId);
    
    List<Integer> selectRolesAndPermissionsByRoleId(Integer roleId, int applicationId);
    
}
