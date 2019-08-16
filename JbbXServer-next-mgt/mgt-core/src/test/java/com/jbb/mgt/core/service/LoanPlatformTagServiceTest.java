package com.jbb.mgt.core.service;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.LoanPlatformTag;
import com.jbb.mgt.core.domain.Platform;
import com.jbb.mgt.core.domain.PlatformUv;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class LoanPlatformTagServiceTest {

    @Autowired
    private LoanPlatformTagService service;
    @Autowired
    private LoanPlatformService loanPlatformService;

    @Autowired
    private UserEventLogService eventLogService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testLoanPlatformTag() {
        LoanPlatformTag tag = new LoanPlatformTag();
        tag.setPlatformId(1);
        tag.setTagId(1);
        tag.setPos(1);
        service.insertLoanPlatformTag(tag);
        LoanPlatformTag tag1 = new LoanPlatformTag();
        tag1.setPlatformId(2);
        tag1.setTagId(1);
        tag1.setPos(2);
        /*service.insertLoanPlatformTag(tag1);*/

        tag.setPos(2);
        service.updateLoanPlatformTag(tag);
        service.deleteLoanPlatformTag(1, 1, 1);
    }

    @Test
    public void testLoanPlatform() {
        Platform f = loanPlatformService.getPlatformById(1);
        System.out.println(f);
    }

    @Test
    public void getPlatformUv() {
        List<PlatformUv> list = eventLogService.getPlatformUv(null, null, null, "2018-10-11", "2018-10-11 23:59:59");
        System.out.println("======="+list.size());
    }
}
