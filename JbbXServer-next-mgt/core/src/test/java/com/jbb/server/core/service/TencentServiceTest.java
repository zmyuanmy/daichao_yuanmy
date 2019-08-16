package com.jbb.server.core.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.server.common.Home;
import com.jbb.server.core.domain.LiveDetectForeResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class TencentServiceTest {

    @Autowired
    private TencentService tencentService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testSign() {
        String signStr = tencentService.sign();
        assertNotNull(signStr);
        System.out.println("testSign ==============");
        System.out.println(signStr);
    }
    
    @Test
    public void testGetFour() {
        String numStr = tencentService.liveGetFour();
        assertNotNull(numStr);
        System.out.println("testGetFour ==============");
        System.out.println(numStr);
    }
    
    //@Test 
    public void testLiveDetectFour(){
       int userId =1000000;
       File file = new File("/Users/VincentTang/Desktop/9370-2.mp4");
       
       Long filelength = file.length();  
       byte[] filecontent = new byte[filelength.intValue()];  
       try {  
           FileInputStream in = new FileInputStream(file);  
           in.read(filecontent);  
           in.close();  
       } catch (FileNotFoundException e) {  
           e.printStackTrace();  
       } catch (IOException e) {  
           e.printStackTrace();  
       }  
       
       LiveDetectForeResult result = tencentService.liveDetectFour(userId, "9370", filecontent);
       
       assertTrue(result.isValid());
       
    }

    
}