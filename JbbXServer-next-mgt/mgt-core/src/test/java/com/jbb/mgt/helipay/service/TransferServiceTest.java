package com.jbb.mgt.helipay.service;

import java.io.UnsupportedEncodingException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserCard;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class TransferServiceTest {

    @Autowired
    private TransferService transferService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    /**
     * 4.1 代付
     * 
     * @throws Exception
     */
    public void testTransfer() throws Exception {

        User user = new User();
        user.setUserId(111);
        user.setUserName("唐文华");

        UserCard userCard = new UserCard();
        userCard.setBankCode("PINGAN");
        userCard.setCardNo("6230583000001622325");

        XjlApplyRecord apply = new XjlApplyRecord();
        apply.setAmount(100);
        transferService.transfer(user, userCard, apply, 1);

    }

    public void testTransferQuery() throws UnsupportedEncodingException {
        transferService.tranferQuery("20180906110129545");
    }

}
