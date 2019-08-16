package com.jbb.server.core.service;

import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.core.domain.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private FaceService faceService;
    @Autowired
    private MgtService mgtService;
    @Autowired
    private AccountService accountService;

    @Test
    @Transient
    public void insertMessage() {
        Message message = new Message();
        message.setCreationDate(DateUtil.getCurrentTimeStamp());
        message.setFromUserId(0);
        message.setHidden(false);
        message.setMessageText("这是测试消息10");
        message.setMessageType(0);
        message.setParameters("测试参数");
        message.setPush(false);
        message.setRead(false);
        message.setSendDate(DateUtil.getCurrentTimeStamp());
        message.setSms(false);
        message.setSubject("测试消息");
        message.setToUserId(1000000);
        messageService.insertMessage(message);
    }

    @Test
    @Transient
    public void getMessage() {
        Message message = messageService.getMessageById(1);
        System.out.println("message " + message.getMessageText());
    }

    @Test
    @Transient
    public void getUnreadMessagesCount() {
        int messagenum = messageService.getUnreadMessagesCount(1);
        System.out.println("messagenum " + messagenum);
    }

    @Test
    @Transient
    public void updateMessageStatusRead() {
        messageService.updateMessageStatusRead(null, 1, true);
    }

    @Test
    @Transient
    public void updateMessageStatusHidden() {
        messageService.updateMessageStatusHidden(1, 1, false);
    }

    @Test
    @Transient
    public void getMessages() {
        List<Message> messagelist = messageService.getMessages(0, 1, false, 4, 1, 5);
    }

    @Test
    public void testUsersyschornize() {
        int userId = 1000051;
        String returnResult = "{\"list\":" + "["
            + "{\"userName\":\"张三\",\"idCard\":\"122\",\"qq\":\"123321\",\"wechat\":\"qqqqq\",\"realnameVerified\":true,\"creationDate\":\"1527582991400\"},"
            + "{\"userName\":\"张三\",\"idCard\":\"122\",\"qq\":\"123321\",\"wechat\":\"qqqqq\",\"realnameVerified\":true,\"creationDate\":\"1527582991474\"},"
            + "{\"userName\":\"张三\",\"idCard\":\"122\",\"qq\":\"123321\",\"wechat\":\"qqqqq\",\"realnameVerified\":true,\"creationDate\":\"1527582991500\"}"
            + "]}";

    }

    @Test
    public void testHttp() {

        String data = "";
        String url = "http://192.168.0.32:8080/manager/integrate/user?phoneNumber=18575512205";
        HttpUtil.HttpResponse response = null;
        String[][] requestProperties = new String[1][2];
        requestProperties[0][0] = "API_KEY";
        requestProperties[0][1] = "9Ol7ipefXdcSD_tWi6raaIdrmHJDas";
        try {
            response = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url, HttpUtil.CONTENT_TYPE_JSON, data,
                requestProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.getResponseCode() == HttpUtil.STATUS_OK) {
            byte[] byteArray = response.getResponseBody();
            try {
                String res = new String(byteArray, "UTF-8");
                System.out.println("res000 " + res);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

    }

    @Test
    public void testCreatIou() {

        /*MgtIou mgtIou = new  MgtIou();
        mgtIou.setAnnualRate(4000);
        mgtIou.setBorrowerId(1);
        mgtIou.setBorrowingAmount(100000);
        mgtIou.setBorrowingDate("2018-05-26");
        mgtIou.setIouCode("JBBXXxx");
        mgtIou.setLenderId(1);
        mgtIou.setRepaymentDate("2018-06-26");
        mgtIou.setStatus(20);
        mgtService.creatMgtIou(mgtIou);*/

       // mgtService.updateMgtIou("JBB2018052113767305623", "1527582991474", 8);

    }

}
