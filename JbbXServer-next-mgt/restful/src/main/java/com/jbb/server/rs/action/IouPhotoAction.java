package com.jbb.server.rs.action;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GenericRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.domain.Iou;
import com.jbb.server.core.domain.IouPhoto;
import com.jbb.server.core.domain.UserPhotoFile;
import com.jbb.server.core.service.AliyunService;
import com.jbb.server.core.service.IouPhotoService;
import com.jbb.server.core.service.IousService;
import com.jbb.server.rs.pojo.ActionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by inspironsun on 2018/6/9
 */

@Service(IouPhotoAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class IouPhotoAction extends BasicAction {

    private static Logger logger = LoggerFactory.getLogger(IouPhotoAction.class);

    public static final String ACTION_NAME = "IouPhotoAction";

    private Response response;

    @Autowired
    private AliyunService aliyunService;

    @Autowired
    private IouPhotoService iouPhotoService;

    @Autowired
    private IousService iousService;

    @Override
    protected ActionResponse makeActionResponse() {
        return response = new Response();
    }

    public void uploadIouPhoto(byte[] file, String iouCode) {
        logger.debug(">uploadIouPhoto");
        if (file == null || file.length == 0) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "file");
        }
        if (StringUtil.isEmpty(iouCode)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "iouCode");
        }
        int photoNumConfig = PropertyManager.getIntProperty("jbb.iouphoto.maxnum ", 3);
        int countnum = iouPhotoService.countUserIouPhoto(this.user.getUserId(), iouCode);
        if (countnum >= photoNumConfig) {
            throw new WrongParameterValueException("jbb.error.exception.userIouPhotoNum");
        }
        String fileName = StringUtil.getRandomnum(16);
        fileName = fileName + ".jpg";
        // 将图片上传到oss
        String bucket = PropertyManager.getProperty("jbb.aliyu.oss.bucket.proof");
        aliyunService.putObject(bucket, fileName, file, "image/jpeg");
        IouPhoto iouPhotoNew = new IouPhoto();
        iouPhotoNew.setDeleted(false);
        iouPhotoNew.setUserId(this.user.getUserId());
        iouPhotoNew.setFileName(fileName);
        iouPhotoNew.setIouCode(iouCode);
        iouPhotoNew.setUploadDate(DateUtil.getCurrentTimeStamp());
        iouPhotoService.insertIouPhoto(iouPhotoNew);
        //上传成功之后，将图片的id 以及图片的地址返回
        String url = generatePresignedUrl(fileName);
        if(iouPhotoNew.getId()!=0){
            this.response.fileId = String.valueOf(iouPhotoNew.getId());
        }
        this.response.fileUrl = url;
        logger.debug("<uploadIouPhoto");
    }

    public void deleteIouPhoto(int iouPhotoId) {
        logger.debug(">deleteIouPhoto");
        if (iouPhotoId == 0) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "iouPhotoId");
        }
        int delete = iouPhotoService.deleteIouPhoto(iouPhotoId, this.user.getUserId());
        if (delete == 0) {
            throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "iouPhotoId");
        }
        logger.debug("<deleteIouPhoto");
    }

    public void getIouPhotos(String iouCode) {
        logger.debug(">getIouPhotos");
        if (StringUtil.isEmpty(iouCode)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "iouCode");
        }
        Iou iou = iousService.getIouByIouCode(iouCode);
        if(iou==null){
           throw new ObjectNotFoundException("jbb.error.exception.iouNotFournd", "zh", iouCode);
        }
        List<IouPhoto> iouPhotos = iouPhotoService.selectIouPhotoListByIouCode(iouCode);
        List<UserPhotoFile> userPhotoFiles = new ArrayList<UserPhotoFile>();
        if (iouPhotos == null || iouPhotos.size() == 0) {
            throw new ObjectNotFoundException("jbb.error.exception.userIouPhotoNotFound", "zh", "iouPhotos");
        }
        for (IouPhoto iouPhoto : iouPhotos) {
            String userType = "";
            if(iouPhoto.getUserId()==iou.getLenderId()){
                userType="1";
            }
            if(iouPhoto.getUserId()==iou.getBorrowerId()){
                userType="2";
            }
            String url = generatePresignedUrl(iouPhoto.getFileName());
            UserPhotoFile userPhotoFile = new UserPhotoFile(iouPhoto.getId(), iouPhoto.getUserId(),
                iouPhoto.getUploadDate(), iouPhoto.getFileName(), url,userType);
            userPhotoFiles.add(userPhotoFile);
        }
        this.response.files = userPhotoFiles;
        String photoService = PropertyManager.getProperty("jbb.aliyu.oss.photoService");
        this.response.photoService = photoService;
        logger.debug("<getIouPhotos");
    }

    private String generatePresignedUrl(String filename) {
        String endpoint = PropertyManager.getProperty("jbb.aliyu.oss.bucket.endpoint");
        String accessId = PropertyManager.getProperty("jbb.aliyun.oss.accessKeyId");
        String accessKey = PropertyManager.getProperty("jbb.aliyun.oss.accessKeySecret");
        String bucket = PropertyManager.getProperty("jbb.aliyu.oss.bucket.proof");
        String style = PropertyManager.getProperty("jbb.aliyu.oss.style");
        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        try {
            client.getObjectMetadata(new GenericRequest(bucket, filename));
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, filename);
            request.setExpiration(new Date(DateUtil.getCurrentTime() + DateUtil.HOUR_MILLSECONDES / 2));
            // request.setProcess("style/resize");
            URL url = client.generatePresignedUrl(request);
            return url.getFile();
        } catch (Exception e) {
            logger.info(e.getMessage());
            // throw new AliyunException("jbb.error.exception.aliyun.error");
            // 如果出现异常，或者某一个图片信息找不到，返回空的url
            // do nothiong
            return "";
        }
    }

    public void bindBorrower(String iouCode) {
        logger.debug(">bindBorrower");
        // check 当前userid 与借条lenderid 是否一样 如果一样则不做操作，如果不一样则保存id到borrowerId
        if (StringUtil.isEmpty(iouCode)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "iouCode");
        }
        Iou iou = iousService.getIouByIouCode(iouCode);
        if (iou == null) {
            throw new ObjectNotFoundException("jbb.error.exception.iouNotFournd", "zh", iouCode);
        }
        if(iou.getBorrowerId()!=0){
            throw new WrongParameterValueException("jbb.error.exception.iouCheck.borrowerIdExist");
        }
        if(iou.getLenderId()!=this.user.getUserId()){
            iou.setBorrowerId(this.user.getUserId());
            iousService.updateIou(iou);
        }else{
            throw new WrongParameterValueException("jbb.error.exception.lenderIouError");
        }
        logger.debug("<bindBorrower");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public String photoService;
        public List<UserPhotoFile> files;
        public String fileId;
        public String fileUrl;

    }

}
