package com.jbb.mall.core.service;

import com.jbb.mall.core.dao.domain.Categorie;
import com.jbb.mall.core.dao.domain.Classification;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.server.common.Home;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class CategorieServiceTest {

    @Autowired
    private CategorieService service;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void gecategorie() {
        List<Classification> list
            = service.getClassificationsByclassification("flower", new String[] {"混搭2","混搭1"}, true);
    }
}
