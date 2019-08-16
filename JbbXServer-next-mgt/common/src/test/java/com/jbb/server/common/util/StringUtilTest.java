package com.jbb.server.common.util;

import org.junit.Test;

public class StringUtilTest {

    @Test
    public void testGenerateIoUId() {

        String id = null;
        for (int i = 0; i < 100; i++) {
            id = StringUtil.generateIoUId();
            System.out.println(id);
        }
    }
}
