package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.MsgToSalesDao;
import com.jbb.mgt.core.dao.mapper.MsgToSalesMapper;
import com.jbb.mgt.core.domain.PlatformMsgGroup;
import com.jbb.mgt.core.domain.PlatformMsgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository("MsgToSalesDao")
public class MsgToSalesDaoImpl implements MsgToSalesDao {
    @Autowired
    private MsgToSalesMapper mapper;

    @Override
    public List<PlatformMsgVo> getPlatformMsgVo(Timestamp startDate, Integer amount, Integer cnt) {
        return mapper.getPlatformMsgVo(startDate, amount, cnt);
    }

    @Override
    public List<PlatformMsgGroup> getPlatformMsgGroup(Timestamp startDate) {
        return mapper.getPlatformMsgGroup(startDate);
    }
}
