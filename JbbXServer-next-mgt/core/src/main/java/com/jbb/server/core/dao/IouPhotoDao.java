package com.jbb.server.core.dao;

import com.jbb.server.core.domain.IouPhoto;

import java.util.List;

/**
 * Created by inspironsun on 2018/6/9
 */
public interface IouPhotoDao {

    void insertIouPhoto(IouPhoto iouPhoto);

    int deleteIouPhoto(int iouPhotoId,int userId);

    List<IouPhoto> selectIouPhotoListByIouCode(String iouCode);

    int countUserIouPhoto(int userId,String iouCode);

}
