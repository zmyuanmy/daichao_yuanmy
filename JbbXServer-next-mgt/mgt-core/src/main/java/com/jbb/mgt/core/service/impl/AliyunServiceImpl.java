package com.jbb.mgt.core.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigRequest;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.mgt.core.dao.MessageCodeDao;
import com.jbb.mgt.core.domain.AliPolicy;
import com.jbb.mgt.core.domain.IPAddressInfo;
import com.jbb.mgt.core.service.AliyunService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.AliyunException;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.HttpUtil.HttpResponse;
import com.jbb.server.common.util.StringUtil;

import net.sf.json.JSONObject;

@Service("aliyunService")
public class AliyunServiceImpl implements AliyunService {
    private static Logger logger = LoggerFactory.getLogger(AliyunServiceImpl.class);

    public static String PHOTO_FILE_PREFIX = "photo_";

    public static String BUCKET_ORG_PHOTOS = "jbb-mgt-org-photos";
    public static String BUCKET_USER_PHOTOS = "jbb-mgt-user-photos";
    public static String BUCKET_VIDEO_AND_PIC = "jbb-video-and-pic";
    public static String BUCKET_USER_AVATAR_V2 = "jbb-users-avatar-v2";

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MessageCodeDao messageCodeDao;

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void sendSms(String phoneNumber, String templateCode, Map<String, String> params) {

        // 如果是测试账户，直接返回
        if (PropertyManager.contains("jbb.test.accounts", phoneNumber)) {
            logger.info("Test account require msgCode, phoneNumber=" + phoneNumber + ",templateCode=" + templateCode);
            return;
        }

        // 初始化ascClient需要的几个参数
        final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
        // 替换成你的AK
        final String accessKeyId = PropertyManager.getProperty("jbb.aliyun.sms.accessKeyId");// 你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = PropertyManager.getProperty("jbb.aliyun.sms.accessKeySecret");// 你的accessKeySecret，参考本文档步骤2
        // 初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            logger.error(e.toString());
            throw new AliyunException();
        }

