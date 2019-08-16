package com.jbb.mgt.changjiepay.util;

import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.HeLiPayException;
import com.jbb.server.common.util.DateUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class ChangJieUtil {

    private static String charset = "UTF-8";

    private static Logger logger = LoggerFactory.getLogger(ChangJieUtil.class);

    /**
     * 公共请求参数设置
     */
    public static Map<String, String> setCommonMap(Map<String, String> origMap) {
        // 2.1 基本参数
        origMap.put("Version", "1.0");
        // origMap.put("PartnerId", "200000140001"); // 测试环境商户号
        // origMap.put("PartnerId", "200000400059");//200000400059 生产参数
        String customerNumber = PropertyManager.getProperty("jbb.pay.changjie.customer.number");
        origMap.put("PartnerId", customerNumber);// 200000400059 生产测试参数

        origMap.put("InputCharset", charset);// 字符集
        String timeString = DateUtil.getOrderNum();
        origMap.put("TradeDate", timeString.substring(0, 8));// 商户请求时间
        origMap.put("TradeTime", timeString.substring(8, timeString.length()));// 商户请求时间
        origMap.put("Memo", null);
        return origMap;
    }

    /**
     * 加密，部分接口，有参数需要加密
     *
     * @param src 原值
     * @param publicKey 畅捷支付发送的平台公钥
     * @param charset UTF-8
     * @return RSA加密后的密文
     */
    public static String encrypt(String src, String publicKey, String charset) {
        try {
            byte[] bytes = RSA.encryptByPublicKey(src.getBytes(charset), publicKey);
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            logger.error("ChangJie encrypt error e= "+e);
        }
        return null;
    }

    /**
     * 向测试服务器发送post请求
     *
     * @param origMap 参数map
     * @param charset 编码字符集
     * @param MERCHANT_PRIVATE_KEY 私钥
     * @throws Exception
     */
    public static String gatewayPost(Map<String, String> origMap, String charset, String MERCHANT_PRIVATE_KEY) {
        try {
            String urlStr = PropertyManager.getProperty("jbb.pay.changjie.server.url");
            Map<String, String> sPara = buildRequestPara(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset);
            return buildRequest(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset, urlStr);
        } catch (Exception e) {
            logger.error("ChangJie gatewayPost error e= "+e);
        }
        return null;
    }

    /**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取钱包的处理结果 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
     * "",sParaTemp)
     *
     * @param strParaFileName 文件类型的参数名
     * @param strFilePath 文件路径
     * @param sParaTemp 请求参数数组
     * @return 钱包处理结果
     * @throws Exception
     */
    public static String buildRequest(Map<String, String> sParaTemp, String signType, String key, String inputCharset,
        String gatewayUrl) throws Exception {
        // 待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp, signType, key, inputCharset);
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        // 设置编码集
        request.setCharset(inputCharset);
        request.setMethod(HttpRequest.METHOD_POST);
        request.setParameters(generatNameValuePair(createLinkRequestParas(sPara), inputCharset));
        request.setUrl(gatewayUrl);
        if (sParaTemp.get("Service").equalsIgnoreCase("nmg_quick_onekeypay")
            || sParaTemp.get("Service").equalsIgnoreCase("nmg_nquick_onekeypay")) {
            return null;
        }

        // 返回结果处理
        HttpResponse response = httpProtocolHandler.execute(request, null, null);
        if (response == null) {
            return null;
        }
        String strResult = response.getStringResult();

        return strResult;
    }

    /**
     * 生成要请求参数数组
     *
     * @param sParaTemp 请求前的参数数组
     * @param signType RSA
     * @param key 商户自己生成的商户私钥
     * @param inputCharset UTF-8
     * @return 要请求的参数数组
     * @throws Exception
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String signType, String key,
        String inputCharset) throws Exception {
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        // 生成签名结果
        String mysign = "";
        if ("MD5".equalsIgnoreCase(signType)) {
            mysign = buildRequestByMD5(sPara, key, inputCharset);
        } else if ("RSA".equalsIgnoreCase(signType)) {
            mysign = buildRequestByRSA(sPara, key, inputCharset);
        }
        // 签名结果与签名方式加入请求提交参数组中
        sPara.put("Sign", mysign);
        sPara.put("SignType", signType);

        return sPara;
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("Sign") || key.equalsIgnoreCase("SignType")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     *
     * @param properties MAP类型数组
     * @return NameValuePair类型数组
     */
    public static NameValuePair[] generatNameValuePair(Map<String, String> properties, String charset)
        throws Exception {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            // nameValuePair[i++] = new NameValuePair(entry.getKey(),
            // URLEncoder.encode(entry.getValue(),charset));
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        return nameValuePair;
    }

    /**
     * 生成MD5签名结果
     *
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByMD5(Map<String, String> sPara, String key, String inputCharset)
        throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = MD5.sign(prestr, key, inputCharset);
        return mysign;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @param encode 是否需要urlEncode
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, boolean encode) {

        params = paraFilter(params);

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        String charset = params.get("InputCharset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /**
     * 生成RSA签名结果
     *
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByRSA(Map<String, String> sPara, String privateKey, String inputCharset)
        throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = RSA.sign(prestr, privateKey, inputCharset);
        return mysign;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @param encode 是否需要urlEncode
     * @return 拼接后字符串
     */
    public static Map<String, String> createLinkRequestParas(Map<String, String> params) {
        Map<String, String> encodeParamsValueMap = new HashMap<String, String>();
        List<String> keys = new ArrayList<String>(params.keySet());
        String charset = params.get("InputCharset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value;
            try {
                value = URLEncoder.encode(params.get(key), charset);
                encodeParamsValueMap.put(key, value);
            } catch (UnsupportedEncodingException e) {
                throw new HeLiPayException("createLinkRequestParas Error e = "+e);
            }
        }

        return encodeParamsValueMap;
    }

    /**
     * 向测试服务器发送post请求
     *
     * @param origMap 参数map
     * @param charset 编码字符集
     * @param MERCHANT_PRIVATE_KEY 私钥
     */
    public static void sendPost(Map<String, String> origMap, String charset, String MERCHANT_PRIVATE_KEY) {
        try {
            Map<String, String> sPara = buildRequestPara(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset);
            String urlStr = PropertyManager.getProperty("jbb.pay.changjie.server.url");
            String resultString = buildTransferRequest(sPara, "RSA", MERCHANT_PRIVATE_KEY, charset, urlStr);
        } catch (Exception e) {
            logger.error("ChangJie sendPost error e= "+e);
        }
    }

    /**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取钱包的处理结果 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
     * "",sParaTemp)
     *
     * @param sParaTemp 请求参数数组
     * @return 钱包处理结果
     * @throws Exception
     */
    public static String buildTransferRequest(Map<String, String> sParaTemp, String signType, String key,
        String inputCharset, String gatewayUrl) throws Exception {

        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        // 设置编码集
        request.setCharset(inputCharset);

        request.setMethod(HttpRequest.METHOD_POST);

        request.setParameters(generatNameValuePair(sParaTemp, inputCharset));
        request.setUrl(gatewayUrl);

        HttpResponse response = httpProtocolHandler.execute(request, null, null);
        if (response == null) {
            return null;
        }

        String strResult = response.getStringResult();
        /* //下载对账文件
        byte[] byteResult = response.getByteResult();
        Header[] responseHeaders = response.getResponseHeaders();
        String fileName = "";
        for(Header header : responseHeaders){
            if("content-disposition".equals(header.getName())){
                System.out.println(header.getValue());
                fileName = header.getValue();
            }
        }
        if(!"".equals(fileName)){
            File file  = new File("C:/"+fileName.substring(20));
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(byteResult);
        }*/

        return strResult;
    }
}
