package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.H5Merchant;
import com.jbb.server.common.Home;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class H5MerchantServiceTest {

    @Autowired
    private H5MerchantService Service;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testChannelService() {
       /* List<H5Merchant> list = Service.selectH5Merchants();
        assertTrue(list.size() == 1);

        Service.insertH5Merchant(new H5Merchant());
        List<H5Merchant> list2 = Service.selectH5Merchants();
        assertTrue(list2.size() == 2);*/

        /*list2.get(0).setCreator(1);
        Service.updateH5Merchant(list2.get(0));
        List<H5Merchant> list3 = Service.selectH5Merchants();
        assertTrue(list3.get(0).getCreator() == 1);
        H5Merchant h5Merchant = Service.selectH5merchantById(2);
        assertTrue(h5Merchant.getCreator() == 1);*/

        /*H5Merchant hm = new H5Merchant();
        hm.setMerchantId(7);
        hm.setCreator(1);
        Service.updateH5Merchant(hm);*/

        H5Merchant uyHLRc = Service.selectH5merchantByChannelCode("UyHLRc");
        assertTrue(uyHLRc.getCreator() == 1);

    }

}
