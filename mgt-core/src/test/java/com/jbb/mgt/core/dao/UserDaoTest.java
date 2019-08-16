package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.OrgAppliedCount;
import com.jbb.server.common.Constants;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testCountOrgAppliedUsers() {
        long startTs = DateUtil.getTodayStartTs();
        long endTs = startTs + DateUtil.DAY_MILLSECONDES;

        List<OrgAppliedCount> orgCounts = userDao.countOrgAppliedUsers(null, new Timestamp(startTs),
            new Timestamp(endTs), Constants.USER_TYPE_REGISTER);

        orgCounts.forEach(orgCnt -> System.out.println("orgId:" + orgCnt.getOrgId() + " , cnt=" + orgCnt.getCnt()));

        orgCounts =
            userDao.countOrgAppliedUsers(null, new Timestamp(startTs), new Timestamp(endTs), Constants.USER_TYPE_ENTRY);

        orgCounts.forEach(orgCnt -> System.out.println("orgId:" + orgCnt.getOrgId() + " , cnt=" + orgCnt.getCnt()));
    }

    @Test
    public void testHideUser() {

        userDao.updateUserHiddenStatus(55, true);

    }

}
