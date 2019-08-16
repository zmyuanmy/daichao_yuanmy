package com.jbb.server.core.dao.mapper;

import com.jbb.server.core.domain.IouPhoto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by inspironsun on 2018/6/9
 */
public interface IouPhotoMapper {

    void insertIouPhoto(IouPhoto iouPhoto);

    int deleteIouPhoto(@Param("id") int id,@Param("userId") int userId);

    List<IouPhoto> selectIouPhotoListByIouCode(@Param("iouCode") String iouCode);

    int countUserIouPhoto(@Param("userId") int userId, @Param("iouCode") String iouCode);
}
