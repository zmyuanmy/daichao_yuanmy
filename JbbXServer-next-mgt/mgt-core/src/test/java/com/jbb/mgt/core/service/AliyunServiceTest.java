package com.jbb.mgt.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.jbb.mgt.core.service.impl.AliyunServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.IPAddressInfo;
import com.jbb.server.common.Home;
import sun.misc.BASE64Encoder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class AliyunServiceTest {

    @Autowired
    private AliyunService aliyunService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testSendMessage() {
        // Map<String, String> params = new HashMap<String, String>();
        // params.put("code", "1234");
        // aliyunService.sendSms("18575511205", "SMS_121165611", params);
    }

   // @Test
    public void testOss() throws IOException {
        String bucket = "jbb-td-preloan";
        String key = "test";
        String content = "blablabla";
        aliyunService.putObject(bucket, key, content);

        String content2 = aliyunService.getObject(bucket, key);
        assertEquals(content, content2);
    }

    @Test
    public void testGetIpInfo() throws IOException {

        IPAddressInfo ipAddress = aliyunService.getIPAddressInfo("0:0:0:0:0:0:0:1");
        assertNotNull(ipAddress);
        System.out.println(ipAddress);

        ipAddress = aliyunService.getIPAddressInfo("not exist");
        assertNull(ipAddress);
        System.out.println(ipAddress);

    }


}
