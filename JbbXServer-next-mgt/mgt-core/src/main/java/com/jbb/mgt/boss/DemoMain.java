package com.jbb.mgt.boss;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.jbb.mgt.core.domain.jsonformat.nwjr.request.MailListBackFlow;
import com.jbb.mgt.core.domain.jsonformat.nwjr.MailListBackFlowBody;
import com.jbb.mgt.core.domain.jsonformat.nwjr.PhoneBook;

public class DemoMain {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            // postHttpsRequest();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void postHttpsRequest(String jsonString, String functionCode) throws Exception {
        String sfUrl = "https://test.tianchengsys.com:5003/boss/uap";
        Map<String, Object> root = new HashMap<String, Object>();
        Map<String, Object> header = new HashMap<String, Object>();
        Map<String, Object> securityInfo = new HashMap<String, Object>();
        header.put("orgCode", "1023");
        header.put("transNo", "Trandsfsdf11");
        header.put("transDate", "2015-02-02 14:12:14");
        header.put("userName", "gold");
        header.put("userPassword", EncryptUtil.md5("gold@cc7836byd"));
        header.put("functionCode", "RCE_100900");
        String headerStr = JSONObject.toJSONString(header);

        String privateKey
            = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDXPNTZtj8kXHPIkjEZPGTpPDwrU9rfqMO3D1EWf/97hYIAJCOwAFc0LAj4tDzUrP+N0S2jr422yChoVHGOkQhdL7KwLx3Qp6FBLFpWQfm3Q0v8hA4KYhtdMGeYIJcjhbKs5/1IePPzeODduYVmEHuZ2Fe5aKpkOFrOet1QFkZJsnuPd8kb7zUe+QiMCBOmSro0SfhdhyqUfhJhJE4DWFzGWKFPfEy0wVg6nm7QnsKCzwG3nnLdd4p3UZ4oVYodLD5o19+NvboH9zRTu2bizviAmhSeMunw+1FAn9kqrhedFpMAyxIqH8fWlwV9OZKznwbWjxGd2j7y49XCQ37OC109AgMBAAECggEACklB8tWe1uIENoWmCwqz1+GlDn0HbMDn+Yul2GltkD2lJNSVUAf0Iu4nz5rtfUx3OhPRFo2BPEb8h1/hvWp499sbswfNaPdZ4X95Ib4tbPCuZPb6pMxjemShzsdf8bal/1Vm6Vu+bNhOBsqi8WnAPm30Y0v5OKjozg1ZHkayPTtP88PunwntXN/UmWAh0x95MshuVMgFF/Og7kdFYPQaMUVMVkuRRxQ1NfQ4x5iRGvTDKDsSaEh2tB8iqsLGYyKp41FWstFhwfrDHiF5yUnHX/yQVOXJ+XNDQ8SgfpJN9R9YJjFBYUsachic7j7veY/ZL3tvcVoBClV8FsiWGO8MAQKBgQDu+Lk9cs8iGH0I00qbmXTzo9ALMFD0MDxk/jFluGt033HPzV9l4wzla1CtFnoRuvA3ng1KguW4poIpjBQN2RnSiP3Y/colqEQzBSJMju3NiJsvy1PQYhsAtn2ncyW4XSED3Q2Y7fUVCEMIytBw8BxWBXHEcEFOYj2ytv445LS0wQKBgQDmkyhIEQzAZ9E2OSu3NkBVlT9P0NIDY97d8iKrdknULEGUE0kTEVaXQE9+t76gJTmCFxigEcCn6ySPl4+INPJL/6D40VWd6FIjkEQabb51CqL+3C38hZ7d3DTPC1RYGpStv0CdUWD+O5sDp/WUJ5yfN2X4wuTsSX60g4g5sK7bfQKBgQDX8s26mMBTaFu9GOsH11HXRcYgBkZxlBFPlmFfMZtxXuZDSayWruode6/I6lHfKdYf/GS4W7dp+JYRI3OP3As4tPDoINeG1hrjHBWVp2d/352VQCF6Yt1R9JdyNVz10Lqf+idqdkaN/gxNqrFYDbMdjQf04xgw1Bg7IqBzG0GtQQKBgDFNE9bc9moJiL1cWhGRow3pnKD3WIBrEVNJm8onv7+DOv5rZTuTgH1UveqtZTAe5X4AbD6FrTj6WVXupT0NfSVs4kFxmSnra+y4wycXghrKjHGMKLUO6RotJImEtaMlA9dmxm0c4m1Z11pfxm3ITn/ou4AGRCaVzGbSnQUg1zKNAoGBAIeTIwhyut4+lne19VJ+nkInvMZFAEI8gE8l+kllpVZBsvbh2fvI3uN28IsegEBBbM0rUIz2lZVHkEKyUYMdr4kb4OZaTm2WJZHDKpxj82UKq1be9DixcaxiXuFk2NhSY5HFD4fMggCKxZQaP2knTt60n9rD6g306JpyFjwO2Iy8";
        String sigValue = RSA.sign(headerStr, privateKey);
        securityInfo.put("signatureValue", sigValue);

        byte[] encBusiData = ThreeDes.encryptMode("AD905@!QLF-D25WEDA5!@#$%".getBytes(), jsonString.getBytes("UTF-8"));

        root.put("header", headerStr);
        root.put("busiData", Base64.getBase64ByByteArray(encBusiData));
        root.put("securityInfo", securityInfo);
        String message = JSONObject.toJSONString(root);

        System.out.println("==" + message);

        String res = HttpRequestUtil.sendJsonWithHttps(sfUrl, message);

        System.out.println("==" + res);
        JSONObject msgJSON = JSONObject.parseObject(res);
        String head = msgJSON.getString("header");
        if (!JSONObject.parseObject(head).getString("rtCode").equals("E0000000")) {
            System.out.println("==");
            return;
        }
        String public_key
            = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1zzU2bY/JFxzyJIxGTxk6Tw8K1Pa36jDtw9RFn//e4WCACQjsABXNCwI+LQ81Kz/jdEto6+NtsgoaFRxjpEIXS+ysC8d0KehQSxaVkH5t0NL/IQOCmIbXTBnmCCXI4WyrOf9SHjz83jg3bmFZhB7mdhXuWiqZDhaznrdUBZGSbJ7j3fJG+81HvkIjAgTpkq6NEn4XYcqlH4SYSROA1hcxlihT3xMtMFYOp5u0J7Cgs8Bt55y3XeKd1GeKFWKHSw+aNffjb26B/c0U7tm4s74gJoUnjLp8PtRQJ/ZKq4XnRaTAMsSKh/H1pcFfTmSs58G1o8Rndo+8uPVwkN+zgtdPQIDAQAB";
        String securityInfo1 = msgJSON.getString("securityInfo");
        String signatureValue = JSONObject.parseObject(securityInfo1).getString("signatureValue");
        boolean verifyFlag = RSA.verify(msgJSON.getString("header"), signatureValue, public_key);
        if (verifyFlag == true) {
            System.out.println("==");
            byte[] b64 = Base64.getFormBase64ByString(msgJSON.getString("busiData"));
            byte[] busiData = ThreeDes.decryptMode("AD905@!QLF-D25WEDA5!@#$%".getBytes(), b64);
            System.out.println("==" + new String(busiData, "UTF-8"));
        } else {
            System.out.println("==");
        }
    }
}
