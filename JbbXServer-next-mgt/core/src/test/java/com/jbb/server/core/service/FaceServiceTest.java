package com.jbb.server.core.service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.domain.FaceVerifyResult;

import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class FaceServiceTest {

    @Autowired
    private FaceService faceService;

    
    public void testGetRandom() {
        // faceService.GetRandomNumber("00000");
    }

    
    public void testGetToken() {
        // faceService.getToken("00000", "123321123", "asdfasasasd");
        InputStream in = null;;
        try {
            in = new FileInputStream("D:\\photo\\IMG_2410.JPG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        try {
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] photoContent = out.toByteArray();

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = "孙志颖";
        /*try {
            str = new String(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        System.out.println("photoContent " + photoContent.length);
        FaceVerifyResult jsonObjverify = faceService.Verify("ab216361c651b9697dba34a13a128557", "20180524115940", str,
            "42098419941002751X", photoContent, "dadsadgzxd", 100000);
        System.out.println("jsonObjverify " + jsonObjverify.getVerifyData());
    }

   
    public void testVerify() {
        /*// 先获取数字，token_random_number
        JSONObject jsonrandom = faceService.GetRandomNumber("00000");
        if (jsonrandom != null) {
            String randomNumber = jsonrandom.getString("random_number");
            String tokenRandomNumber = jsonrandom.getString("token_random_number");
            // 上传视频 获取 token_video
            if (!StringUtil.isEmpty(randomNumber) && !StringUtil.isEmpty(tokenRandomNumber)) {
        
                InputStream invideo = null;;
                try {
                    invideo = new FileInputStream("D:\\photo\\IMG_2409.MOV");
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ByteArrayOutputStream outvideo = new ByteArrayOutputStream();
                byte[] buffervideo = new byte[1024 * 4];
                int i = 0;
                try {
                    while ((i = invideo.read(buffervideo)) != -1) {
                        outvideo.write(buffervideo, 0, i);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] videoContent = outvideo.toByteArray();
        
                try {
                    invideo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObj = faceService.ValidateVideo(tokenRandomNumber, "00000", 10000, videoContent);
                if (jsonObj != null) {
                    String token_video = jsonrandom.getString("token_video");
                    if (!StringUtil.isEmpty(token_video)) {
                        // 进行验证
                        InputStream in = null;;
                        try {
                            in = new FileInputStream("D:\\photo\\IMG_2410.JPG");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024 * 4];
                        int n = 0;
                        try {
                            while ((n = in.read(buffer)) != -1) {
                                out.write(buffer, 0, n);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        byte[] photoContent = out.toByteArray();
        
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String str = "孙志颖";
                        try {
                            str = new String(str.getBytes("utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
        
                        System.out.println("photoContent " + photoContent.length);
                        JSONObject jsonObjverify = faceService.Verify(token_video, "00000", str, "42098419941002751X",
                            photoContent, "dadsadgzxd");
                        System.out.println("jsonObjverify " + jsonObjverify.toString());
                    }
                }
            }
        
        }*/
    }

}
