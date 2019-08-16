package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.OrganizationLender;
import com.jbb.server.common.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class OrganizationLenderServiceTest {

    @Autowired
    OrganizationLenderService service;

    @Test
    public void deleteByPrimaryKey() throws Exception {
        int i = service.deleteByPrimaryKey(1);
        assertTrue(i>0);
    }

    @Test
    public void insert() throws Exception {
        OrganizationLender record = new OrganizationLender(1,1,100,"张三","测试");
        int insert = service.insert(record);
        assertTrue(insert>0);
    }

    @Test
    public void insertSelective() throws Exception {
        OrganizationLender record = new OrganizationLender(2,1,100,"李四","测试");
        int insert = service.insert(record);
        assertTrue(insert>0);
    }

    @Test
    public void selectByPrimaryKey() throws Exception {
        OrganizationLender organizationLender = service.selectByPrimaryKey(1);
        assertNotNull(organizationLender);
    }

    @Test
    public void updateByPrimaryKeySelective() throws Exception {
        OrganizationLender organizationLender = service.selectByPrimaryKey(1);
        organizationLender.setDescription("updateByPrimaryKeySelective");
        organizationLender.setUpdateDate(DateUtil.getCurrentTimeStamp());
        int i = service.updateByPrimaryKeySelective(organizationLender);
        assertTrue(i>0);
    }

    @Test
    public void updateByPrimaryKey() throws Exception {
        OrganizationLender organizationLender = service.selectByPrimaryKey(1);
        organizationLender.setDescription("updateByPrimaryKey");
        organizationLender.setUpdateDate(DateUtil.getCurrentTimeStamp());
        int i = service.updateByPrimaryKey(organizationLender);
        assertTrue(i>0);
    }

    @Test
    public void selectOrgLender(){
        List<OrganizationLender> organizationLenders = service.selectOrgLenderList();
        assertNotNull(organizationLenders);
    }

}