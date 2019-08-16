 package com.jbb.mgt.core.dao;

import java.util.List;

import com.jbb.mgt.core.domain.XjlPayBank;

public interface XjlPayBankDao {
    // 获取绑卡支持银行列表
     List<XjlPayBank> selectXjlPayBank(String payProductId);
}
