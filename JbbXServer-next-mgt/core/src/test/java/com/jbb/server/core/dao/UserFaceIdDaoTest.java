package com.jbb.server.core.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.server.core.domain.UserFaceIdBizNo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class UserFaceIdDaoTest {

    @Autowired
    private UserFaceIdBizNoDao userFaceIdBizNoDao;
    
    @Test
    public void testUserKey() {
        /*UserFaceIdBizNo userface =  new UserFaceIdBizNo();
        userface.setBizNo("1223135");
        userface.setRandomData("aaa");
        userface.setRandomNumber("1234");
        userface.setTokenRandomNumber("aaa");
        userface.setTokenVideo("aaa");
        userface.setUserId(10000);
        userface.setValidateVideoData("aaa");
        userface.setVerifyData("aaa");
        userFaceIdBizNoDao.insertUserFaceIdBizNo(userface);*/
        UserFaceIdBizNo userface = userFaceIdBizNoDao.selectUserFaceIdBizNo("1223135", 10000);
        userface.setVerifyData("bbb");
        userFaceIdBizNoDao.updateUserFaceIdBizNo(userface);
        
    }
}
