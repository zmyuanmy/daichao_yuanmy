package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.LoanPlatformPublish;

public interface LoanPlatformPublishMapper {

    // 新增发布
    void insertPlatformPublish(@Param("platformId") Integer platformId, @Param("publishDate") Timestamp publishDate);

    // 更新发布
    void updatePlatformPublish(@Param("id") Integer id, @Param("platformId") Integer platformId,
        @Param("publishDate") Timestamp publishDate);

    // 删除发布
    void deletePlatformPublish(@Param("id") Integer id);

    LoanPlatformPublish selectPlatformPublish(@Param("id") Integer id, @Param("platformId") Integer platformId);

    // H5页面类 获取列表
    List<LoanPlatformPublish> selectPlatformPublishByDate(@Param("startDate") Timestamp startDate,
        @Param("endDate") Timestamp endDate, @Param("isDeleted") boolean isDeleted);

}
