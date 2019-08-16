package com.jbb.mgt.core.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.JuXinLiReportRsp;
import com.jbb.mgt.core.domain.YxReportRsp;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class JuXinLiReportServiceTest {

    @Autowired
    private JuXinLiReportService juXinLiReportService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testYxReport() {

    }

    // @Test
    public void getReportFromJuXinLi() {
        String token = "";
        JuXinLiReportRsp rsp = null;

        // JuXinLiReportRsp rsp = juXinLiReportService.getReportFormJuXinLi(token);
        // System.out.println(rsp);

        token = "954c60b006c3433b829a45eaae5b3cc3";

        rsp = juXinLiReportService.getReportFormJuXinLi(token);
        System.out.println(rsp);
        // assertNotNull(rsp);
    }

    @Test
    public void deleteOssReport() {
        String token = "954c60b006c3433b829a45eaae5b3cc3";
        juXinLiReportService.deleteOssRepoert(token);
    }

}
