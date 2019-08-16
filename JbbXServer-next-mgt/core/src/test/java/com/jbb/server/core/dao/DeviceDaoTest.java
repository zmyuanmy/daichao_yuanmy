package com.jbb.server.core.dao;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class DeviceDaoTest {
    @Autowired
    private DeviceDao deviceDao;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testDevice() {
        String equipmentNumber = "testSeqNumber" + StringUtil.getRandomnum(12);
        boolean ret = deviceDao.checkDeviceExist(equipmentNumber);
        if (!ret) {
            ret = deviceDao.insertDevice(equipmentNumber, DateUtil.getCurrentTimeStamp());
            assertTrue(ret);
        }
        ret = deviceDao.checkDeviceExist(equipmentNumber);
        assertTrue(ret);

    }
}
