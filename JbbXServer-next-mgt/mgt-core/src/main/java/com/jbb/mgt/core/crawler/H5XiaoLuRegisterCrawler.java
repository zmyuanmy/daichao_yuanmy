package com.jbb.mgt.core.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jbb.mgt.core.dao.ChannelCrawlerUserDao;
import com.jbb.mgt.core.domain.ChannelCrawlerUser;
import com.jbb.mgt.core.service.ChuangLanService;
import com.jbb.mgt.server.core.util.SpringUtil;
import com.jbb.server.common.PropertyManager;

public class H5XiaoLuRegisterCrawler {

    private static String USERAGENAT =
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36";

    private static String CONTENTTYPE = "application/x-www-form-urlencoded";

    private static int PAGESIZE = 20;

    private static Logger logger = LoggerFactory.getLogger(H5XiaoLuRegisterCrawler.class);

    private ChannelCrawlerUserDao channelCrawlerUserDao;

    private CloseableHttpClient httpClient = null;

    private CookieStore cookieStore = new BasicCookieStore();

    private ChuangLanService chuanLanService;

    private static H5XiaoLuRegisterCrawler instance = new H5XiaoLuRegisterCrawler();

    private H5XiaoLuRegisterCrawler() {
        this.httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        this.channelCrawlerUserDao = SpringUtil.getBean("ChannelCrawlerUserDao", ChannelCrawlerUserDao.class);
        this.chuanLanService = SpringUtil.getBean("ChuangLanService", ChuangLanService.class);
    }

    public static H5XiaoLuRegisterCrawler getInstance() {
        return instance;
    }

    public void closeHttpClient() throws IOException {
        this.httpClient.close();
    }

    private CloseableHttpResponse login(int merchantId) throws ClientProtocolException, IOException {

        String userName = PropertyManager.getProperty("jbb.crawler.username." + merchantId);
        String password = PropertyManager.getProperty("jbb.crawler.password." + merchantId);
        String loginUrl = PropertyManager.getProperty("jbb.crawler.loginUrl." + merchantId);

        HttpPost httpPost = new HttpPost(loginUrl);
        httpPost.setHeader("Content-Type", CONTENTTYPE);
        httpPost.setHeader("User-Agent", USERAGENAT); // 设置请求头消息User-Agent

        // 装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("code", userName));
        nvps.add(new BasicNameValuePair("channelPassword", password));
        // 设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

        CloseableHttpResponse response = httpClient.execute(httpPost); // 执行http get请求

        Header[] headers = response.getAllHeaders();
        for (int i = 0; i < headers.length; i++) {
            System.out.println(headers[i].getName() + "==" + headers[i].getValue());
        }

        HttpEntity entity = response.getEntity(); // 获取返回实体

        System.out.println("Content-Type:" + entity.getContentType().getValue());
        response.close(); // response关闭

        return response;
    }

    private String postData(int merchantId, int pageSize, int pageNum, String stateTime, String endTime)
        throws ClientProtocolException, IOException {
        String dataUrl = PropertyManager.getProperty("jbb.crawler.dataUrl." + merchantId);

        HttpPost httpPost = new HttpPost(dataUrl);

        httpPost.setHeader("Content-Type", CONTENTTYPE);
        httpPost.setHeader("User-Agent", USERAGENAT); // 设置请求头消息User-Agent
        // 装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("pageNum", String.valueOf(pageNum)));
        nvps.add(new BasicNameValuePair("pageSize", String.valueOf(pageSize)));
        nvps.add(new BasicNameValuePair("channelId", "110"));
        nvps.add(new BasicNameValuePair("startTime", stateTime));
        nvps.add(new BasicNameValuePair("endTime", endTime));

        // 设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

        CloseableHttpResponse response = httpClient.execute(httpPost); // 执行http get请求
        HttpEntity entity = response.getEntity(); // 获取返回实体

        System.out.println("Content-Type:" + entity.getContentType().getValue());
        String content = null;
        if (entity != null) {
            System.out.println("--------------------------------------");
            content = EntityUtils.toString(entity, "UTF-8");
            System.out.println("Response content: " + content);

            System.out.println("--------------------------------------");
        }
        response.close(); // response关闭

        return content;
    }

