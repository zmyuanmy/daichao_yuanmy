package com.jbb.mgt.rs.action.upload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.service.AliyunService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;

@Service(UploadUtilAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UploadUtilAction extends BasicAction {

    public static final String ACTION_NAME = "UploadUtilAction";

    private static final int MAX_CONTENT_LENGTH_M = 10;
    private static final int MAX_CONTENT_LENGTH = MAX_CONTENT_LENGTH_M * 1024 * 1024;

    private static final int MAX_LOGO_CONTENT_LENGTH_KB = 500;
    private static final int CONTENT_LENGTH = 1024;

    private static Logger logger = LoggerFactory.getLogger(UploadUtilAction.class);

    @Autowired
    private AliyunService aliyunservie;

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void upload(byte[] content, Integer type) {
        logger.debug(">uploadImg() content:{} type:{}", content.length, type);
        int cimgsSize = PropertyManager.getIntProperty("jbb.aliyu.oss.cimgsSize", 100);
        int pimgsSize = PropertyManager.getIntProperty("jbb.aliyu.oss.pimgsSize", 100);
        int padsSize = PropertyManager.getIntProperty("jbb.aliyu.oss.padsSize", 100);
        if ((content == null) || (content.length == 0)) {
            logger.debug("<upload() missing file content");
            throw new MissingParameterException("jbb.mgt.exception.file.empty", "zh", "content");
        }
        if (type == null) {
            throw new MissingParameterException("jbb.mgt.exception.upload.imgType");
        }
        String accessUrl = PropertyManager.getProperty("jbb.aliyu.oss.accessUrl");
        String url = "";
        if (type == 1) {
            url = "cimgs/";
            if (content.length > cimgsSize * CONTENT_LENGTH) {
                logger.debug("<upload() cimgs content > cimgsSize  fileSize:{}", content.length);
                throw new WrongParameterValueException("jbb.mgt.exception.file.maxLength.kb", "zh",
                    String.valueOf(cimgsSize));
            }
        } else if (type == 2) {
            url = "pimgs/";
            if (content.length > pimgsSize * CONTENT_LENGTH) {
                logger.debug("<upload() pimgs content > pimgsSize  fileSize:{}", content.length);
                throw new WrongParameterValueException("jbb.mgt.exception.file.maxLength.kb", "zh",
                    String.valueOf(pimgsSize));
            }
        } else if (type == 3) {
            url = "pads/";
            if (content.length > padsSize * CONTENT_LENGTH) {
                logger.debug("<upload() pads content > padsSize  fileSize:{}", content.length);
                throw new WrongParameterValueException("jbb.mgt.exception.file.maxLength.kb", "zh",
                    String.valueOf(padsSize));
            }
        } else {
            throw new WrongParameterValueException("jbb.mgt.exception.upload.imgType");
        }
        String fileName = url + DateUtil.getCurrentTime() + ".jpg";
        aliyunservie.putObject("hwjf-imgs", fileName, content, null);
        this.response.downloadUrl = accessUrl + "/" + fileName;
        logger.debug("<upload()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private String downloadUrl;

        public String getDownloadUrl() {
            return downloadUrl;
        }

    }

}
