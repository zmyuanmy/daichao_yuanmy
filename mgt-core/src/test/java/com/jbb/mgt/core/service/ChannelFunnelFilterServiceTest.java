package com.jbb.mgt.core.service;

import com.google.common.collect.Maps;
import com.jbb.mgt.core.domain.ChannelFunnel;
import com.jbb.mgt.core.domain.ChannelFunnelFilter;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.StringUtil;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.MapContext;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class ChannelFunnelFilterServiceTest {

    private static final JexlEngine JEXL = new JexlBuilder().cache(512).strict(true).silent(false).create();

    @Autowired
    private ChannelFunnelService channelFunnelService;

    @Autowired
    private ChannelFunnelFilterService channelFunnelFilterService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void channelFunnelServiceTest() {
        List<ChannelFunnel> list
            = channelFunnelService.getChannelFunnelCompare(null, null, null, "2019-05-13", null, null, null);

        List<ChannelFunnelFilter> filterList = channelFunnelFilterService.getChannelFunnelFilter(null);

        for (ChannelFunnel channelFunnel : list) {
            Map<String, Object> channelFunnelMap = beanToMap(channelFunnel);
            JexlContext context = new MapContext(channelFunnelMap);

            for (ChannelFunnelFilter filter : filterList) {
                if (!StringUtil.isEmpty(filter.getFilterScript())) {
                    boolean result = (Boolean)JEXL.createExpression(filter.getFilterScript()).evaluate(context);
                    if (result) {
                        channelFunnel.setStyle(filter.getStyle());
                        break;
                    }
                }
            }
        }
        System.out.println("---" + list.size());

    }

    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

}
