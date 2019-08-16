package com.jbb.server.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.domain.Message;
import com.jbb.server.core.doman.notification.NotificationRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class LeanCloudServiceTest {
    @Autowired
    private LeanCloudService leanCloudService;

    @Test
    public void testGetSignature() {
        String nonce = StringUtil.randomAlphaNum(16);
        long timestamp = DateUtil.getCurrentTime();
        String clientId = "testClientId";
        String sortedMemberIds = null;
        String s = leanCloudService.getLoginSignature(clientId, nonce, timestamp);
        System.out.println(s);
        sortedMemberIds = "test111:test112";
        s = leanCloudService.getConvSignature(clientId, sortedMemberIds, nonce, timestamp, null, null);
        System.out.println(s);

        s = leanCloudService.getConvSignature(clientId, sortedMemberIds, nonce, timestamp, "testConvId", "create");
        System.out.println(s);
    }

    // @Test
    public void testExchangeInfo() {
        int from = 1000000;
        int to = 1000001;
        String convId = "5a6a95ae0ad3b7276f699b3a";
        // int cmdType = 102;
        for (int cmdType = 101; cmdType < 103; cmdType++) {
            System.out.println("========================");
            System.out.println("===========" + cmdType + "==========");
            leanCloudService.exchangeInfo(from, to, cmdType, convId, null, null);
            System.out.println("========================");
        }
    }

    @Test
    public void pushNotification() {
        Message message = new Message();
        message.setToUserId(1000000);
        message.setSubject("Subject");
        message.setMessageText("test text" + StringUtil.randomAlphaNum(50));
        NotificationRequest req = new NotificationRequest(message);
        leanCloudService.pushNotification(req);
    }

}
