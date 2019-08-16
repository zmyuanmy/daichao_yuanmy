package com.jbb.server.core.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GenericRequest;
import com.jbb.server.common.Home;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.AliyunException;
import com.jbb.server.common.util.DateUtil;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;

/**
 * Created by inspironsun on 2018/6/11
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class IouPhotoServerTest {

    @Autowired
    private AliyunService aliyunService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    public void testGetPhoto() {
        String bucketidcar = PropertyManager.getProperty("jbb.aliyu.oss.bucket.proof");
        try {
            byte[] photofinally = aliyunService.getImageBytes(bucketidcar, "6418393186811791.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getIouPhotoUrl() {

        String endpoint = PropertyManager.getProperty("jbb.aliyu.oss.bucket.endpoint");
        String accessId = PropertyManager.getProperty("jbb.aliyun.oss.accessKeyId");
        String accessKey = PropertyManager.getProperty("jbb.aliyun.oss.accessKeySecret");
        String bucket = PropertyManager.getProperty("jbb.aliyu.oss.bucket.proof");
        String style = PropertyManager.getProperty("jbb.aliyu.oss.style");
        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        try {
            client.getObjectMetadata(new GenericRequest(bucket, "6418393186811791.jpg"));
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, "6418393186811791.jpg");
            request.setExpiration(new Date(DateUtil.getCurrentTime() + DateUtil.HOUR_MILLSECONDES / 2));
            // request.setProcess("style/resize");
            URL url = client.generatePresignedUrl(request);
            System.out.println("url " + url.getFile());
        } catch (Exception e) {
            throw new AliyunException("jbb.error.exception.aliyun.error");
            // do nothiong
        }

    }
}
