package com.jbb.mgt.core.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.Location;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class UserLocationTest {

    @Autowired
    private UserLocationService userLocationService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    @Rollback
    public void userLocationTest() {

        // 先插入数据
        Location location = new Location();
        location.setUserId(10000);
        location.setLatitude(113.933226046);
        location.setLongitude(22.5391098673);
        location.setAccuracy(33.333333);
        location.setAltitude(44.444444);
        location.setAltitudeAccuracy(55.555555);
        location.setHeading(66.66666);
        location.setSpeed(78.77777);
        location.setAddress("广东省深圳市南山区");
        userLocationService.insertLocation(location);
        List<Location> list = userLocationService.getLocations(10000, null, null);
        assertTrue(list.size() >= 1);
        System.out.println(list);
        Location l = userLocationService.getLastLocation(10000);
        System.out.println(l);
    }

    @Test
    @Rollback
    public void getRegeoTest() {
        double latitude = 22.539;
        double longitude = 113.933;
        String address = userLocationService.getRegeo(latitude, longitude);
        System.out.println(address);
    }
}
