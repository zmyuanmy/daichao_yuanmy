package com.jbb.mgt.core.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.LoanTag;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class LoanTagServiceTest {

    @Autowired
    private LoanPlatformService service;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void test() {
        LoanTag loanTag = new LoanTag();
        loanTag.setTagName("1");
        loanTag.setAreaId(1);
        loanTag.setPos(2);
        // loanTag.setLogoUrl("1");
         service.saveLoanTag(loanTag);
        loanTag.setAreaTagId(2);
//        service.updateLoanTag(loanTag);
        // List<LoanTag> list = service.getLoanTags(true);
        // service.deleteLoanTag(1);
//        service.deleteLoanTag(1);
        // service.deletePlatformTagByTagId(1);

//        System.out.println(service.getLoanTagByAreaTagId(1));
    }

}
