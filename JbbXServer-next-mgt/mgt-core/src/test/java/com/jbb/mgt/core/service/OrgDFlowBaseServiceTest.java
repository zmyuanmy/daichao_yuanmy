package com.jbb.mgt.core.service;

import com.jbb.server.common.Home;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class OrgDFlowBaseServiceTest {

    @Autowired
    private OrgDFlowBaseService service;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testOrgDFlowBaseService() {
        service.deleteOrgDflowBase(1);

       /* ArrayList<OrgDflowBase> list = new ArrayList<>();
        OrgDflowBase odf = new OrgDflowBase();
        odf.setOrgId(1);
        odf.setdFlowId(1);
        odf.setUpdateAccountId(1);
        list.add(odf);*/
        /*OrgDflowBase odf2 = new OrgDflowBase();
        odf2.setOrgId(1);
        odf2.setdFlowId(2);
        list.add(odf2);*/
//        service.insertOrgDFlowBase(list);

        /*List<OrgDflowBase> list = service.selectOrgDflowBase(1);
        assertTrue(list.size() > 0);*/

        int[] ints = service.gerdflowId(2);
    }
}
