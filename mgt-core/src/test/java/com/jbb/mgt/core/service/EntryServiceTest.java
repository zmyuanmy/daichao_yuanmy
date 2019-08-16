package com.jbb.mgt.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class EntryServiceTest {

    @Autowired
    private EntryService Service;

    @Test
    public void testEntryService() {
        Integer userId = 10000001;
        String channelCode = "8EgVLF";
        Service.check(userId);
       // boolean b = Service.check(userId, channelCode);
        //System.out.println(b);

    }
}
