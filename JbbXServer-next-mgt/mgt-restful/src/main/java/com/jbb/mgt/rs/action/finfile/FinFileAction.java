package com.jbb.mgt.rs.action.finfile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.FinFile;
import com.jbb.mgt.core.service.AliyunService;
import com.jbb.mgt.core.service.FinFileService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.mgt.server.rs.pojo.request.ReFinFile;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.TimeZone;


@Service(FinFileAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FinFileAction extends BasicAction {

    public static final String ACTION_NAME = "FinFileAction";

    private static Logger logger = LoggerFactory.getLogger(FinFileAction.class);

    private Response response;

    @Autowired
    private FinFileService finFileService;

    @Autowired
    private AliyunService aliyunService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void insertFinFile(Integer orgId,Integer merchantId,Integer platformId,Integer fileType,String fileDateTs,byte[] fileDate) {
        logger.debug(">insertFinFile");
        byte[] file = fileDate;
        if (file == null || file.length == 0) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "file");
        }
        String fileName = StringUtil.getRandomnum(16);
        fileName = fileName + ".jpg";
        String bucket = PropertyManager.getProperty("jbb.aliyu.oss.bucket.mgt.photos");
        aliyunService.putObject(bucket, fileName, file, "image/jpeg");
        FinFile finFile = new FinFile();
        finFile.setCreationDate(DateUtil.getCurrentTimeStamp());
        finFile.setCreator(this.account.getAccountId());
        finFile.setDeleted(false);
        if (!StringUtil.isEmpty(fileDateTs)) {
            //如果给定的时间是今天，那么抛出异常信息 因为当日无法保存数据
            Timestamp eTs = Util.parseTimestamp(fileDateTs, TimeZone.getDefault());
            Date dateFormat = new Date(DateUtil.getTodayStartCurrentTime());
            finFile.setFileDate(eTs);
        } else {
            finFile.setFileDate(DateUtil.getCurrentTimeStamp());
        }
        finFile.setFileName(fileName);
        finFile.setFileType(fileType);
        finFile.setMerchantId(merchantId);
        finFile.setOrgId(orgId);
        finFile.setPlatformId(platformId);
        finFileService.insertFinFile(finFile);
        this.response.fileName = fileName;
        if(finFile.getFileId()!=0){
            this.response.fileId = finFile.getFileId();
        }
        logger.debug("<insertFinFile");
    }

    public void deleteFinFile(String fileId) {
        logger.debug(">deleteFinFile");
        if (StringUtil.isEmpty(fileId)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "fileId");
        }
        finFileService.deleteFinFileById(Integer.valueOf(fileId));
        logger.debug(">deleteFinFile");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public String fileName;
        public Integer fileId;
    }
}
