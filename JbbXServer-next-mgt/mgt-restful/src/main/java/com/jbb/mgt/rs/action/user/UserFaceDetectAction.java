package com.jbb.mgt.rs.action.user;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.FaceRandomResult;
import com.jbb.mgt.core.domain.FaceValidateVideoResult;
import com.jbb.mgt.core.domain.FaceVerifyResult;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserFaceIdBizNo;
import com.jbb.mgt.core.service.FaceService;
import com.jbb.mgt.core.service.ProductService;
import com.jbb.mgt.core.service.UserEventLogService;
import com.jbb.mgt.core.service.UserFaceIdBizNoService;
import com.jbb.mgt.core.service.UserService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.mgt.server.rs.pojo.ReLiveDetectRequest;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.ApiCallLimitException;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;

@Service(UserFaceDetectAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserFaceDetectAction extends BasicAction {

    public static final String ACTION_NAME = "UserFaceDetectAction";

    private static Logger logger = LoggerFactory.getLogger(UserFaceDetectAction.class);
    @Autowired
    private FaceService faceService;
    @Autowired
    private UserFaceIdBizNoService userFaceIdBizNoService;
    @Autowired
    private UserEventLogService userEventLogService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    private Response response;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    // 获取四个数字
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

    public void liveDetect(ReLiveDetectRequest request) {
        int userId = this.user.getUserId();
        validateCnt(userId);
        validateReq(request);
        String validateData = request.getValidateData();
        byte[] content = request.getFileDate();
        logger.debug(">liveDetect(), validateData= " + validateData + ", content length=" + content.length);
        // 请求验证
        userEventLogService.insertEventLog(userId, null, null, "user", "videoValidate", "submit", "1",
            this.getRemoteAddress(), DateUtil.getCurrentTimeStamp());

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
                    // String bucket = PropertyManager.getProperty("jbb.aliyu.oss.bucket.idcards");
                    byte[] photoContent = null;
                    /*
                    // 不用比对身份证照片，face++进行有源比对。
                    try {
                        photoContent = aliyunService.getImageBytes(bucket, "idcard_" + this.user.getUserId() + "_rear");
                        photoContent = aliyunService.getImageBytesWithStyle(bucket,
                            "idcard_" + this.user.getUserId() + "_rear", "style/idcardresize");
                    } catch (IOException e) {
                        logger.debug("get image faill reason=" + e.getMessage());
                    }
                    */
                    FaceVerifyResult verifyResult = faceService.Verify(userFaceIdBizNo.getTokenVideo(),
                        userFaceIdBizNo.getBizNo(), this.user.getUserName(), this.user.getIdCard(), photoContent,
                        DateUtil.getOrderNum(), userId);
                    UserFaceIdBizNo userFaceIdBizNoverify
                        = userFaceIdBizNoService.selectUserFaceIdBizNoByRandom(validateData, this.user.getUserId());
                    userFaceIdBizNoverify.setVerifyData(verifyResult.getVerifyData());
                    boolean flagverify = userFaceIdBizNoService.updateUserFaceIdBizNo(userFaceIdBizNoverify);
                    if (verifyResult.isFlagVerify()) {
                        // 验证通过
                        if (flagverify) {
                            // 验证通过更新用户表
                            User user = userService.selectUserById(this.user.getUserId());
                            if (user != null) {
                                user.setVideoVerified(true);
                                user.setIdcardVerified(true);
                                userService.updateUser(user);
                            }
                            this.response.isValid = verifyResult.isFlagVerify();
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
            logger.warn("FaceValidateVideo fail, userFaceIdBizNo is null");
            throw new WrongParameterValueException("jbb.mgt.exception.randomNum.empty");

        }

        // 插入日志
        String eventLabel = this.response.isValid == null ? null : this.response.isValid == true ? "true" : "false";
        userEventLogService.insertEventLog(userId, null, null, "user", "videoValidate", eventLabel, "1",
            this.getRemoteAddress(), DateUtil.getCurrentTimeStamp());

        logger.debug("<liveDetect");

    }

    private void validateCnt(int userId) {
        // 检查是否需要扣费
        int fee = PropertyManager.getIntProperty("jbb.mgt.livedetect.auth.fee", 0);
        if (fee != 0 && productService.getProductCount(this.user.getUserId(), Constants.PRODUCT_AUTH) == 0) {
            throw new ApiCallLimitException("jbb.error.exception.auth.notPay");
        }

        // 检查是否超出每日限制
        int apiLimitCnt = PropertyManager.getIntProperty("jbb.mgt.livedetect.validate.limit", 5);
        long start = DateUtil.getTodayStartTs();
        long end = start + DateUtil.DAY_MILLSECONDES;
        int cnt = userEventLogService.countEventLogByParams(userId, null, null, "user", "videoValidate", "submit", "1",
            new Timestamp(start), new Timestamp(end));

        if (cnt >= apiLimitCnt) {
            throw new ApiCallLimitException("jbb.error.exception.api.limitwithdaynum", String.valueOf(apiLimitCnt));
        }
    }

    private void validateReq(ReLiveDetectRequest request) {
        if (request == null || StringUtil.isEmpty(request.getValidateData())) {
            throw new MissingParameterException("jbb.mgt.exception.randomNum.empty");
        }
        if (request.getFileDate() == null || request.getFileDate().length == 0) {
            throw new MissingParameterException("jbb.mgt.exception.video.empty");
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {

    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private String validateData;
        private Boolean isValid;
        private String msg;

        public String getValidateData() {
            return validateData;
        }

        public Boolean getIsValid() {
            return isValid;
        }

        public String getMsg() {
            return msg;
        }

    }

}
