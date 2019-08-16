package com.jbb.server.rs.action;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.ApiCallLimitException;
import com.jbb.server.core.domain.IdCard;
import com.jbb.server.core.service.AliyunService;
import com.jbb.server.core.service.FaceService;
import com.jbb.server.core.service.ProductService;
import com.jbb.server.rs.pojo.ActionResponse;

@Component(FaceOcrIdCardAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FaceOcrIdCardAction extends BasicAction {

    private static Logger logger = LoggerFactory.getLogger(FaceOcrIdCardAction.class);
    public static final String ACTION_NAME = "FaceOcrIdCardAction";

    private Response response;

    @Autowired
    private ProductService productService;

    @Autowired
    private AliyunService aliyunService;

    @Override
    protected ActionResponse makeActionResponse() {
        return response = new Response();
    }

    @Autowired
    private FaceService faceService;

    public void ocrIdCard(Object body) {
        logger.debug(">ocrIdCard()");
        int fee = PropertyManager.getIntProperty("jbb.wx.pay.auth.fee", 0);
        if (fee != 0 && productService.getProductCount(this.user.getUserId(), Constants.PRODUCT_AUTH) == 0) {
            throw new ApiCallLimitException("jbb.error.exception.auth.notPay");
        }
        // 获取图片信息，在此使用用户id查询
        String bucket = PropertyManager.getProperty("jbb.aliyu.oss.bucket.idcards");
        byte[] photoContent = null;
        try {
            photoContent = aliyunService.getImageBytes(bucket, "idcard_" + this.user.getUserId() + "_rear");
            
        } catch (IOException e) {
             logger.debug("get image faill reason=" + e.getMessage());
        }
        this.response.idcard = faceService.faceOCR(this.user.getUserId(), photoContent);
        logger.debug("<ocrIdCard()");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private IdCard idcard;

        public IdCard getIdcard() {
            return idcard;
        }

    }

}
