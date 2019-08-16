package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.AccountOpLog;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 账号操作日志service,dao测试类
 * 
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class AccountOpLogServiceTest {

    @Autowired
    private AccountOpLogService accountOpLogService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testAccountOpLogService() {
        // 插入数据
        AccountOpLog accountOpLog = new AccountOpLog();
        accountOpLog.setApplyId(1);
        accountOpLog.setOpType(1);
        accountOpLog.setOpDate(DateUtil.getCurrentTimeStamp());
        accountOpLog.setOpReason("dao插入测试");
        accountOpLog.setOpComment("dao插入测试");
        Account account = new Account();
        account.setAccountId(1);
        accountOpLog.setAccount(account);
        accountOpLogService.createOpLog(accountOpLog);

    }
}
