package com.jbb.server.core.dao.impl;

import com.jbb.server.core.dao.IouPhotoDao;
import com.jbb.server.core.dao.mapper.IouPhotoMapper;
import com.jbb.server.core.domain.IouPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by inspironsun on 2018/6/9
 */
@Repository("IouPhotoDao")
public class IouPhotoDaoImpl implements IouPhotoDao {

    @Autowired
    private IouPhotoMapper iouPhotoMapper;

    @Override
    public void insertIouPhoto(IouPhoto iouPhoto) {
        iouPhotoMapper.insertIouPhoto(iouPhoto);
    }

    @Override
    public int deleteIouPhoto(int iouPhotoId,int userId) {
        return iouPhotoMapper.deleteIouPhoto(iouPhotoId,userId);
    }

    @Override
    public List<IouPhoto> selectIouPhotoListByIouCode(String iouCode) {
        return iouPhotoMapper.selectIouPhotoListByIouCode(iouCode);
    }

    @Override
    public int countUserIouPhoto(int userId, String iouCode) {
        return iouPhotoMapper.countUserIouPhoto(userId, iouCode);
    }
}
