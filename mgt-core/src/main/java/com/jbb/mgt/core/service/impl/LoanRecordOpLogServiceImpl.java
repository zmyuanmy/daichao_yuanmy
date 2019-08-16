package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.LoanRecordOpLogDao;
import com.jbb.mgt.core.domain.LoanOpLog;
import com.jbb.mgt.core.service.LoanRecordOpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("LoanRecordOpLogService")
public class LoanRecordOpLogServiceImpl implements LoanRecordOpLogService {

    @Autowired
    LoanRecordOpLogDao dao;

    @Override
    public void insertOpLog(LoanOpLog loanOpLog) {
        dao.insertOpLog(loanOpLog);
    }

    @Override
    public List<LoanOpLog> selectLoanRecordOpLogByLoanId(Integer loanId, Integer[] opTypes) {
        return dao.selectLoanRecordOpLogByLoanId(loanId, opTypes);
    }

    @Override
    public Integer getmoneyCount(Integer accountId, Integer op, Boolean newtype) {
        return dao.getmoneyCount(accountId, op, newtype);
    }
}
