package com.jbb.mgt.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.XjlPayBank;

public interface XjlPayBankMapper {
    // 获取绑卡支持银行列表
    List<XjlPayBank> selectXjlPayBank(@Param("payProductId") String payProductId);

}
