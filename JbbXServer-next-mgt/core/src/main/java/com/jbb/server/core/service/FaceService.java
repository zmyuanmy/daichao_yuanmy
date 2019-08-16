package com.jbb.server.core.service;

import com.jbb.server.core.domain.FaceRandomResult;
import com.jbb.server.core.domain.FaceValidateVideoResult;
import com.jbb.server.core.domain.FaceVerifyResult;
import com.jbb.server.core.domain.IdCard;

import net.sf.json.JSONObject;

public interface FaceService {

    /**
     * @param bizNo 用于标志一次验证流程，不超过128字符,可声明为拉业务流水号。
     * @return
     */
    FaceRandomResult GetRandomNumber(String bizNo);

    /**
     * 上传视频进行活体验证
     * 
     * @param tokenRandomNumber 调用Raw-GetRandomNumber API返回的token_random_number。
     * @param bizNo
     * @param returnImage 0（默认）：不需要图像 1：需要返回最佳质量图 (仅当API调用成功后才返回)
     * @param userId
     * @param videoContent 视频
     * @return
     */
    FaceValidateVideoResult ValidateVideo(String tokenRandomNumber, String bizNo, int userId, byte[] videoContent);

    /**
     * 执行活体校验 返回结果
     * 
     * @param token ValidateVideo 返回的token_side_face
     * @param comparisonType 目前支持的比对类型为“有源比对”（取值“1”）或“无源比对”（取值“0”）
     * @param livenessPreferences 表示一系列可以放松或加强活体检测的特别的选项 none selfie_no_metadata_check video_strict
     * @param idcardName comparisonType=1 时提供
     * @param idcardNumber comparisonType=1 时提供
     * @param photoContent 图片
     * @param uuid comparisonType=0 时提供 用于标志本次识别对应的用户的唯一ID
     * @return
     */
    FaceVerifyResult Verify(String token, String bizNo, String idcardName, String idcardNumber, byte[] photoContent,
        String uuid,int userId);

    /**
     * h5页面验证时 先获取token
     * 
     * @param bizNo
     * @param comparison_type
     * @param idcard_mode
     * @param idcard_name
     * @param idcard_number
     * @param uuid 如果用户不使用数据源的数据进行比对，则上传此字段 用于标志本次识别对应的用户的唯一ID comparison_type ＝ 0
     * @return
     */
    JSONObject getToken(String bizNo, String bizExtraData, String uuid);
    
    IdCard faceOCR(int userId,byte[] photoContent);

}
