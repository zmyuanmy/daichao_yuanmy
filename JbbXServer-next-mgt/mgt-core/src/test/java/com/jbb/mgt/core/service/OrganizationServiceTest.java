package com.jbb.mgt.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ctc.wstx.util.DataUtil;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class OrganizationServiceTest {

    @Autowired
    private OrganizationService organizationService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testOrganizations() {
        // 插入org
        Organization org = new Organization("test org 1", "test org desc");
        org.setCompanyName("腾讯");
        org.setOrgType(1);
        org.setCnt(1);
        org.setWeight(0);
        org.setDataEnabled(true);
        organizationService.insertOrganization(org);
        int orgId = org.getOrgId();

        // 获取org
        Organization orgR = organizationService.getOrganizationById(orgId, false);
        assertEquals(org.getDescription(), orgR.getDescription());
        assertEquals(org.getName(), orgR.getName());

        // 删除org
        organizationService.deleteOrganization(orgId);

        // 获取org
        orgR = organizationService.getOrganizationById(orgId, true);
        assertTrue(orgR.isDeleted());

    }

    @Test
    public void getCounts() {
        /*long start = DateUtil.getTodayStartCurrentTime();
        long end = start + DateUtil.DAY_MILLSECONDES;
        organizationService.getOrganizations(true, true, new Timestamp(start), new Timestamp(end));*/
        List<Organization> list = organizationService.selectOrganizationSimper(new Integer[]{1, 2,3,4,5,6,7});
    }

}
