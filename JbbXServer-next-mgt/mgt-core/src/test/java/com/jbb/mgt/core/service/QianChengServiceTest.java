package com.jbb.mgt.core.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GenericRequest;
import com.jbb.mgt.core.dao.XjlApplyRecordDao;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.server.common.Home;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.AliyunException;
import com.jbb.server.common.util.DateUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class QianChengServiceTest {

    @Autowired
    private QianChengService qianChengService;

    @Autowired
    private  UserService userService;

    @Autowired
    private XjlApplyRecordDao xjlApplyRecordDao;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }


    @Test
    public void testGetToken(){
        String token = qianChengService.getToken();
    }

    @Test
    public void getIouPhotoUrl() {

        String endpoint = PropertyManager.getProperty("jbb.aliyu.oss.bucket.endpoint");
        String accessId = PropertyManager.getProperty("jbb.aliyun.oss.accessKeyId");
        String accessKey = PropertyManager.getProperty("jbb.aliyun.oss.accessKeySecret");
        String bucket = PropertyManager.getProperty("jbb.aliyu.oss.bucket.jbb.mgt.user.photos");
        String style = PropertyManager.getProperty("jbb.aliyu.oss.style");
        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        try {
            client.getObjectMetadata(new GenericRequest(bucket, "2351228215347974.jpg"));
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, "2351228215347974.jpg");
            request.setExpiration(new Date(DateUtil.getCurrentTime() + DateUtil.HOUR_MILLSECONDES / 2));
            // request.setProcess("style/resize");
            URL url = client.generatePresignedUrl(request);
            System.out.println("url " + url.getFile());
        } catch (Exception e) {
            throw new AliyunException("jbb.error.exception.aliyun.error");
            // do nothiong
        }

    }


    @Test
    public void TestGetVerifyResult(){
        User user = userService.selectUserById(10000);
        qianChengService.getVerifyResult(user,"3");
    }


    @Test
    public void TestGetVerifyDate(){
        User user = userService.selectUserById(10000);
        XjlApplyRecord applyRecord = xjlApplyRecordDao.selectXjlApplyRecordByApplyId("3", null, false);
        long delayTime = PropertyManager.getLongProperty("mgt.xjl.checkapply.delayTime", 300000L);
        Timestamp startDate = applyRecord.getCreationDate();
        Timestamp endDate = DateUtil.calTimestamp(startDate.getTime(), delayTime);
        qianChengService.getVerifyData(user, startDate, endDate);
    }

    @Test
    public void testCheckApplyRecord(){
        qianChengService.checkUserApplyRecord();
    }


}
