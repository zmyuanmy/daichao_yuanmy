package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.OrganizationDao;
import com.jbb.mgt.core.dao.mapper.OrganizationMapper;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.server.common.util.CommonUtil;

@Repository("OrganizationDao")
public class OrganizationDaoImpl implements OrganizationDao {

    @Autowired
    private OrganizationMapper mapper;

    @Override
    public void insertOrganization(Organization org) {
        mapper.insertOrganization(org);

    }

    @Override
    public boolean deleteOrganization(int orgId) {
        return mapper.deleteOrganization(orgId) > 0;
    }

    @Override
    public void updateOrganization(Organization org) {
        mapper.updateOrganization(org);

    }

    @Override
    public Organization selectOrganizationById(int orgId, boolean detail) {
        List<Organization> orgs = mapper.selectOrganizations(orgId, detail, false, false, null, null, true);
        if (CommonUtil.isNullOrEmpty(orgs)) {
            return null;
        }
        return orgs.get(0);
    }

    @Override
    public List<Organization> selectOrganizations(boolean detail, boolean getStatistic, boolean getAdStatistic,
        Timestamp startDate, Timestamp endDate, Boolean includeDelete) {
        return mapper.selectOrganizations(null, detail, getStatistic, getAdStatistic, startDate, endDate,
            includeDelete);
    }

    @Override
    public List<Organization> selectOrganizationSimper(Integer[] salesId) {
        return mapper.selectOrganizationSimper(salesId);
    }

    @Override
    public List<Organization> selectOrgAdByOrgId(Integer orgId, boolean detail, boolean getStatistic,
        boolean getAdStatistic, Timestamp startDate, Timestamp endDate) {
        return mapper.selectOrganizations(orgId, detail, getStatistic, getAdStatistic, startDate, endDate, true);
    }
}
