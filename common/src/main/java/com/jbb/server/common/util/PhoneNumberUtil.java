package com.jbb.server.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jbb.server.common.PropertyManager;

public class PhoneNumberUtil {

    private static Pattern pattern = null;
    static {
        String regStr = PropertyManager.getProperty("jbb.phonenumber.reg");
        pattern = Pattern.compile(regStr);
    }
    private static String[] IPPFXS4 = {"1790", "1791", "1793", "1795", "1796", "1797", "1799"};
    private static String[] IPPFXS5 = {"12583", "12593", "12589", "12520", "10193", "11808"};
    private static String[] IPPFXS6 = {"118321"};

    public static boolean isValidPhoneNumber(String phoneNumber) {

        if (StringUtil.isEmpty(phoneNumber) || phoneNumber.length() != 11) {
            return false;
        }

        Matcher mat = pattern.matcher(phoneNumber);
        return mat.matches();
    }

    public static void main(String[] args) {
        System.out.println(isValidPhoneNumber("1111"));
    }

    // 去除号码区号 空格 -
    public static String trimTelNum(String telNum) {

        if (telNum == null || "".equals(telNum)) {
            return "";
        }

        String ippfx6 = substring(telNum, 0, 6);
        String ippfx5 = substring(telNum, 0, 5);
        String ippfx4 = substring(telNum, 0, 4);

        if (telNum.length() > 7
            && (substring(telNum, 5, 1).equals("0") || substring(telNum, 5, 1).equals("1")
                || substring(telNum, 5, 3).equals("400") || substring(telNum, 5, 3).equals("+86"))
            && (inArray(ippfx5, IPPFXS5) || inArray(ippfx4, IPPFXS4)))
            telNum = substring(telNum, 5);
        else if (telNum.length() > 8
            && (substring(telNum, 6, 1).equals("0") || substring(telNum, 6, 1).equals("1")
                || substring(telNum, 6, 3).equals("400") || substring(telNum, 6, 3).equals("+86"))
            && inArray(ippfx6, IPPFXS6))
            telNum = substring(telNum, 6);

        telNum = telNum.replace("-", "");
        telNum = telNum.replace(" ", "");

        if (substring(telNum, 0, 4).equals("0086"))
            telNum = substring(telNum, 4);
        else if (substring(telNum, 0, 3).equals("+86"))
            telNum = substring(telNum, 3);
        else if (substring(telNum, 0, 5).equals("00186"))
            telNum = substring(telNum, 5);

        return telNum;
    }

    protected static String substring(String s, int from) {
        try {
            return s.substring(from);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected static String substring(String s, int from, int len) {
        try {
            return s.substring(from, from + len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected static boolean inArray(String target, String[] arr) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        if (target == null) {
            return false;
        }
        for (String s : arr) {
            if (target.equals(s)) {
                return true;
            }
        }
        return false;
    }
}
