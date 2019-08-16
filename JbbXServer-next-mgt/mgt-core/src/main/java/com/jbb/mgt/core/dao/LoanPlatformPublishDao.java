package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.LoanPlatformPublish;

public interface LoanPlatformPublishDao {
    // 新增发布
    void insertPlatformPublish(Integer platformId, Timestamp publishDate);

    // 更新发布
    void updatePlatformPublish(Integer id, Integer platformId, Timestamp publishDate);

    // 删除发布
    void deletePlatformPublish(Integer id);

    // 查询发布
    LoanPlatformPublish selectPlatformPublish(Integer id, Integer platformId);

    // 获取列表
    List<LoanPlatformPublish> selectPlatformPublishByDate(Timestamp startDate, Timestamp endDate, boolean isDeleted);

}
