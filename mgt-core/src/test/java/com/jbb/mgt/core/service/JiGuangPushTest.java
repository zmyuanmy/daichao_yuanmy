package com.jbb.mgt.core.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.JiGuangMessage;
import com.jbb.mgt.core.domain.JiGuangMessage.Flag;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class JiGuangPushTest {

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Autowired
    private JiGuangService jiGuangService;

    // @Test
    public void testSelectAggrement1() {
        JiGuangMessage jiGuangMessage = new JiGuangMessage();
        Flag f = new Flag();
        String[] s = {"Android_vivo_1.5.0"};
        String[] s1 = {"ios"};
        f.setAlias(s);
        f.setRegistration_id(null);
        f.setTag(null);
        f.setTag_and(null);
        // jiGuangMessage.setPlatform(s1);
        // jiGuangMessage.setAudience(f);
        jiGuangMessage.setAlert("这是测试信息");
        jiGuangMessage.setTitle("点击会跳到百度");
        jiGuangMessage.setPlatformId("1");
        jiGuangMessage.setPlatformName("测试");
        jiGuangMessage.setPlatformUrl("http://jiebangbang.cn");
        jiGuangService.jiGuangPush(jiGuangMessage);
    }
}
