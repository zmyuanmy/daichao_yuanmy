package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.XjlPayBank;

public interface XjlPayBankService {

    // 获取绑卡支持银行列表
    List<XjlPayBank> selectXjlPayBank(String payProductId);
}
