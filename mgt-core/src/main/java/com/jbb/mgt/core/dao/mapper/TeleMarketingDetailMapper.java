package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.ChuangLanPhoneNumber;
import com.jbb.mgt.core.domain.TeleMarketingDetail;
import com.jbb.mgt.core.domain.TeleMarketingInit;

/**
 * 批次明细数据Mapper接口
 * 
 * @author wyq
 * @date 2018/04/29
 */
public interface TeleMarketingDetailMapper {

    int insertMobiles(@Param(value = "batchId") int batchId,
        @Param(value = "mobiles") List<TeleMarketingDetail> mobiles);

    int updateMobile(TeleMarketingDetail mobile);

    List<TeleMarketingDetail> selectMaxTeleMarketingDetail(@Param(value = "accountId") int accountId);

    List<TeleMarketingDetail> selectTeleMarketingDetails(@Param(value = "batchId") int batchId);

    /**
     * 查询该批次中在detail表中且不在init表中
     * 
     * @param batchId
     * @return
     */
    List<TeleMarketingDetail> selectTeleMarketingDetailsNotInit(@Param(value = "batchId") int batchId,
        @Param(value = "test") boolean test);

    /**
     * 批量分配或者单个分配
     * 
     * @param mobileInits
     * @return sql 执行成功的数量
     */
    int insertTeleInits(@Param(value = "mobileInits") List<TeleMarketingInit> mobileInits);

    /**
     * 查询当前用户的明细
     * 
     * @param accountId
     * @return
     */
    List<TeleMarketingInit> selectMobilesByAccountId(@Param(value = "accountId") Integer accountId);

    void updateTeleMarketingInitByIdAndOpCommet(@Param(value = "id") Integer id,
        @Param(value = "opComment") String opCommet, @Param(value = "updateDate") Timestamp updateDate);

    TeleMarketingInit selectTeleMarketingInitById(@Param(value = "id") Integer id);

    List<TeleMarketingDetail> selectTeleMarketingByStatus(@Param(value = "status") Integer status,
        @Param(value = "limit") int limit, @Param(value = "batchId") int batchId);

    Integer getPhoneCount(@Param(value = "batchId") Integer batchId, @Param(value = "status") Integer status);

    int insertPhoneNumber(ChuangLanPhoneNumber data);
}
