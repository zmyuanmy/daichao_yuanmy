package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.dao.SystemDao;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.server.core.util.OrgDataFlowSettingsUtil;
import com.jbb.mgt.server.core.util.PropertiesUtil;
import com.jbb.mgt.server.core.util.RegisterEventUtil;
import com.jbb.server.common.Home;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.shared.rs.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class RecommandSeviceTest {

    @Autowired
    private RecommandService recommandService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private SystemDao systemDao;

    @Autowired
    private OrganizationUserService organizationUserService;

    @Autowired
    private UserService userService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
        Home.settingsTest();

        PropertiesUtil.init();
        OrgDataFlowSettingsUtil.init();
        RegisterEventUtil.init();

    }

    @AfterClass
    public static void after() {
        OrgDataFlowSettingsUtil.destroy();
        PropertiesUtil.destroy();
        RegisterEventUtil.destroy();
    }

//    @Test
    public void testRecommand2Org2() {
        String channelCode = "jbbd";
        int userType = 2;
        // Channel channel = channelService.getChannelByCode(channelCode);
        Timestamp start = Util.parseTimestamp("2018-09-01T00:00:00", TimeZone.getDefault());
        Timestamp end = Util.parseTimestamp("2018-09-04T15:00:00", TimeZone.getDefault());

        // List<User> users =
        // userService.selectUserApplyDetails(channelCode, start, end, null, userType, true, true, true);
        List<User> users = new ArrayList<User>();
        User u = userService.selectUserById(15333, 1);
        users.add(u);

        if (!CommonUtil.isNullOrEmpty(users)) {
            for (User user : users) {
                // if("13060950694".equals(user.getPhoneNumber())) continue;
                recommandService.applyToOrg(user, 385, userType, channelCode);
            }
        }

    }

    @Test
    public void testRecommand2Organization() {
        String channelCode = "jbbd";
        int orgId = 163;
        int userType = 2;

        List<User> users = new ArrayList<User>();
        User u = userService.selectUserById(488718, 1);
        users.add(u);

        if (!CommonUtil.isNullOrEmpty(users)) {
            for (User user : users) {
                recommandService.applyToOrg(user, orgId, userType, channelCode);
            }
        }

    }

    // @Test
    public void testRecommand() throws InterruptedException {
        String channelCode = "DJtDXO";
        int userType = 2;
        // Channel channel = channelService.getChannelByCode(channelCode);
        Timestamp start = Util.parseTimestamp("2018-09-01T13:15:00", TimeZone.getDefault());
        Timestamp end = Util.parseTimestamp("2018-09-01T14:15:00", TimeZone.getDefault());

        List<User> users =
            userService.selectUserApplyDetails(channelCode, start, end, null, userType, true, true, true);

        // List<User> users = new ArrayList<User>();
        // User u = userService.selectUserById(526180, 1);
        // users.add(u);
        // users.add(u);
        // users.add(u);
        // users.add(u);
        // users.add(u);
        // users.add(u);
        // users.add(u);
        // users.add(u);
        // users.add(u);
        // users.add(u);

        if (!CommonUtil.isNullOrEmpty(users)) {
            for (User user : users) {

                System.out.println("============" + user.getUserId() + " : " + user.getUserName());

                boolean entryStatus = user.isMobileVerified() && userType == 1;

                String[] applyOrgs = null;
                if (user.getApplyOrgIds() != null) {
                    applyOrgs = user.getApplyOrgIds().split(",");
                }

                if (applyOrgs != null && applyOrgs.length >= 3) {
                    // 已经推荐过三家
                    continue;
                }

                Channel channel = user.getChannels();
                List<Organization> orgs = recommandService.getRecommandOrgs(user, channel, userType, entryStatus);

                if (orgs == null || orgs.size() == 0) {
                    continue;
                }
                int needSize = applyOrgs == null ? 3 : 3 - applyOrgs.length;
                int[] orgIds = new int[needSize];
                int i = 0;

                System.out.println("======RECOMMAND======" + user.getUserId() + " : " + user.getUserName());
                // 3. 返回 推荐的组织
                // 数量不够时，再推

                for (Organization org : orgs) {

                    System.out.println(
                        "============" + org.getOrgId() + " : " + org.getName() + " : " + org.getRecommandUserType());
                    orgIds[i++] = org.getOrgId();
                    if (i == needSize) {
                        break;
                    }

                }
                // 4. 提交， 目前是后台提交，后续做接口，前端调用过来提交
                recommandService.applyToOrgs(user, orgs);

                Thread.sleep((long)(Math.random() * 30 * 1000));
            }

        }

    }

    // @Test
    public void testFilterByGroupXor() {
        // String xorGroupsStr =
        // "{\"xorGroups\":[{\"groupName\":\"g1\",\"orgs\":[155,2]},{\"groupName\":\"g3\",\"orgs\":[156,157]},{\"groupName\":\"g2\",\"orgs\":[155,109]}]}";

        Set<Integer> toChooseOrgs = new HashSet<Integer>();
        toChooseOrgs.add(2);
        toChooseOrgs.add(146);
        toChooseOrgs.add(242);

        String xorGroupsStr = systemDao.getSystemPropertyValue("sys.policy.xor.dflow.groups");

        for (int i = 0; i < 100; i++) {
            // Set<Integer> toRecommandOrgs = recommandService.filterOrgsByGroupPolicy(toChooseOrgs, xorGroupsStr);
            Set<Integer> toRecommandOrgs = getRecommand(toChooseOrgs);
            System.out.println("=========Re===========");
            toRecommandOrgs.forEach(orgId -> System.out.println(orgId));
            System.out.println("=========RE ===========");
        }
    }

    private Set<Integer> getRecommand(Set<Integer> toChooseOrgs) {
        Map<String, Set<Integer>> map = new HashMap<String, Set<Integer>>();

        Set<Integer> a = new HashSet<Integer>();
        a.add(146);
        a.add(242);
        map.put("中正1推1", a);

        Set<Integer> b = new HashSet<Integer>();
        b.add(2);
        map.put("g1", b);

        Set<Integer> c = new HashSet<Integer>();
        c.add(2);
        c.add(242);
        map.put("中正", c);

        Set<Integer> setA = new HashSet<Integer>();
        Set<Integer> setD = new HashSet<Integer>();
        map.forEach((key, setC) -> {
            // 差值
            toChooseOrgs.removeAll(setC);

            // 差值
            Set<Integer> setE = new HashSet<Integer>();
            setE.addAll(setC);
            setE.removeAll(setD);

            if (setE.size() == 0) {
                return;
            }

            // 先把一个用户U
            Integer u = getRandomElement(setE);

            System.out.println("u=" + u);

            setA.removeAll(setE);
            setA.add(u);

            setD.addAll(setE);
            setD.remove(u);

        });
        // 合并
        toChooseOrgs.addAll(setA);
        return toChooseOrgs;
    }

    public static <E> E getRandomElement(Set<E> set) {
        int index = (int)(Math.random() * set.size());
        int i = 0;
        for (E e : set) {
            if (i == index) {
                return e;
            }
            i++;
        }
        return null;
    }
}
