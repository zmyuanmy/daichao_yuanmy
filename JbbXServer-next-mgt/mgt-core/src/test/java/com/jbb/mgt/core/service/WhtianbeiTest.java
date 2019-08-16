package com.jbb.mgt.core.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class WhtianbeiTest {

    @Autowired
    private DataWhtianbeiReportService service;

    @Test
    public void testDataWhtianbeiReportService() {
        String phone = "17876543213";
        String name = "狄彪树";
        String idcard = "450101197405207446";
        String s = service.getDataWhtianbeiReport(phone, name, idcard);
        assertNotNull(s);
        // System.out.println("=========="+s);
    }
    
    @Test
    public void testDataWhtianbeiReportService2() {
        String phone = "17876543213";
        String name = "狄彪树";
        String idcard = "450101197405207446";
        String s = service.getTianBeiReportstatic(phone, name, idcard);
        assertNotNull(s);
        // System.out.println("=========="+s);
    }
}