    // return : 0 重新抓取此页， -1 终止， 1 - 再抓取一页
    private int readContent(String channelCode, Integer merchantId, String content, int pageNum,
        ChannelCrawlerUser lastUser, List<ChannelCrawlerUser> cUsers) throws ClientProtocolException, IOException {

        JSONObject obj = (JSONObject)JSONObject.parse(content);
        String codeEnum = obj.getString("codeEnum");
        int code = obj.getIntValue("code");

        if ("FAIL".equals(codeEnum)) {
            logger.warn(content);
            // 登录过时
            if (code == 216) {
                login(merchantId);
            }
            return 0;
        }

        if (!"SUCCESS".equals(codeEnum)) {
            return -1;
        }

        JSONObject data = obj.getJSONObject("data");
        JSONObject pageDto = data.getJSONObject("pageDto");
        // int total = pageDto.getIntValue("total");
        int pages = pageDto.getIntValue("pages");
        List<ChannelCrawlerUser> users = new ArrayList<ChannelCrawlerUser>();
        JSONArray list = pageDto.getJSONArray("list");
        for (int i = 0; i < list.size(); i++) {
            JSONObject o = list.getJSONObject(i);
            ChannelCrawlerUser cUser = new ChannelCrawlerUser(channelCode, merchantId, o.getString("id"),
                o.getString("realName"), o.getString("loginName"), o.getString("registTime"));
            if (lastUser == null || (cUser.getRegisterDate().getTime() >= lastUser.getRegisterDate().getTime()
                && !cUser.getuId().equals(lastUser.getuId()))) {
                users.add(cUser);
            }
        }
        if (users.size() == 0) {
            return -1;
        } else {
            cUsers.addAll(users);
            if (pageNum == pages) {
                return -1;
            }
            return (list.size() == users.size()) ? 1 : -1;
        }

    }

    private void sendAlertMessage() {
        String[] mobiles = PropertyManager.getProperties("jbb.crawler.alert.mobiles");
        if (mobiles == null || mobiles.length == 0) {
            return;
        }
        for (int i = 0; i < mobiles.length; i++) {
            chuanLanService.sendMsgCodeWithContent(mobiles[i], "【风控系统】采集美丽人生出错，请查看！H5XiaoLuRegisterCrawler");
        }

    }

    public void crawlRegisterData(Integer merchantId, String startDate, String endDate) {
        boolean enable = PropertyManager.getBooleanProperty("sys.crawler.merchant.enable." + merchantId, true);

        // 读取数据
        // 获取绑定的渠道
        String channelCode = PropertyManager.getProperty("sys.crawler.channel.merchant." + merchantId);
        if (channelCode == null) {
            channelCode = "jbbd";
        }

        // 是否开启抓取
        if (enable == false) {
            return;
        }

        try {

            // 登录
            login(merchantId);

            List<ChannelCrawlerUser> cUsersList = new ArrayList<ChannelCrawlerUser>();

            int pageNum = 1;

            ChannelCrawlerUser lastUser = channelCrawlerUserDao.getLastCrawlUser(channelCode);

            // 获取第一页数据
            String content = postData(merchantId, PAGESIZE, 1, startDate, endDate);

            // 读取并返回状态
            int status = readContent(channelCode, merchantId, content, pageNum, lastUser, cUsersList);

            // 是否抓取后续页的数据
            while (status != -1) {
                if (status == 1) {
                    pageNum++;
                }
                content = postData(merchantId, PAGESIZE, pageNum, startDate, endDate);
                status = readContent(channelCode, merchantId, content, pageNum, lastUser, cUsersList);
            }

            // 插入数据
            if (cUsersList != null && cUsersList.size() > 0) {
                channelCrawlerUserDao.insertChannelsStatisticDaily(cUsersList);
            }

            closeHttpClient();

        } catch (Exception e) {

            this.sendAlertMessage();
            logger.error(e.getMessage());

        }
    }

}
