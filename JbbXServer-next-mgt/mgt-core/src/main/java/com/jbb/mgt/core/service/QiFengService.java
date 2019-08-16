package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.HumanLpPhone;

public interface QiFengService {

    // 实现实时的推送数据至 企峰 的电销呼叫系统
    void qiFengCustAdd(List<HumanLpPhone> humans);
}
