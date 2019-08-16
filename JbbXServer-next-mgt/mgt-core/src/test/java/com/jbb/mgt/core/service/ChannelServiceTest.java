package com.jbb.mgt.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.ChannelSimpleInfo;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class ChannelServiceTest {

    @Autowired
    private ChannelService channelService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testChannelService() {

        // 先删除所有测试数据
        List<Channel> all = channelService.getChannels("test", 1, DateUtil.getCurrentTimeStamp(),
            DateUtil.getCurrentTimeStamp(), 0, null, null, null, null, true, false, null, false, false, null, null);
        for (Channel c : all) {
            channelService.deleteChannel(c.getChannelCode());
        }

        // 先插入数据
        Channel channel = new Channel();
        String code = StringUtil.randomAlphaNum(5);
        channel.setChannelCode(code);
        channel.setChannelUrl("http://jiebangbang/jie/?code=" + code);
        channel.setChannelName("test channel 1");
        channel.setCreator(1);
        channel.setReceiveMode(1);
        channelService.createChannal(channel);

        // 获取数据并验证
        Channel channel2 = channelService.getChannelByCode(code);
        assertEquals(channel.getChannelCode(), channel2.getChannelCode());
        assertEquals(channel.getChannelName(), channel2.getChannelName());
        assertEquals(channel.getChannelUrl(), channel2.getChannelUrl());
        assertEquals(channel.getCreator(), channel2.getCreator());

        // 插入第二条数据
        code = StringUtil.randomAlphaNum(5);
        channel.setChannelCode(code);
        channel.setChannelUrl("http://jiebangbang/jie/?code=" + code);
        channel.setChannelName("test channel 2");
        channel.setCreator(2);
        channelService.createChannal(channel);

        // 插入第三条条数据
        code = StringUtil.randomAlphaNum(5);
        channel.setChannelCode(code);
        channel.setChannelUrl("http://jiebangbang/jie/?code=" + code);
        channel.setChannelName("test channel 3");
        channel.setCreator(3);
        channelService.createChannal(channel);

        // 获取渠道列表， 返回3条数据
        List<Channel> channels = channelService.getChannels("test", 1, DateUtil.getCurrentTimeStamp(),
            DateUtil.getCurrentTimeStamp(), 0, null, null, null, null, false, false, null, false, false, null, null);
        assertTrue(channels.size() == 3);

        // 更新数据
        String newName = "test channel 3 update";
        channel.setChannelName(newName);
        channel2 = channelService.getChannelByCode(code);
        assertEquals(channel.getCreator(), channel2.getCreator());

        // 删除最近一条插入的数据
        channelService.deleteChannel(code);

        // 再获取数据，应该返回长度为2
        channels = channelService.getChannels("test", 1, DateUtil.getCurrentTimeStamp(), DateUtil.getCurrentTimeStamp(),
            0, null, null, null, null, false, false, null, false, false, null, null);
        assertTrue(channels.size() == 2);
    }

    @Test
    public void getChannel() {
        Channel channel = channelService.checkPhoneNumberAndPassword("101", "12345678");
        /*List<ChannelStatistic> list
        = channelService.selectChannelStatisticS(null, 10014,
                null, null, null, null, false, false);*/
        /*List<Channel> list
            = channelService.getChannels(null, 1, null, null, 1, null, false, false, null, true, false, null, false);
        System.out
            .println("===================================================" + list.get(2).getStatistic().getClickCnt());
        System.out
            .println("===================================================" + list.get(2).getStatistic().getClickCnt1());
        System.out
            .println("===================================================" + list.get(2).getStatistic().getClickCnt2());
        System.out.println(
            "===================================================" + list.get(2).getStatistic().getRegisterCnt());
        System.out.println(
            "===================================================" + list.get(2).getStatistic().getRegisterCnt1());
        System.out.println(
            "===================================================" + list.get(2).getStatistic().getRegisterCnt2());*/
        // ChannelStatistic statistic = list.get(0).getStatistic();
    }

    @Test
    public void testGroupName() {
        List<Channel> list = channelService.getChannels(null, 1, null, null, null, null, false, false, null, true,
            false, null, false, false, null, null);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testGroupName2() {
        List<ChannelSimpleInfo> list = channelService.getChannelNamesAndCodes(1, null, "");
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testXjlChannels() {
        Timestamp timestamp = new Timestamp(1539705600000L);
        Timestamp timestamp2 = new Timestamp(1539792000000L);
        List<Channel> list = channelService.getXjlChannels(null, 1, timestamp, timestamp2);

    }

    @Test
    public void testChannels2() {
        Channel channel = new Channel();
        channel.setCompanyRequired(true);
        channel.setChannelName("JFsc2l");
        channel.setChannelCode("JFsc2l");
        channel.setCreator(21);
        // channelService.createChannal(channel);
        // channelService.updateChannle(channel);
        Channel channelByCode = channelService.getChannelByCode("RivW3g");
    }
}
