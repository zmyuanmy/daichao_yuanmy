package com.jbb.mgt.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.mgt.core.service.WeiboService;
import com.jbb.server.common.util.HttpUtil;

@Service("WeiboService")
public class WeiboServiceImpl implements WeiboService {

    private static Logger logger = LoggerFactory.getLogger(WeiboServiceImpl.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public String shorten(String longUrl) {
        String tinyurl = "";
        try {
            String urlString = "http://suo.im/api.php?url=" + longUrl;
            HttpUtil.HttpResponse response
                = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_GET, urlString, HttpUtil.CONTENT_TYPE_JSON, null, null);
            String jsonStr = new String(response.getResponseBody());

            tinyurl = jsonStr.replace("{\"tinyurl\":\"", "");
            int index = tinyurl.indexOf("\",\"status");
            if (index > -1) {
                tinyurl = tinyurl.substring(0, index).replace("\\/", "/");
            }
            System.out.println(tinyurl);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        return tinyurl;
    }

}
