package com.jbb.mgt.core.crawler;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class CrawlerTest {

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testH5XiaoLuRegisterCrawler() {
        H5XiaoLuRegisterCrawler instance = H5XiaoLuRegisterCrawler.getInstance();
        instance.crawlRegisterData(3, "2018-08-16", "2018-08-17");

    }

}
