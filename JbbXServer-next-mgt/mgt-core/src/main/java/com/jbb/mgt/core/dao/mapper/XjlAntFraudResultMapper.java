package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.XjlAntiFraudResult;
import org.apache.ibatis.annotations.Param;

public interface XjlAntFraudResultMapper {

    void insert(@Param("xjlAntiFraudResult") XjlAntiFraudResult xjlAntiFraudResult);

    void update(@Param("orderId") int orderId, @Param("antiFraudResult") String antiFraudResult);
}
