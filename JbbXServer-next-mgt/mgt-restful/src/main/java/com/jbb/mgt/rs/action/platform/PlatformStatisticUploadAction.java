package com.jbb.mgt.rs.action.platform;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.FinOpLog;
import com.jbb.mgt.core.domain.LoanPlatformStatistic;
import com.jbb.mgt.core.domain.Platform;
import com.jbb.mgt.core.service.FinOpLogService;
import com.jbb.mgt.core.service.LoanPlatformReportService;
import com.jbb.mgt.core.service.LoanPlatformService;
import com.jbb.mgt.rs.action.batch.POIUtil;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service(PlatformStatisticUploadAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
public class PlatformStatisticUploadAction extends BasicAction {
    public static final String ACTION_NAME = "PlatformStatisticUploadAction";
    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private LoanPlatformReportService loanPlatformReportService;
    @Autowired
    private LoanPlatformService loanPlatformService;

    private Response response;
    @Autowired
    private PlatformTransactionManager txManager;
    @Autowired
    private FinOpLogService finOpLogService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void platformStatisticUpload(byte[] content, String fileName) throws IOException {
        if ((content == null) || (content.length == 0)) {
            log.debug("<uploadFile() missing file content");
            throw new WrongParameterValueException("jbb.mgt.exception.file.empty");
        }
        TransactionStatus txStatus = null;
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        InputStream input = new ByteArrayInputStream(content);
        List<String[]> List = POIUtil.readExcel(fileName, input);
        for (int i = 0; i < List.size(); i++) {
            String[] cell = List.get(i);
            try {
                txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
                Integer platformId = Integer.parseInt(cell[1]);
                String statisticDate = cell[0];
                int cnt = StringUtil.isEmpty(cell[3]) ? 0 : Integer.parseInt(cell[3]);
                int puvCnt = StringUtil.isEmpty(cell[4]) ? 0 : Integer.parseInt(cell[4]);
                int price = (int)(StringUtil.isEmpty(cell[5]) ? 0 : Double.parseDouble(cell[5]) * 100);
                LoanPlatformStatistic loanPlatformStatistic
                    = loanPlatformReportService.selectPlatformByStaDate(platformId, statisticDate, 0);
                Platform p = loanPlatformService.getPlatformById(platformId);
                if (p != null) {

                    if (null == loanPlatformStatistic) {
                        loanPlatformStatistic = generatePlatform(null, cnt, puvCnt, price);
                        String statisticDateTime = statisticDate + " 00:00:00";
                        java.sql.Date statisticDateTimeParm
                            = new java.sql.Date(DateUtil.parseStrnew(statisticDateTime).getTime());
                        loanPlatformStatistic.setStatisticDate(statisticDateTimeParm);
                        loanPlatformStatistic.setPlatformId(platformId);
                        loanPlatformStatistic
                            .setBalance(updateBalance(loanPlatformStatistic, platformId, statisticDate));
                        loanPlatformReportService.insertPlatform(loanPlatformStatistic);
                    } else if (null != loanPlatformStatistic) {
                        if (loanPlatformStatistic.getStatus() == 1) {
                            break;
                        }

                        loanPlatformStatistic = generatePlatform(loanPlatformStatistic, cnt, puvCnt, price);
                        loanPlatformStatistic
                            .setBalance(updateBalance(loanPlatformStatistic, platformId, statisticDate));
                        loanPlatformReportService.updatePlatform(loanPlatformStatistic);
                    }
                    insertFinOpLog(this.account.getAccountId(), platformId, cnt, price, puvCnt, statisticDate);
                    txManager.commit(txStatus);
                    txStatus = null;
                } else {

                }
            } finally {
                if (txStatus == null) {
                    continue;
                }
                try {
                    txManager.rollback(txStatus);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("statisticDate", cell[0]);
                    map.put("platformId", cell[1]);
                    map.put("platformName", cell[2]);
                    list.add(map);
                    continue;
                } catch (Exception er) {
                    log.warn("Cannot rollback transaction", er);
                }

            }

        }

        this.response.list = list;
    }

    private void insertFinOpLog(Integer accountId, Integer platformId, Integer cnt, Integer price, Integer puvCnt,
        String statisticDate) {
        FinOpLog finOpLog = new FinOpLog();
        finOpLog.setSourceId(platformId.toString());
        finOpLog.setAccountId(accountId);
        finOpLog.setType(FinOpLog.PLATFORM_UPLOAD_FLAG);
        finOpLog.setParams("statisticDate:" + statisticDate + ",cnt:" + cnt + ",price:" + price + ",puvCnt:" + puvCnt);
        finOpLog.setOpDate(DateUtil.getCurrentTimeStamp());
        finOpLogService.insertFinOpLog(finOpLog);
    }

    private LoanPlatformStatistic generatePlatform(LoanPlatformStatistic loanPlatformStatistic, int cnt, int puvCnt,
        int price) {
        loanPlatformStatistic = null == loanPlatformStatistic ? new LoanPlatformStatistic() : loanPlatformStatistic;
        loanPlatformStatistic.setCnt(cnt);
        loanPlatformStatistic.setPuvCnt(puvCnt);
        loanPlatformStatistic.setPrice(price);
        loanPlatformStatistic.setExpense(
            (loanPlatformStatistic.getCnt() + loanPlatformStatistic.getPuvCnt()) * loanPlatformStatistic.getPrice());
        loanPlatformStatistic.setUpdateDate(DateUtil.getCurrentTimeStamp());
        return loanPlatformStatistic;
    }

    private int updateBalance(LoanPlatformStatistic loanPlatformStatistic, Integer platformId, String statisticDate) {
        LoanPlatformStatistic loanPlatformStatistic1
            = loanPlatformReportService.selectPlatformByStaDate(platformId, statisticDate, null);
        int balance = null != loanPlatformStatistic1 ? loanPlatformStatistic1.getBalance() : 0;// 最近一余额
        int balanceOld = null != loanPlatformStatistic ? loanPlatformStatistic.getBalance() : 0;// 获取原余额
        int balanceNew = balance + loanPlatformStatistic.getAmount() - loanPlatformStatistic.getExpense();// 更改后余额
        // 如果原余额 不等于 改后余额 就算差
        if (balanceOld != balanceNew) {
            balance = balanceNew - balanceOld;
            loanPlatformReportService.updatePlatformByBalance(balance, loanPlatformStatistic.getPlatformId(),
                loanPlatformStatistic.getStatisticDate());
        }
        return balanceNew;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @Getter
    public static class Response extends ActionResponse {
        List<Map<String, String>> list;
    }
}
