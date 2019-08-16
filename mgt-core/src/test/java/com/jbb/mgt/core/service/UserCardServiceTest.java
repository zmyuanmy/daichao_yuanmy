package com.jbb.mgt.core.service;

import com.alibaba.fastjson.JSON;
import com.jbb.mgt.core.domain.UserCard;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class UserCardServiceTest {

    @Autowired
    private UserCardService userCardService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testInsert() {
        UserCard userCardHistory = userCardService.selectUserCardByCardNo(55, "87654321", "1");
        if (userCardHistory != null) {
            // 曾经绑定过，更新删除状态
            userCardHistory.setDeleted(false);
            userCardService.updateUserCard(userCardHistory);
        } else {
            UserCard userCardNew = new UserCard();
            userCardNew.setDeleted(false);
            userCardNew.setAcceptLoanCard(false);
            userCardNew.setBankCode("001");
            userCardNew.setCardNo("87654321");
            userCardNew.setCreationDate(DateUtil.getCurrentTimeStamp());
            userCardNew.setPayProductId("1");
            userCardNew.setPhoneNumber("88888888");
            userCardNew.setUserId(1);
            userCardService.insertUserCard(userCardNew);
        }
    }

    @Test
    public void testDelete() {
        UserCard userCardHistory = userCardService.selectUserCardByCardNo(55, "87654321", "1");
        if (userCardHistory != null) {
            userCardHistory.setAcceptLoanCard(false);
            userCardHistory.setDeleteDate(DateUtil.getCurrentTimeStamp());
            userCardHistory.setDeleted(true);
            userCardService.updateUserCard(userCardHistory);
        }
    }

    @Test
    public void testAccept() {
        userCardService.acceptUserCard(1, "87654321", "1");
    }

    @Test
    public void testSelect() {
        List<UserCard> userCards = userCardService.selectUserCards(1, "1");
        String json = JSON.toJSONString(userCards);
        System.out.println(json);
    }

}
