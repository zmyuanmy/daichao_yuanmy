package com.jbb.mgt.boss;

public class MessageUtil {
    public static String getBusiData() {
        StringBuffer sb = new StringBuffer(
            "{\"realName\":\"AA\",\"identityCard\":\"362528198745421654\",\"mobile\":\"18612345678\",\"functionCode\":\"10002002\"}");
        return sb.toString();

    }

    public static void main(String[] args) {
        System.out.print(getBusiData());
    }
}
