package com.jbb.server.core.service.impl;

import com.jbb.server.core.dao.IouPhotoDao;
import com.jbb.server.core.domain.IouPhoto;
import com.jbb.server.core.service.IouPhotoService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by inspironsun on 2018/6/9
 */
@Service("IouPhotoService")
public class IouPhotoServiceImpl implements IouPhotoService {
    private static Logger logger = LoggerFactory.getLogger(IouPhotoServiceImpl.class);

    @Autowired
    private IouPhotoDao iouPhotoDao;

    @Override
    public void insertIouPhoto(IouPhoto iouPhoto) {
        iouPhotoDao.insertIouPhoto(iouPhoto);
    }

    @Override
    public int deleteIouPhoto(int iouPhotoId,int userId) {
        return iouPhotoDao.deleteIouPhoto(iouPhotoId,userId);
    }

    @Override
    public List<IouPhoto> selectIouPhotoListByIouCode(String iouCode) {
        return iouPhotoDao.selectIouPhotoListByIouCode(iouCode);
    }

    @Override
    public int countUserIouPhoto(int userId, String iouCode) {
        return iouPhotoDao.countUserIouPhoto(userId, iouCode);
    }
}
