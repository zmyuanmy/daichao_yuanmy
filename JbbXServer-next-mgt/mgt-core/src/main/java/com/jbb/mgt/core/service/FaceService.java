package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.FaceRandomResult;
import com.jbb.mgt.core.domain.FaceValidateVideoResult;
import com.jbb.mgt.core.domain.FaceVerifyResult;
import com.jbb.mgt.core.domain.IdCard;

public interface FaceService {

    IdCard faceOCR(int userId, byte[] photoContent);

    FaceRandomResult GetRandomNumber(String bizNo);

    FaceValidateVideoResult ValidateVideo(String tokenRandomNumber, String bizNo, int userId, byte[] content);

    FaceVerifyResult Verify(String token, String bizNo, String idcardName, String idcardNumber, byte[] photoContent,
        String uuid, int userId);
}
