package com.jbb.mgt.core.service;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.UserJob;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class UserJobServiceTest {

    @Autowired
    private UserJobService service;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testSaveUserJobInfo() {
        UserJob userJob = new UserJob();
        userJob.setUserId(23315);
        userJob.setCompany("创世");
        userJob.setAddressId(1);
        userJob.setStartDate(new Date());
        userJob.setEndDate(new Date());
        service.saveUserJobInfo(userJob);
    }
}
