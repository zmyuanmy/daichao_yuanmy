package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.FinOrgSalesRelation;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class FinOrgSalesRelationServiceTest {

    @Autowired
    private FinOrgSalesRelationService service;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testService() {
        FinOrgSalesRelation f = new FinOrgSalesRelation();
        f.setOrgId(1);
        service.insertFinOrgSalesRelation(f);
    }
}
