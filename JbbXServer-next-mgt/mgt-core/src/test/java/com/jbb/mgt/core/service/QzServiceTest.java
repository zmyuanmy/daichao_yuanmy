package com.jbb.mgt.core.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.MD5;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class QzServiceTest {

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    //@Test
    public void testQzTask() {
        String apiUser = "8150725329";
        String timeMark = DateUtil.getSystemTimeString1() + "/" + "taobaopay";
        String taskId = "Test007";
        String apiEnc = MD5.MD5Encode(
            apiUser + DateUtil.getSystemTimeString1() + "taobaopay" + taskId + "6613d600d19941a094753830bd6fc0af");
        String jumpUrl = "https://www.baidu.com";
        System.out.println("https://qz.xinyan.com/h5/" + apiUser + "/" + apiEnc + "/" + timeMark + "/" + taskId + "?"
            + "jumpUrl=" + jumpUrl);
        // https://qz.xinyan.com/h5/{apiUser}/{apiEnc}/{timeMark}/{apiName}/{taskId}?jumpUrl={jumpUrl}

    }

}
