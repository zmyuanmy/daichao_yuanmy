package com.jbb.mgt.core.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.XjlPayBank;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class XjlPayBankServiceTest {

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Autowired
    private XjlPayBankService xjlPayBankService;

    @Test
    public void testSelectXjlPayBank() {
        List<XjlPayBank> list = xjlPayBankService.selectXjlPayBank("");
        assertNotNull(list);
    }

}
