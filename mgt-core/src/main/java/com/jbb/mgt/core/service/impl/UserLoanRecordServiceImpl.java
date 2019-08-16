package com.jbb.mgt.core.service.impl;

import java.util.List;
import java.util.TimeZone;
import java.util.stream.IntStream;

import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.LoanRecordOpLogDao;
import com.jbb.mgt.core.dao.UserLoanRecordDao;
import com.jbb.mgt.core.dao.UserLoanRecordDetailDao;
import com.jbb.mgt.core.domain.JbbToMgtResult;
import com.jbb.mgt.core.domain.LoanOpLog;
import com.jbb.mgt.core.domain.UserLoanRecord;
import com.jbb.mgt.core.domain.UserLoanRecordDetail;
import com.jbb.mgt.core.service.UserLoanRecordService;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.exception.WrongParameterValueException;

@Service("UserLoanRecordService")
public class UserLoanRecordServiceImpl implements UserLoanRecordService {

    @Autowired
    private UserLoanRecordDao userLoanRecordDao;

    @Autowired
    private UserLoanRecordDetailDao userLoanRecordDetailDao;

    @Autowired
    private LoanRecordOpLogDao loanRecordOpLogDao;

    @Override
    public List<UserLoanRecord> getUserLoanRecords(Integer loanId, Integer accountId, String op, Integer orgId,
        int[] statuses, int[] iouStatuses, Integer iouDateType, String phoneNumberSearch, String channelSearch,
        String usernameSearch, String idcardSearch, String jbbIdSearch) {
        return userLoanRecordDao.selectUserLoanRecords(loanId, accountId, op, orgId, statuses, iouStatuses, iouDateType,
            phoneNumberSearch, channelSearch, usernameSearch, idcardSearch, jbbIdSearch);

    }

    @Override
    public void createUserLoanRecord(UserLoanRecord userLoanRecord) {
        userLoanRecordDao.insertUserLoanRecord(userLoanRecord);
    }

    @Override
    public void updateUserLoanRecord(UserLoanRecord userLoanRecord) {
        userLoanRecordDao.updateUserLoanRecord(userLoanRecord);

    }

    @Override
    /**
     * 此方法为JBB->MGT
     */
    public void updateUserLoanRecordByIouCode(UserLoanRecord userLoanRecord, int beforeIouStatus) {
        JbbToMgtResult result = jbbToMgtStatusChange(beforeIouStatus, userLoanRecord.getIouStatus());
        userLoanRecord.setStatus(result.getStatus());

        if (userLoanRecord.getIouStatus() == 12 || userLoanRecord.getIouStatus() == 6) {
            userLoanRecord.setRepaymentDate(userLoanRecord.getExtentionDate());
        }

        if (userLoanRecord.getIouStatus() == 9 || userLoanRecord.getIouStatus() == 14) {
            userLoanRecord.setActualRepaymentDate(DateUtil.getCurrentTimeStamp());
            userLoanRecord.setRepayAmount(userLoanRecord.getBorrowingAmount());
            // 记账明细 收入明细
            // 还款为借条金额 
            UserLoanRecordDetail loanRecordDetail = new UserLoanRecordDetail(userLoanRecord.getLoanId(), 6, 2,
                userLoanRecord.getBorrowingAmount(), userLoanRecord.getAccountId());
            userLoanRecordDetailDao.insertUserLoanRecordDetail(loanRecordDetail);
        }

        userLoanRecordDao.updateUserLoanRecordByIouCode(userLoanRecord);
        loanRecordOpLogDao.insertOpLog(
            new LoanOpLog(userLoanRecord.getLoanId(), result.getOpType(), userLoanRecord.getAccountId(), null, null));
    }

    private JbbToMgtResult jbbToMgtStatusChange(int beforeIouStatus, int afterIouStatus) {
        if (afterIouStatus == 0) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "afterIouStatus");
        }
        int opType;
        Integer status = null;
        int[] afterIouStatusArray = {31, 1, 21, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        boolean contains = IntStream.of(afterIouStatusArray).anyMatch(x -> x == afterIouStatus);
        if (!contains) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.wrong.paramvalue", "zh",
                beforeIouStatus + "->" + afterIouStatus);
        }
        if (beforeIouStatus == 30 && afterIouStatus == 31) {
            opType = 101;
        } else if (beforeIouStatus == 30 && afterIouStatus == 1) {
            status = 0;
            opType = 102;
        } else if (beforeIouStatus == 20 && afterIouStatus == 21) {
            opType = 104;
        } else if (beforeIouStatus == 20 && afterIouStatus == 1) {
            status = 0;
            opType = 105;
        } else if (afterIouStatus == 5) {
            opType = 106;
        } else if (beforeIouStatus == 5 && afterIouStatus == 6) {
            status = 3;
            opType = 107;
        } else if (beforeIouStatus == 5 && afterIouStatus == 7) {
            opType = 108;
        } else if (afterIouStatus == 8) {
            opType = 109;
        } else if (beforeIouStatus == 8 && afterIouStatus == 9) {
            opType = 110;
        } else if (beforeIouStatus == 8 && afterIouStatus == 10) {
            opType = 111;
        } else if (afterIouStatus == 11) {
            opType = 112;
        } else if (beforeIouStatus == 11 && afterIouStatus == 12) {
            status = 3;
            opType = 113;
        } else if (beforeIouStatus == 11 && afterIouStatus == 13) {
            opType = 114;
        } else if (afterIouStatus == 14) {
            status = 8;
            opType = 115;
        } else {
            throw new WrongParameterValueException("jbb.mgt.error.exception.wrong.paramvalue", "zh",
                beforeIouStatus + "->" + afterIouStatus);
        }
        return new JbbToMgtResult(status, opType);
    }

    @Override
    public UserLoanRecord getUserLoanRecordByLoanId(int loanId) {

        return userLoanRecordDao.selectUserLoanRecordByLoanId(loanId);
    }

    @Override
    public void saveUserLoanRecordDetail(UserLoanRecordDetail loanRecordDetail) {
        userLoanRecordDetailDao.insertUserLoanRecordDetail(loanRecordDetail);

    }

    @Override
    public List<UserLoanRecordDetail> getUserLoanRecordDetails(int loanId) {
        return userLoanRecordDetailDao.selectUserLoanRecordDetails(loanId);
    }

    @Override
    public UserLoanRecord selectUserLoanRecordByIouCode(String iouCode) {
        return userLoanRecordDao.selectUserLoanRecordByIouCode(iouCode);
    }

}
