package com.jbb.mgt.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.Permissions;
import com.jbb.server.common.Home;

/**
 * 权限service测试类
 * 
 * @author wyq
 * @date 2018/04/27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class PermissionsServiceTest {
    
    int applicationId = 0;

    @Autowired
    private PermissionsService service;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testDataDlowSettingService() {
        // 获取数据并验证
        List<Permissions> list = service.selectPermissions(applicationId);
        assertTrue(list.size() == 17);

        Permissions permissions = service.selectPermissionsByPermissionsId(1, applicationId);
        assertEquals(list.get(0).getDescription(), permissions.getDescription());
    }

}
