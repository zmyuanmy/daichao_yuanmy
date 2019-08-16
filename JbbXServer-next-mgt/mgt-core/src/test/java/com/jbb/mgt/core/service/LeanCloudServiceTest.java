package com.jbb.mgt.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.Message;
import com.jbb.mgt.core.doman.notification.NotificationRequest;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class LeanCloudServiceTest {
    @Autowired
    private NotificationServerService leanCloudService;

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
