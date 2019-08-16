package com.jbb.mgt.boss;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64 {

    public static String getBase64ByByteArray(byte[] byteArray) {
        if (byteArray != null) {
            return new BASE64Encoder().encode(byteArray);
        } else {
            return null;
        }
    }

    public static byte[] getFormBase64ByString(String str) {
        byte[] byteArray = null;
        if (StringUtils.isNotEmpty(str)) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                byteArray = decoder.decodeBuffer(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return byteArray;
    }

}
