package com.jbb.server.core.service.impl;

import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.ApiCallLimitException;
import com.jbb.server.common.exception.ExecutionException;
import com.jbb.server.common.lang.LanguageHelper;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.HttpUtil.HttpResponse;
import com.jbb.server.common.util.IOUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.dao.AccountDao;
import com.jbb.server.core.dao.UserVerifyDao;
import com.jbb.server.core.domain.*;
import com.jbb.server.core.service.AliyunService;
import com.jbb.server.core.service.FaceService;
import com.jbb.server.core.service.UserEventLogService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;

@Service("FaceService")
public class FaceServiceImpl implements FaceService {

    private static Logger logger = LoggerFactory.getLogger(FaceServiceImpl.class);
    @Autowired
    private AliyunService aliyunService;

    @Autowired
    private UserVerifyDao userVerifyDao;

    @Autowired
    private UserEventLogService userEventLogService;

    @Autowired
    private AccountDao acconutDao;

    @Override
    public FaceRandomResult GetRandomNumber(String bizNo) {
        logger.debug("> GetRandomNumber() bizNo = " + bizNo);
        String apiKey = PropertyManager.getProperty("jbb.face.apikey");
        String apiSecret = PropertyManager.getProperty("jbb.face.apisecret");
        String urlStr = PropertyManager.getProperty("jbb.face.randomnumber.url");
        FaceRandomResult result = null;
        try {
            HttpResponse response =
                postFaceApi(apiKey, apiSecret, bizNo, urlStr, null, null, null, 0, null, null, null, null, null, null);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                byte[] byteArray = response.getResponseBody();
                if (byteArray == null || byteArray.length < 1) {
                    throw new IllegalArgumentException("this byteArray must not be null or empty");
                } else {
                    String strRead = new String(byteArray, "utf-8");
                    logger.debug("content = " + strRead);
                    JSONObject jsonObject = JSONObject.fromObject(strRead);
                    result = new FaceRandomResult();
                    result.setBizNo(jsonObject.getString("biz_no"));
                    result.setRandomNumber(jsonObject.getString("random_number"));
                    result.setRequestId(jsonObject.getString("request_id"));
                    result.setTimeUsed(jsonObject.getInt("time_used"));
                    result.setTokenRandomNumber(jsonObject.getString("token_random_number"));
                    result.setRandomData(strRead);
                }
            } else {
                logger.debug("response code = " + response.getResponseCode());
                logger.debug("response content = " + new String(response.getResponseBody()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("< GetRandomNumber() ");
        return result;
    }

    @Override
    public FaceValidateVideoResult ValidateVideo(String tokenRandomNumber, String bizNo, int userId,
        byte[] videoContent) {
        logger.debug("> ValidateVideo() userId = " + userId);
        String apiKey = PropertyManager.getProperty("jbb.face.apikey");
        String apiSecret = PropertyManager.getProperty("jbb.face.apisecret");
        String urlStr = PropertyManager.getProperty("jbb.face.validatevideo.url");
        String returnImage = PropertyManager.getProperty("jbb.face.returnimage");
        FaceValidateVideoResult result = null;
        try {
            HttpResponse response = postFaceApi(apiKey, apiSecret, bizNo, urlStr, tokenRandomNumber, videoContent,
                returnImage, userId, null, null, null, null, null, null);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                byte[] byteArray = response.getResponseBody();
                if (byteArray == null || byteArray.length < 1) {
                    throw new IllegalArgumentException("this byteArray must not be null or empty");
                } else {
                    String strRead = new String(byteArray, "utf-8");
                    logger.debug("content = " + strRead);
                    JSONObject jsonObject = JSONObject.fromObject(strRead);
                    result = new FaceValidateVideoResult();
                    result.setBizNo(jsonObject.getString("biz_no"));
                    result.setImageBest(jsonObject.getString("image_best"));
                    result.setRequestId(jsonObject.getString("biz_no"));
                    result.setTimeUsed(jsonObject.getInt("time_used"));
                    result.setTokenVideo(jsonObject.getString("token_video"));
                    // 保存返回的图像
                    aliyunService.putObject(AliyunServiceImpl.BUCKET_VIDEO_AND_PIC, "photo_" + userId,
                        jsonObject.getString("image_best"));
                    //在json值中不保存图片的信息，因为太大了
                    jsonObject.put("image_best", "");
                    result.setValidateVideoData(jsonObject.toString());
                }
            } else {
                result = new FaceValidateVideoResult();
                byte[] byteArray = response.getResponseBody();
                String strRead = new String(byteArray, "utf-8");
                logger.debug("content = " + strRead);
                JSONObject jsonObject = JSONObject.fromObject(strRead);
                String errorMessage = jsonObject.getString("error_message");
                String message = "";
                if (errorMessage != null) {
                    if (errorMessage.indexOf(":") >= 0) {
                        message = LanguageHelper.getLocalizedMessage("jbb.face.errormessage." + errorMessage.substring(0, errorMessage.indexOf(":")), "zh");
                    }else{
                        message = LanguageHelper.getLocalizedMessage("jbb.face.errormessage." + errorMessage, "zh");
                    }
                } else {
                    // 未知错误
                    message = PropertyManager.getProperty("jbb.face.errormessage.unknowerror");
                }

                result.setErrorMessage(message);
                logger.debug("response code = " + response.getResponseCode());
                logger.debug("response content = " + new String(response.getResponseBody()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.debug("< ValidateVideo()");
        return result;
    }

    @Override
    public FaceVerifyResult Verify(String token, String bizNo, String idcardName, String idcardNumber,
        byte[] photoContent, String uuid, int userId) {
        logger.debug("> Verify()");
        String apiKey = PropertyManager.getProperty("jbb.face.apikey");
        String apiSecret = PropertyManager.getProperty("jbb.face.apisecret");
        String urlStr = PropertyManager.getProperty("jbb.face.verify.url");
        String comparisonType = PropertyManager.getProperty("jbb.face.comparisontype");
        FaceVerifyResult result = null;
        int sim = PropertyManager.getIntProperty("jbb.tencent.livedetectfour.sim", 75);
        try {
            HttpResponse response = postFaceApi(apiKey, apiSecret, bizNo, urlStr, null, null, null, 0, token,
                comparisonType, idcardName, idcardNumber, uuid, photoContent);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                if (logger.isDebugEnabled()) {
                    logger.debug("response = " + new String(response.getResponseBody()));
                }
                byte[] byteArray = response.getResponseBody();
                if (byteArray == null || byteArray.length < 1) {
                    throw new IllegalArgumentException("this byteArray must not be null or empty");
                } else {
                    String strRead = new String(byteArray, "utf-8");
                    logger.debug("content = " + strRead);
                    JSONObject jsonObject = JSONObject.fromObject(strRead);
                    result = new FaceVerifyResult();
                    result.setBizNo(jsonObject.getString("biz_no"));
                    result.setRequestId(jsonObject.getString("request_id"));
                    result.setTimeUsed(jsonObject.getInt("time_used"));
                    String liveness = jsonObject.getString("liveness");
                    result.setVerifyData(strRead);
                    String resultFaceid = "";
                    if (strRead.indexOf("result_faceid") >= 0) {
                        resultFaceid = jsonObject.getString("result_faceid");
                    }
                    if (!StringUtil.isEmpty(liveness)) {
                        JSONObject jsonliveness = JSONObject.fromObject(liveness);
                        if (liveness.indexOf("face_genuineness") >= 0) {
                            result.setFaceGenuineness(jsonliveness.getString("face_genuineness"));
                        } else {
                            result.setFaceGenuineness("");
                        }
                        if (liveness.indexOf("procedure_validation") >= 0) {
                            result.setProcedureNalidation(jsonliveness.getString("procedure_validation"));
                        } else {
                            result.setProcedureNalidation("");
                        }
                    }
                    String message = "";
                    if (!StringUtil.isEmpty(resultFaceid)) {
                        JSONObject jsonresultFaceid = JSONObject.fromObject(resultFaceid);
                        double simjson = jsonresultFaceid.getDouble("confidence");
                        if (result.getFaceGenuineness().equals("PASSED")
                            && result.getProcedureNalidation().equals("PASSED")) {
                            if (simjson >= sim) {
                                //实名验证第二步 实名验证
                                userVerifyDao.saveUserVerifyResult(userId, Constants.VERIFY_TYPE_REALNAME, 2, true);
                                // 实名验证第三步，视频唇语核身 通过
                                userVerifyDao.saveUserVerifyResult(userId, Constants.VERIFY_TYPE_VIDEO, 1, true);
                                result.setFlagVerify(true);
                                result.setSim((int)simjson);
                                return result;
                            } else if (simjson > 0 && simjson < sim) {
                                message = LanguageHelper.getLocalizedMessage("jbb.error.live.detect.code.sim", "zh");
                                result.setSim((int)simjson);
                                result.setFlagVerify(false);
                                result.setError_message(message);
                                return result;
                            } else {
                                message = LanguageHelper.getLocalizedMessage("jbb.face.errormessage.unknowerror", "zh");
                                result.setFlagVerify(false);
                                result.setError_message(message);
                                return result;
                            }

                        } else {
                            // 不通过假脸验证
                            String compareMsg = "";
                            String liveVideoMsg = "";
                            if (result.getProcedureNalidation() != null
                                && !result.getProcedureNalidation().equals("")) {
                                compareMsg = LanguageHelper.getLocalizedMessage(
                                    "jbb.face.errormessage." + result.getProcedureNalidation(), "zh");
                            }
                            if (result.getFaceGenuineness() != null && !result.getFaceGenuineness().equals("")) {
                                liveVideoMsg = LanguageHelper
                                    .getLocalizedMessage("jbb.face.errormessage." + result.getFaceGenuineness(), "zh");
                            }

                            message = compareMsg + "\r\n" + liveVideoMsg;
                            result.setError_message(message);
                            result.setFlagVerify(false);
                            return result;
                        }
                    } else {
                        // 不通过
                        message = LanguageHelper
                            .getLocalizedMessage("jbb.face.errormessage." + result.getProcedureNalidation(), "zh");
                        result.setFlagVerify(false);
                        result.setError_message(message);
                        return result;
                    }
                }
            } else {
                String message = "";
                byte[] byteArray = response.getResponseBody();
                String strRead = new String(byteArray, "utf-8");
                logger.debug("content = " + strRead);
                JSONObject jsonObject = JSONObject.fromObject(strRead);
                result = new FaceVerifyResult();
                String errorMessage = jsonObject.getString("error_message");
                if (errorMessage != null) {
                    if (errorMessage.indexOf(":") >= 0) {
                        message = LanguageHelper.getLocalizedMessage("jbb.face.errormessage." + errorMessage.substring(0, errorMessage.indexOf(":")), "zh");
                    }else{
                        message = LanguageHelper.getLocalizedMessage("jbb.face.errormessage." + errorMessage, "zh");
                    }
                   
                } else {
                    // 未知错误
                    message = LanguageHelper.getLocalizedMessage("jbb.face.errormessage.unknowerror", "zh");
                }
                result.setFlagVerify(false);
                result.setError_message(message);
                result.setVerifyData(strRead);
                logger.debug("response code = " + response.getResponseCode());
                logger.debug("response content = " + new String(response.getResponseBody()));
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("< Verify()");
        result = new FaceVerifyResult();
        result.setFlagVerify(false);
        result.setError_message(PropertyManager.getProperty("jbb.face.errormessage.unknowerror"));
        return result;
    }

    private HttpResponse postFaceApi(String apiKey, String apiSecret, String bizNo, String urlStr,
        String tokenRandomNumber, byte[] videoContent, String returnImage, int userId, String token,
        String comparisonType, String idcardName, String idcardNumber, String uuid, byte[] photoContent)
            throws IOException {

        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "----jbbvideoandpic";

        URL url = new URL(urlStr);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        // 发送POST请求必须设置如下两行
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

        DataOutputStream ds = new DataOutputStream(conn.getOutputStream());

        // appid
        ds.writeBytes(twoHyphens + boundary + end);
        ds.writeBytes("Content-Disposition: form-data; name=\"api_key\"");
        ds.writeBytes(end);
        ds.writeBytes(end);
        ds.writeBytes(apiKey);
        ds.writeBytes(end);

        // api_secret
        ds.writeBytes(twoHyphens + boundary + end);
        ds.writeBytes("Content-Disposition: form-data; name=\"api_secret\"");
        ds.writeBytes(end);
        ds.writeBytes(end);
        ds.writeBytes(apiSecret);
        ds.writeBytes(end);

        // biz_no
        ds.writeBytes(twoHyphens + boundary + end);
        ds.writeBytes("Content-Disposition: form-data; name=\"biz_no\"");
        ds.writeBytes(end);
        ds.writeBytes(end);
        ds.writeBytes(bizNo);
        ds.writeBytes(end);

        // token_random_number
        if (!StringUtil.isEmpty(tokenRandomNumber)) {
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"token_random_number\"");
            ds.writeBytes(end);
            ds.writeBytes(end);
            ds.writeBytes(tokenRandomNumber);
            ds.writeBytes(end);
        }

        // video
        if (videoContent != null && videoContent.length != 0) {
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"video\"; filename=\"video" + userId + ".mp4\"");
            ds.writeBytes(end);
            ds.writeBytes("Content-Type: video/mp4");
            ds.writeBytes(end);
            ds.writeBytes(end);
            ds.write(videoContent);
            ds.writeBytes(end);
        }

        // return_image
        if (!StringUtil.isEmpty(returnImage)) {
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"return_image\"");
            ds.writeBytes(end);
            ds.writeBytes(end);
            ds.writeBytes(returnImage);
            ds.writeBytes(end);
        }

        // token
        if (!StringUtil.isEmpty(token)) {
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"token\"");
            ds.writeBytes(end);
            ds.writeBytes(end);
            ds.writeBytes(token);
            ds.writeBytes(end);
        }

        // comparisonType
        if (!StringUtil.isEmpty(comparisonType)) {
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"comparison_type\"");
            ds.writeBytes(end);
            ds.writeBytes(end);
            ds.writeBytes(comparisonType);
            ds.writeBytes(end);
            if (comparisonType.equals("1")) {
                // 有源对比
                // idcard_name
                if (!StringUtil.isEmpty(idcardName)) {
                    ds.writeBytes(twoHyphens + boundary + end);
                    ds.writeBytes("Content-Disposition: form-data; name=\"idcard_name\"");
                    ds.writeBytes(end);
                    ds.writeBytes(end);
                    ds.write(idcardName.getBytes());
                    ds.writeBytes(end);
                }

                // idcard_number
                if (!StringUtil.isEmpty(idcardNumber)) {
                    ds.writeBytes(twoHyphens + boundary + end);
                    ds.writeBytes("Content-Disposition: form-data; name=\"idcard_number\"");
                    ds.writeBytes(end);
                    ds.writeBytes(end);
                    ds.writeBytes(idcardNumber);
                    ds.writeBytes(end);
                }

            } else if (comparisonType.equals("0")) {
                // 无源对比
                // uuid
                if (!StringUtil.isEmpty(uuid)) {
                    ds.writeBytes(twoHyphens + boundary + end);
                    ds.writeBytes("Content-Disposition: form-data; name=\"uuid\"");
                    ds.writeBytes(end);
                    ds.writeBytes(end);
                    ds.writeBytes(uuid);
                    ds.writeBytes(end);
                }
            }

            if (photoContent != null && photoContent.length != 0) {
                ds.writeBytes(twoHyphens + boundary + end);
                ds.writeBytes(
                    "Content-Disposition: form-data; name=\"image_ref1\"; filename=\"photo" + userId + ".jpeg\"");
                ds.writeBytes(end);
                ds.writeBytes("Content-Type: image/jpeg");
                ds.writeBytes(end);
                ds.writeBytes(end);
                ds.write(photoContent);
                ds.writeBytes(end);
            }

            /*  // 加载图片组参数
            if (imageMap != null && imageMap.size() != 0) {
                int i = 1;
                for (Map.Entry<String, byte[]> entry : imageMap.entrySet()) {
                    if (entry.getValue() != null && !entry.getValue().equals("") && entry.getValue() != null
                        && entry.getValue().length != 0) {
                        ds.writeBytes(twoHyphens + boundary + end);
                        ds.writeBytes("Content-Disposition: form-data; name=\"image_ref" + i + "\"; filename=\""
                            + entry.getKey() + "\"");
                        ds.writeBytes(end);
                        ds.writeBytes("Content-Type: image/jpeg");
                        ds.writeBytes(end);
                        ds.writeBytes(end);
                        ds.write(entry.getValue());
                        ds.writeBytes(end);
                        i++;
                    }
                }
            }*/
        }
        // end
        ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

        ds.flush();

        conn.connect();

        int responseCode = conn.getResponseCode();

        InputStream in = null;
        byte[] responseBody = null;
        String errorMessage = null;

        try {
            if (responseCode < HttpURLConnection.HTTP_BAD_REQUEST) {
                in = conn.getInputStream();

                if (in == null) {
                    throw new ExecutionException(
                        "Cannot get response stream for urlString=[" + urlStr + "], responseCode=" + responseCode);
                }

                responseBody = IOUtil.readStreamClean(in);
            } else {
                in = conn.getErrorStream();
                if (in == null)
                    in = conn.getInputStream();

                if (in != null) {
                    responseBody = IOUtil.readStreamClean(in);
                }

                errorMessage = conn.getResponseMessage();

                if (logger.isDebugEnabled()) {
                    logger.debug("Response code: " + responseCode + ", " + errorMessage);
                }
            }
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception e) {
                logger.warn("Exception in closing input stream " + e.toString());
            }
        }

        return new HttpResponse(responseCode, responseBody, errorMessage);
    }

    private HttpResponse postToken(String apiKey, String apiSecret, String bizNo, String urlStr, String comparisonType,
        String idcardMode, String bizExtraData, String uuid) throws IOException {

        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "----jbbvideoandpic";

        URL url = new URL(urlStr);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        // 发送POST请求必须设置如下两行
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

        DataOutputStream ds = new DataOutputStream(conn.getOutputStream());

        // appid
        ds.writeBytes(twoHyphens + boundary + end);
        ds.writeBytes("Content-Disposition: form-data; name=\"api_key\"");
        ds.writeBytes(end);
        ds.writeBytes(end);
        ds.writeBytes(apiKey);
        ds.writeBytes(end);

        // api_secret
        ds.writeBytes(twoHyphens + boundary + end);
        ds.writeBytes("Content-Disposition: form-data; name=\"api_secret\"");
        ds.writeBytes(end);
        ds.writeBytes(end);
        ds.writeBytes(apiSecret);
        ds.writeBytes(end);

        // biz_no
        if (!StringUtil.isEmpty(bizNo)) {
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"biz_no\"");
            ds.writeBytes(end);
            ds.writeBytes(end);
            ds.writeBytes(bizNo);
            ds.writeBytes(end);
        }

        // return_url
        ds.writeBytes(twoHyphens + boundary + end);
        ds.writeBytes("Content-Disposition: form-data; name=\"return_url\"");
        ds.writeBytes(end);
        ds.writeBytes(end);
        ds.writeBytes("http://pde2bv.natappfree.cc/espapi/api/face/getreturnresult");
        ds.writeBytes(end);

        // biz_extra_data //额外数据
        if (!StringUtil.isEmpty(bizExtraData)) {
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"biz_extra_data\"");
            ds.writeBytes(end);
            ds.writeBytes(end);
            ds.writeBytes(bizExtraData);
            ds.writeBytes(end);
        }

