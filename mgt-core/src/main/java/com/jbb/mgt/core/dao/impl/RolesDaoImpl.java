package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.RolesDao;
import com.jbb.mgt.core.dao.mapper.RolesMapper;
import com.jbb.mgt.core.domain.Roles;

/**
 * 角色Dao实现类
 * 
 * @author wyq
 * @date 2018/04/28
 */
@Repository("RolesDao")
public class RolesDaoImpl implements RolesDao {

    @Autowired
    private RolesMapper mapper;

    @Override
    public List<Roles> selectRoles(int applicationId) {
        return mapper.selectRoles(applicationId);
    }

    @Override
    public Roles selectRolesByRoleId(Integer roleId, int applicationId) {
        return mapper.selectRolesByRoleId(roleId, applicationId);
    }

    @Override
    public List<Integer> selectRolesAndPermissionsByRoleId(Integer roleId, int applicationId) {
        return mapper.selectRolesAndPermissionsByRoleId(roleId, applicationId);
    }

}
