package com.jbb.server.rs.action;

import java.io.IOException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.ApiCallLimitException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.core.domain.FaceRandomResult;
import com.jbb.server.core.domain.FaceValidateVideoResult;
import com.jbb.server.core.domain.FaceVerifyResult;
import com.jbb.server.core.domain.UserFaceIdBizNo;
import com.jbb.server.core.service.AliyunService;
import com.jbb.server.core.service.FaceService;
import com.jbb.server.core.service.ProductService;
import com.jbb.server.core.service.TencentService;
import com.jbb.server.core.service.UserEventLogService;
import com.jbb.server.core.service.UserFaceIdBizNoService;
import com.jbb.server.rs.pojo.ActionResponse;

@Service(FaceDetectAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FaceDetectAction extends BasicAction {
    private static Logger logger = LoggerFactory.getLogger(FaceDetectAction.class);
    public static final String ACTION_NAME = "FaceDetectAction";

    private Response response;

    @Autowired
    private TencentService tencentService;

    @Autowired
    private UserEventLogService userEventLogService;

    @Autowired
    private ProductService productService;

    @Autowired
    private FaceService faceService;

    @Autowired
    private UserFaceIdBizNoService userFaceIdBizNoService;

    @Autowired
    private AliyunService aliyunService;

    @Override
    protected ActionResponse makeActionResponse() {
        return response = new Response();
    }

    public void liveDetectGetFour() {
        String bizNo = DateUtil.getOrderNum();
        FaceRandomResult result = faceService.GetRandomNumber(bizNo);
        if (result != null) {
            // 保存到库
            UserFaceIdBizNo userFaceIdBizNo = new UserFaceIdBizNo();
            userFaceIdBizNo.setBizNo(bizNo);
            userFaceIdBizNo.setRandomData(result.getRandomData());
            userFaceIdBizNo.setRandomNumber(result.getRandomNumber());
            userFaceIdBizNo.setTokenRandomNumber(result.getTokenRandomNumber());
            userFaceIdBizNo.setUserId(this.user.getUserId());
            userFaceIdBizNoService.insertUserFaceIdBizNo(userFaceIdBizNo);
            this.response.validateData = result.getRandomNumber();
        }

    }

    public void liveDetect(String validateData, byte[] content) {
        // 此处跟之前逻辑不同 的是 ，此处需要先上传验证，在校验
        int fee = PropertyManager.getIntProperty("jbb.wx.pay.auth.fee", 0);
        if (fee != 0 && productService.getProductCount(this.user.getUserId(), Constants.PRODUCT_AUTH) == 0) {
            throw new ApiCallLimitException("jbb.error.exception.auth.notPay");
        }

        // 检查是否超出每日限制
        int userId = this.user.getUserId();
        int apiLimitCnt = PropertyManager.getIntProperty("jbb.api.video.validate.limit", 5);
        long start = DateUtil.getTodayStartTs();
        long end = start + DateUtil.DAY_MILLSECONDES;
        int cnt = userEventLogService.countEventLogByParams(userId, null, "user", "videoValidate", "submit", "1",
            new Timestamp(start), new Timestamp(end));
        if (cnt >= apiLimitCnt) {
            throw new ApiCallLimitException("jbb.error.exception.api.limitwithdaynum", String.valueOf(apiLimitCnt));
        }

        logger.debug(">liveDetect(), validateData= " + validateData + ", content length=" + content.length);

        // 请求验证
        userEventLogService.insertEventLog(userId, null, "user", "videoValidate", "submit", "1",
            this.getRemoteAddress());

        // 获取tokenRandomNumber
        UserFaceIdBizNo userFaceIdBizNo
            = userFaceIdBizNoService.selectUserFaceIdBizNoByRandom(validateData, this.user.getUserId());
        if (userFaceIdBizNo != null) {
            FaceValidateVideoResult videoresult = faceService.ValidateVideo(userFaceIdBizNo.getTokenRandomNumber(),
                userFaceIdBizNo.getBizNo(), userId, content);
            if (videoresult.getErrorMessage() == null || videoresult.getErrorMessage().equals("")) {
                // 获取正常
                logger.info("ValidateVideo ok");
                userFaceIdBizNo.setTokenVideo(videoresult.getTokenVideo());
                userFaceIdBizNo.setValidateVideoData(videoresult.getValidateVideoData());
                boolean flag = userFaceIdBizNoService.updateUserFaceIdBizNo(userFaceIdBizNo);
                if (flag) {
                    // 校验视频,将图片当做参数注入
                    String bucket = PropertyManager.getProperty("jbb.aliyu.oss.bucket.idcards");
                    byte[] photoContent = null;
                    // 不用比对身份证照片，face++进行有源比对。
                    // try {
                    // //photoContent = aliyunService.getImageBytes(bucket, "idcard_" + this.user.getUserId() +
                    // "_rear");
                    // photoContent = aliyunService.getImageBytesWithStyle(bucket, "idcard_" + this.user.getUserId() +
                    // "_rear", "style/idcardresize");
                    // } catch (IOException e) {
                    // logger.debug("get image faill reason=" + e.getMessage());
                    // }
                    FaceVerifyResult verifyResult = faceService.Verify(userFaceIdBizNo.getTokenVideo(),
                        userFaceIdBizNo.getBizNo(), this.user.getUserName(), this.user.getIdCardNo(), photoContent,
                        DateUtil.getOrderNum(), userId);
                    UserFaceIdBizNo userFaceIdBizNoverify
                        = userFaceIdBizNoService.selectUserFaceIdBizNoByRandom(validateData, this.user.getUserId());
                    userFaceIdBizNoverify.setVerifyData(verifyResult.getVerifyData());
                    boolean flagverify = userFaceIdBizNoService.updateUserFaceIdBizNo(userFaceIdBizNoverify);
                    if (verifyResult.isFlagVerify()) {
                        // 验证通过
                        if (flagverify) {
                            boolean isValid = verifyResult.isFlagVerify();
                            this.response.isValid = isValid;
                        } else {
                            logger.info("save userFaceIdBizNoverify fail, userId=" + userId);
                            this.response.isValid = false;
                        }
                    } else {
                        this.response.isValid = false;
                        this.response.msg = verifyResult.getError_message();
                        logger.info(
                            "FaceVerifyResult fail, userId =" + userId + " ,msg = " + verifyResult.getError_message());
                    }
                }
            } else {
                this.response.isValid = false;
                this.response.msg = videoresult.getErrorMessage();
                logger.info("FaceValidateVideo fail, userId =" + userId + " ,msg = " + videoresult.getErrorMessage());
            }
        } else {
            this.response.isValid = false;
            this.response.msg = "faceValidateVideo fail";
            logger.warn("FaceValidateVideo fail, userFaceIdBizNo is null");
        }

        // 插入日志
        String eventLabel = this.response.isValid == null ? null : this.response.isValid == true ? "true" : "false";
        userEventLogService.insertEventLog(userId, null, "user", "videoValidate", eventLabel, "1",
            this.getRemoteAddress());

        logger.debug("<liveDetect");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private Boolean isValid;

        private String msg;

        private String validateData;

        public Boolean getIsValid() {
            return isValid;
        }

        public String getValidateData() {
            return validateData;
        }

        public String getMsg() {
            return msg;
        }

    }

}