        // uuid
        if (!StringUtil.isEmpty(uuid)) {
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"uuid\"");
            ds.writeBytes(end);
            ds.writeBytes(end);
            ds.writeBytes(uuid);
            ds.writeBytes(end);
        }

        // notify_url
        ds.writeBytes(twoHyphens + boundary + end);
        ds.writeBytes("Content-Disposition: form-data; name=\"notify_url\"");
        ds.writeBytes(end);
        ds.writeBytes(end);
        ds.writeBytes("http://pde2bv.natappfree.cc/espapi/api/face/notify");
        ds.writeBytes(end);

        // comparisonType
        if (!StringUtil.isEmpty(comparisonType)) {
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"comparison_type\"");
            ds.writeBytes(end);
            ds.writeBytes(end);
            ds.writeBytes(comparisonType);
            ds.writeBytes(end);
            if (comparisonType.equals("1")) {
                // 有源对比
                // idcard_mode
                if (!StringUtil.isEmpty(idcardMode)) {
                    ds.writeBytes(twoHyphens + boundary + end);
                    ds.writeBytes("Content-Disposition: form-data; name=\"idcard_mode\"");
                    ds.writeBytes(end);
                    ds.writeBytes(end);
                    ds.writeBytes(idcardMode);
                    ds.writeBytes(end);
                }
            } else if (comparisonType.equals("0")) {
                if (!StringUtil.isEmpty(idcardMode)) {
                    ds.writeBytes(twoHyphens + boundary + end);
                    ds.writeBytes("Content-Disposition: form-data; name=\"idcard_mode\"");
                    ds.writeBytes(end);
                    ds.writeBytes(end);
                    ds.writeBytes(idcardMode);
                    ds.writeBytes(end);
                }
            }
        }
        // end
        ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

        ds.flush();

        conn.connect();

        int responseCode = conn.getResponseCode();

        InputStream in = null;
        byte[] responseBody = null;
        String errorMessage = null;

        try {
            if (responseCode < HttpURLConnection.HTTP_BAD_REQUEST) {
                in = conn.getInputStream();

                if (in == null) {
                    throw new ExecutionException(
                        "Cannot get response stream for urlString=[" + urlStr + "], responseCode=" + responseCode);
                }

                responseBody = IOUtil.readStreamClean(in);
            } else {
                in = conn.getErrorStream();
                if (in == null)
                    in = conn.getInputStream();

                if (in != null) {
                    responseBody = IOUtil.readStreamClean(in);
                }

                errorMessage = conn.getResponseMessage();

                if (logger.isDebugEnabled()) {
                    logger.debug("Response code: " + responseCode + ", " + errorMessage);
                }
            }
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception e) {
                logger.warn("Exception in closing input stream " + e.toString());
            }
        }

        return new HttpResponse(responseCode, responseBody, errorMessage);
    }

    private HttpResponse postOcr(String apiKey, String apiSecret, String urlStr, int userId, byte[] photoContent)
        throws IOException {

        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "----jbbvideoandpic";

        URL url = new URL(urlStr);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        // 发送POST请求必须设置如下两行
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

        DataOutputStream ds = new DataOutputStream(conn.getOutputStream());

        // appid
        ds.writeBytes(twoHyphens + boundary + end);
        ds.writeBytes("Content-Disposition: form-data; name=\"api_key\"");
        ds.writeBytes(end);
        ds.writeBytes(end);
        ds.writeBytes(apiKey);
        ds.writeBytes(end);

        // api_secret
        ds.writeBytes(twoHyphens + boundary + end);
        ds.writeBytes("Content-Disposition: form-data; name=\"api_secret\"");
        ds.writeBytes(end);
        ds.writeBytes(end);
        ds.writeBytes(apiSecret);
        ds.writeBytes(end);

        // image
        if (photoContent != null && photoContent.length != 0) {
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"photo" + userId + ".jpeg\"");
            ds.writeBytes(end);
            ds.writeBytes("Content-Type: image/jpeg");
            ds.writeBytes(end);
            ds.writeBytes(end);
            ds.write(photoContent);
            ds.writeBytes(end);
        }

        // end
        ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

        ds.flush();

        conn.connect();

        int responseCode = conn.getResponseCode();

        InputStream in = null;
        byte[] responseBody = null;
        String errorMessage = null;

        try {
            if (responseCode < HttpURLConnection.HTTP_BAD_REQUEST) {
                in = conn.getInputStream();

                if (in == null) {
                    throw new ExecutionException(
                        "Cannot get response stream for urlString=[" + urlStr + "], responseCode=" + responseCode);
                }

                responseBody = IOUtil.readStreamClean(in);
            } else {
                in = conn.getErrorStream();
                if (in == null)
                    in = conn.getInputStream();

                if (in != null) {
                    responseBody = IOUtil.readStreamClean(in);
                }

                errorMessage = conn.getResponseMessage();

                if (logger.isDebugEnabled()) {
                    logger.debug("Response code: " + responseCode + ", " + errorMessage);
                }
            }
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception e) {
                logger.warn("Exception in closing input stream " + e.toString());
            }
        }

        return new HttpResponse(responseCode, responseBody, errorMessage);
    }

    @Override
    public JSONObject getToken(String bizNo, String bizExtraData, String uuid) {
        String apiKey = PropertyManager.getProperty("jbb.face.apikey");
        String apiSecret = PropertyManager.getProperty("jbb.face.apisecret");
        String urlStr = PropertyManager.getProperty("jbb.face.gettoken.url");
        String comparisonType = PropertyManager.getProperty("jbb.face.comparisontype");
        String idcardMode = PropertyManager.getProperty("jbb.face.idcardmode");
        JSONObject jsonObject = null;
        try {
            HttpResponse response =
                postToken(apiKey, apiSecret, bizNo, urlStr, comparisonType, idcardMode, bizExtraData, "");
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                byte[] byteArray = response.getResponseBody();
                if (byteArray == null || byteArray.length < 1) {
                    throw new IllegalArgumentException("this byteArray must not be null or empty");
                } else {
                    String strRead = new String(byteArray, "utf-8");
                    logger.debug("content = " + strRead);
                    jsonObject = JSONObject.fromObject(strRead);
                }
            } else {
                logger.debug("response code = " + response.getResponseCode());
                logger.debug("response content = " + new String(response.getResponseBody()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public IdCard faceOCR(int userId, byte[] photoContent) {
        logger.debug("> faceOCR()");
        int apiLimitCnt = PropertyManager.getIntProperty("jbb.api.ocridcard.limit", 5);
        long start = DateUtil.getTodayStartTs();
        long end = start + DateUtil.DAY_MILLSECONDES;
        int cnt = userEventLogService.countEventLogByParams(userId, null, "user", "ocrIdCard", null, "1",
            new Timestamp(start), new Timestamp(end));
        if (cnt > apiLimitCnt) {
            throw new ApiCallLimitException("jbb.error.exception.api.limitwithdaynum", String.valueOf(apiLimitCnt));
        }
        String apiKey = PropertyManager.getProperty("jbb.face.apikey");
        String apiSecret = PropertyManager.getProperty("jbb.face.apisecret");
        String urlStr = PropertyManager.getProperty("jbb.face.ocr.url");
        IdCard result = null;
        try {
            userEventLogService.insertEventLog(userId, null, "user", "ocrIdCard", null, "1", null);
            HttpResponse response = postOcr(apiKey, apiSecret, urlStr, userId, photoContent);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                byte[] byteArray = response.getResponseBody();
                if (byteArray == null || byteArray.length < 1) {
                    throw new IllegalArgumentException("this byteArray must not be null or empty");
                } else {
                    String strRead = new String(byteArray, "utf-8");
                    logger.debug("content = " + strRead);
                    JSONObject jsonObject = JSONObject.fromObject(strRead);
                    result = new IdCard();
                    result.setAddress(jsonObject.getString("address"));
                    result.setBirth(jsonObject.getString("birthday"));
                    result.setName(jsonObject.getString("name"));
                    result.setNationality(jsonObject.getString("race"));
                    result.setNum(jsonObject.getString("id_card_number"));
                    result.setSex(jsonObject.getString("gender"));
                    // 修改保存逻辑
                    User usernew = acconutDao.getUser(userId);
                    usernew.setUserName(jsonObject.getString("name"));
                    usernew.setIdCardNo(jsonObject.getString("id_card_number"));
                    acconutDao.updateUser(usernew);
                    // 更新user 表中的内容
                }
            } else {
                logger.debug("response code = " + response.getResponseCode());
                logger.debug("response content = " + new String(response.getResponseBody()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("< faceOCR()");
        return result;
    }

}
