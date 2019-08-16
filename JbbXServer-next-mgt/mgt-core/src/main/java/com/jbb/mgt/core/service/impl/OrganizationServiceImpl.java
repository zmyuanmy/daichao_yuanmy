package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.OrganizationDao;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.service.OrganizationService;

@Service("OrganizationService")
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;

    @Override
    public void insertOrganization(Organization org) {
        organizationDao.insertOrganization(org);
    }

    @Override
    public Organization getOrganizationById(int orgId, boolean detail) {
        return organizationDao.selectOrganizationById(orgId, detail);
    }

    @Override
    public boolean deleteOrganization(int orgId) {
        return organizationDao.deleteOrganization(orgId);
    }

    @Override
    public List<Organization> getOrganizations(boolean detail, boolean getStatistic, boolean getAdStatistic,
        Timestamp startDate, Timestamp endDate, Boolean includeDelete) {

        return organizationDao.selectOrganizations(detail, getStatistic, getAdStatistic, startDate, endDate,
            includeDelete);
    }

    @Override
    public void updateOrganization(Organization org) {
        organizationDao.updateOrganization(org);

    }

    @Override
    public List<Organization> selectOrganizationSimper(Integer[] salesId) {
        return organizationDao.selectOrganizationSimper(salesId);
    }

}
