package com.jbb.server.core.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.server.common.Home;
import com.jbb.server.core.domain.IntendRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class IouIntentionalUsersDaoTest {

    public static final int TESTUSERID = 1000000;
    public static final int TESTUSERID_2 = 1000001;
    public static final String TESTIOUCODE = "JBB2018020818248513792";

    @Autowired
    private IouIntentionalUserDao intentionRecordDao;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testIntentionRecord() {
        int status = 0;
        intentionRecordDao.saveIouIntentional(TESTUSERID, TESTIOUCODE, status);
        IntendRecord r = intentionRecordDao.getIntentionalUsersByUserId(TESTIOUCODE, TESTUSERID);
        assertEquals(r.getStatus(), (Integer)status);

        status = 1;
        intentionRecordDao.saveIouIntentional(TESTUSERID, TESTIOUCODE, status);
        r = intentionRecordDao.getIntentionalUsersByUserId(TESTIOUCODE, TESTUSERID);
        assertEquals(r.getStatus(), (Integer)status);
        status = 2;
        intentionRecordDao.saveIouIntentional(TESTUSERID, TESTIOUCODE, status);
        r = intentionRecordDao.getIntentionalUsersByUserId(TESTIOUCODE, TESTUSERID);
        assertNull(r);

        intentionRecordDao.saveIouIntentional(TESTUSERID, TESTIOUCODE, 0);
        intentionRecordDao.saveIouIntentional(TESTUSERID_2, TESTIOUCODE, 0);
        List<IntendRecord> list = intentionRecordDao.getIntentionalUsers(TESTIOUCODE);
        System.out.println(list.size());

        List<Integer> userIds = new ArrayList<Integer>();
        userIds.add(TESTUSERID);
        intentionRecordDao.rejectIouIntentionalUsers(TESTIOUCODE, userIds, null);
        r = intentionRecordDao.getIntentionalUsersByUserId(TESTIOUCODE, TESTUSERID);
        assertEquals(r.getStatus(), (Integer)1);

        r = intentionRecordDao.getIntentionalUsersByUserId(TESTIOUCODE, TESTUSERID_2);
        assertEquals(r.getStatus(), (Integer)0);

    }

}