        IAcsClient acsClient = new DefaultAcsClient(profile);
        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        // 使用post提交
        request.setMethod(MethodType.POST);
        // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phoneNumber);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(PropertyManager.getProperty("jbb.aliyun.sms.signName"));
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        if (!CommonUtil.isNullOrEmpty(params)) {
            JSONObject jsonObject = JSONObject.fromObject(params);
            request.setTemplateParam(jsonObject.toString());
        }
        // 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");
        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        // request.setOutId("yourOutId");
        // 请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                logger.debug("send message success");
            } else {
                logger.warn(sendSmsResponse.getMessage());
                throw new AliyunException("jbb.error.exception.aliyun.limit", sendSmsResponse.getMessage());
            }
        } catch (ServerException e) {
            logger.error(e.toString());
            throw new AliyunException();
        } catch (ClientException e) {
            logger.error(e.toString());
            throw new AliyunException();
        }
    }

    @Override
    public void putObject(String bucket, String key, String content) {
        putObject(bucket, key, content.getBytes(), null);
    }
    
    @Override
    public void deleteObject(String bucket, String key) {
     // endpoint以杭州为例，其它region请按实际情况填写
        String endpoint = PropertyManager.getProperty("jbb.aliyu.oss.bucket.endpoint");
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
        String accessKeyId = PropertyManager.getProperty("jbb.aliyun.oss.accessKeyId");
        String accessKeySecret = PropertyManager.getProperty("jbb.aliyun.sms.accessKeySecret");
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 上传
        ossClient.deleteObject(bucket, key);
        // 关闭client
        ossClient.shutdown();
    }

    @Override
    public void putObject(String bucket, String key, byte[] content, String contentType) {
        // endpoint以杭州为例，其它region请按实际情况填写
        String endpoint = PropertyManager.getProperty("jbb.aliyu.oss.bucket.endpoint");
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
        String accessKeyId = PropertyManager.getProperty("jbb.aliyun.oss.accessKeyId");
        String accessKeySecret = PropertyManager.getProperty("jbb.aliyun.sms.accessKeySecret");
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        ObjectMetadata metadata = null;
        if (StringUtil.isEmpty(contentType)) {
            metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
        }
        // 上传
        ossClient.putObject(bucket, key, new ByteArrayInputStream(content), metadata);
        // 关闭client
        ossClient.shutdown();
    }

    @Override
    public String getObject(String bucket, String key) throws IOException {
        // endpoint以杭州为例，其它region请按实际情况填写
        String endpoint = PropertyManager.getProperty("jbb.aliyu.oss.bucket.endpoint");
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
        String accessKeyId = PropertyManager.getProperty("jbb.aliyun.oss.accessKeyId");
        String accessKeySecret = PropertyManager.getProperty("jbb.aliyun.sms.accessKeySecret");
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucket, key);
        // 读Object内容
        System.out.println("Object content:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        // 关闭client
        ossClient.shutdown();
        return buffer.toString();
    }

    @Override
    public IPAddressInfo getIPAddressInfo(String ipAddress) {
        if (StringUtil.isEmpty(ipAddress)) {
            return null;
        }
        String appcode = PropertyManager.getProperty("jbb.api.ip.appcode");
        String url = PropertyManager.getProperty("jbb.api.ip.url");
        String[][] requestProperties = {{"Authorization", "APPCODE " + appcode}};
        url += "?ip=" + ipAddress;
        try {
            HttpResponse response
                = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_GET, url, HttpUtil.CONTENT_TYPE_JSON, null, requestProperties);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                IPAddressInfo rsp = mapper.readValue(new String(response.getResponseBody()), IPAddressInfo.class);
                if (IPAddressInfo.SUCCESS_STATUS.equals(rsp.getStatus())) {
                    return rsp;
                } else {
                    logger.debug("getIPAddressInfo response = " + new String(response.getResponseBody()));
                    return null;
                }

            } else {
                logger.debug("getIPAddressInfo response code = " + response.getResponseCode());
            }

        } catch (IOException e) {
            logger.debug("ip address = " + ipAddress + " , getIPAddressInfo response with error, " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AliPolicy getPostPolicy(int userId, String filePrifix) {
        String endpoint = PropertyManager.getProperty("jbb.aliyu.oss.bucket.endpoint");
        String accessId = PropertyManager.getProperty("jbb.aliyun.oss.accessKeyId");
        String accessKey = PropertyManager.getProperty("jbb.aliyun.oss.accessKeySecret");
        String bucket = null;
        bucket = PropertyManager.getProperty("jbb.aliyu.oss.bucket.mgt.photos");
        String dir = filePrifix + String.valueOf(userId);
        String host = "http://" + bucket + "." + endpoint;
        OSSClient client = new OSSClient(endpoint, accessId, accessKey);

        try {
            long expireTime = 3000;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 5242880);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);
            return new AliPolicy(accessId, encodedPolicy, postSignature, dir, host, expireEndTime / 1000);
        } catch (Exception e) {
            throw new AliyunException();
        }
    }

    @Override
    public void sendCode(String phoneNumber, String channelCode, String signName, String signTemplate) {
        // 如果是测试账户，直接返回
        if (PropertyManager.contains("jbb.test.accounts", phoneNumber)) {
            logger.info("Test account require msgCode, phoneNumber=" + phoneNumber);
            return;
        }

        // 初始化ascClient需要的几个参数
        final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
        // 替换成你的AK
        final String accessKeyId = PropertyManager.getProperty("jbb.aliyun.sms.accessKeyId");// 你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = PropertyManager.getProperty("jbb.aliyun.sms.accessKeySecret");// 你的accessKeySecret，参考本文档步骤2
        // 初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            logger.error(e.toString());
            throw new AliyunException();
        }

        String msgCode = StringUtil.getRandomnum(4);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        // 使用post提交
        request.setMethod(MethodType.POST);
        // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phoneNumber);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(signTemplate);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{\"code\":" + msgCode + "}");
        // 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");
        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        // request.setOutId("yourOutId");
        // 请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                long time = System.currentTimeMillis();
                messageCodeDao.saveMessageCode(phoneNumber, channelCode, msgCode, new Timestamp(time),
                    DateUtil.calTimestamp(time, 5 * 60 * 1000));
            } else {
                logger.warn(sendSmsResponse.getMessage());
                throw new AliyunException("jbb.error.exception.aliyun.limit", sendSmsResponse.getMessage());
            }
        } catch (ServerException e) {
            logger.error(e.toString());
            throw new AliyunException();
        } catch (ClientException e) {
            logger.error(e.toString());
            throw new AliyunException();
        }

    }

    @Override
    public void sendCode(String phoneNumber, String channelCode) {
        String signName = PropertyManager.getProperty("jbb.aliyun.sms.signName");
        String signTemplate = PropertyManager.getProperty("jbb.aliyun.sms.templateCode");
        sendCode(phoneNumber, channelCode, signName, signTemplate);
    }

    @Override
    public void afsCheck(String sessionId, String sig, String token, String scene, String remoteIp) {
        verifyCode(sessionId, sig, token, scene);
        // 替换成你的AK
        final String accessKeyId = PropertyManager.getProperty("jbb.aliyun.code.accessKeyId");// 你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = PropertyManager.getProperty("jbb.aliyun.code.accessKeySecret");// 你的accessKeySecret，参考本文档步骤2
        final String appKey = PropertyManager.getProperty("jbb.aliyun.code.appkey");// 你的appKey

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "afs", "afs.aliyuncs.com");
        } catch (ClientException e) {
            logger.error(e.toString());
            throw new AliyunException();
        }

        IAcsClient client = new DefaultAcsClient(profile);

        AuthenticateSigRequest request = new AuthenticateSigRequest();
        request.setSessionId(sessionId);// 必填参数，从前端获取，不可更改，android和ios只变更这个参数即可，下面参数不变保留xxx
        request.setSig(sig);// 必填参数，从前端获取，不可更改
        request.setToken(token);// 必填参数，从前端获取，不可更改
        request.setScene(scene);// 必填参数，从前端获取，不可更改
        request.setAppKey(appKey);// 必填参数，后端填写
        request.setRemoteIp(remoteIp);// 必填参数，后端填写

        try {
            AuthenticateSigResponse response = client.getAcsResponse(request);
            if (response.getCode() != null && response.getCode() == 100) {
                logger.debug("smart verify code success status:" + response.getCode());
            } else {
                logger.warn("smart verify code msg:" + response.getMsg());
                logger.info("smart verify code status:" + response.getCode());
                throw new AliyunException(response.getMsg());
            }
        } catch (ServerException e) {
            logger.error(e.toString());
            throw new AliyunException();
        } catch (ClientException e) {
            logger.error(e.toString());
            throw new AliyunException();
        }
    }

    private void verifyCode(String sessionId, String sig, String token, String scene) {
        if (StringUtil.isEmpty(sessionId)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "sessionId");
        }
        if (StringUtil.isEmpty(sig)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "sig");
        }
        if (StringUtil.isEmpty(token)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "token");
        }
        if (StringUtil.isEmpty(scene)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "scene");
        }

    }

    @Override
    public byte[] getImageBytesWithStyle(String bucket, String key, String style) throws IOException {
        // endpoint以杭州为例，其它region请按实际情况填写
        String endpoint = PropertyManager.getProperty("jbb.aliyu.oss.bucket.endpoint");
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
        String accessKeyId = PropertyManager.getProperty("jbb.aliyun.oss.accessKeyId");
        String accessKeySecret = PropertyManager.getProperty("jbb.aliyun.sms.accessKeySecret");
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        GetObjectRequest request = new GetObjectRequest(bucket, key);

        // 按样式处理图片
        if (style != null) {
            request.setProcess(style);
        }

        OSSObject ossObject = ossClient.getObject(request);

        // 读Object内容
        System.out.println("Object content:");
        InputStream is = ossObject.getObjectContent();

        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = is.read(buff, 0, 100)) > 0) {
            byteArrayStream.write(buff, 0, rc);
        }
        byteArrayStream.close();
        // 关闭client
        ossClient.shutdown();
        return byteArrayStream.toByteArray();
    }

    @Override
    public byte[] getImageBytes(String bucket, String key) throws IOException {
        return this.getImageBytesWithStyle(bucket, key, null);
    }

}
