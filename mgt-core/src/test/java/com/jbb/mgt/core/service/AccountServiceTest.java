package com.jbb.mgt.core.service;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.LoginLog;
import com.jbb.mgt.server.core.util.PasswordUtil;
import com.jbb.server.common.Home;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class AccountServiceTest {
    @Autowired
    private AccountService accountsService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    // @Test
    public void testAccountsService() {

        /*
         * List<Account> all = accountsService.getAccount(1); for (Account c : all) {
         * accountsService.deleteAccount(c.getAccountId()); }
         */
        // 创建子账号-添加数据
        Account account = new Account();
        String username = StringUtil.randomAlphaNum(10);
        account.setUsername(username);
        account.setNickname("nickname");
        account.setPhoneNumber("11111");
        account.setPassword(PasswordUtil.passwordHash("1234567890"));
        account.setJbbUserId(null);
        account.setOrgId(1);
        account.setCreator(1);
        int[] roles = {2, 3, 4, 5, 6};
        // account.setRoles(roles);
        accountsService.createAccount(account);

        accountsService.getAccountById(account.getAccountId(), null, true);
        System.out.println(account);
        int orgId = 1;
        List<Account> accountAll = accountsService.getAccount(orgId, true);
        System.out.println(accountAll.size());

        // 更新数据
        account = accountsService.getAccountById(account.getAccountId(), null, true);
        String newName = StringUtil.randomAlphaNum(10);
        account.setUsername(newName);
        account.setPhoneNumber("111111");
        accountsService.updateAccount(account);

        // accountsService.deleteAccount(account.getAccountId());

    }

    // @Test
    public void testLoginLog() {
        LoginLog log = new LoginLog();
        log.setAccountId(1);
        log.setIpAddress("127.0.0.1");
        log.setProvince("广东");
        log.setCity("深圳");
        log.setLoginDate(DateUtil.getCurrentTimeStamp());

        accountsService.insertLoginLog(log);
        accountsService.insertLoginLog(log);

        List<LoginLog> logs = accountsService.getLoginLogsByAccountId(1);
        assertTrue(logs.size() >= 2);

        logs = accountsService.getLoginLogsByOrgId(1);
        assertTrue(logs.size() >= 2);
    }

    @Test
    public void testGetAssingAccounts() {
        Timestamp startDate = Util.parseTimestamp("2018-08-20", TimeZone.getDefault());
        Timestamp endDate = Util.parseTimestamp("2018-08-21", TimeZone.getDefault());
        List<Account> accounts = accountsService.getOrgAutoAssignAccounts(188, 3, startDate, endDate, null);
        if (accounts == null)
            return;
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println(
                "============" + accounts.get(i).getAccountId() + "============" + accounts.get(i).getInitCnt());
        }

    }

    @Test
    public void testGetInitAccount() {

//        Account acc = accountsService.getOrgAutoAssignAccount(188, 1);
//        System.out.println("==============");
//        System.out.println(acc);
//
//        acc = accountsService.getOrgAutoAssignAccount(188, 2);
//        System.out.println("==============");
//        System.out.println(acc);
//        for(int i=0;i<20;i++){
//            Account acc = accountsService.getOrgAutoAssignAccount(188, 3);
//            System.out.println("==============");
//            System.out.println(acc.getAccountId() + "====" + acc.getInitCnt());
//        }
        
        String[] referers = PropertyManager.getProperties("jbb.app.referer");
       
        boolean refFlag =  !CommonUtil.inArray("test", referers);
        for(String s :referers){
            System.out.println(s);
        }
        System.out.println(refFlag);
       
    }

}
