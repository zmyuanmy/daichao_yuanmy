package com.jbb.mgt.core.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.ChannelStatisticDaily;
import com.jbb.mgt.core.domain.Channels;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class ChannelStatisticDailyServiceTest {

    @Autowired
    private ChannelStatisticDailyService service;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    // @Test
    public void getCounts() throws ParseException {
        System.out.println(service.getChannelStatisticDailys("2018-07-30", null, null, null));

    }

    @Test
    public void insertChannelsStatisticDaily() throws ParseException {

        int startDay = 14;
        for (int i = startDay; i < 15; i++) {
            String str = String.format("%02d", i);
            List<Channels> channels = service.getChannelStatisticDailys("2019-01-" + str, null, null, null);
            List<ChannelStatisticDaily> list = new ArrayList<ChannelStatisticDaily>();
            for (Channels channel : channels) {
                list.add(channel.getChannelStatisticDaily());
                System.out.println("=========================" + channel.getChannel().getChannelCode());
                System.out.println(channel.getChannelStatisticDaily().getCnt());
            }

            service.insertChannelsStatisticDaily(list);
        }
    }

    // @Test
    public void getstatisticByDate() throws ParseException {
        List<Channels> a = service.getstatisticByDate("2018-07-30", null, null);

    }

}
