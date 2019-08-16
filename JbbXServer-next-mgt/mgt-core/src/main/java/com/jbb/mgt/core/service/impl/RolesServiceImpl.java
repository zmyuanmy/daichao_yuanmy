package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.RolesDao;
import com.jbb.mgt.core.domain.Roles;
import com.jbb.mgt.core.service.RolesService;

/**
 * 角色service实现类
 * 
 * @author wyq
 * @date 2018/04/28
 */
@Service("RolesService")
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesDao rolesDao;

    @Override
    public List<Roles> selectRoles(int applicationId) {
        return rolesDao.selectRoles(applicationId);
    }

    @Override
    public Roles selectRolesByRoleId(Integer roleId, int applicationId) {
        return rolesDao.selectRolesByRoleId(roleId, applicationId);
    }

    @Override
    public List<Integer> selectRolesAndPermissionsByRoleId(Integer roleId, int applicationId) {
        return rolesDao.selectRolesAndPermissionsByRoleId(roleId, applicationId);
    }

}
