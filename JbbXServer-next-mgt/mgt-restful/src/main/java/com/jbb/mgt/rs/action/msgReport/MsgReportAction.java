package com.jbb.mgt.rs.action.msgReport;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;

import com.jbb.mgt.core.service.*;
import com.jbb.mgt.rs.action.msgCode.MsgCodeAction;
import com.jbb.server.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.OrgRecharges;
import com.jbb.mgt.core.domain.XjlMessageDetail;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;

@Service(MsgReportAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
public class MsgReportAction extends BasicAction {

    public static final String ACTION_NAME = "MsgReportAction";
    public static final String SUCCESS = "000000";
    public static final String FAIL = "111111";

    @Autowired
    private ChuangLanService chuangLanService;

    public void getMsgReport(String receiver, String pswd, String msgid, String reportTime, String mobile,
        String status, String notifyTime, String statusDesc, String uid, int length) {
        log.debug(
            ">getMsgReport() receiver:{} pswd:{} msgid:{} reportTime:{} mobile:{} status:{} notifyTime:{} statusDesc:{} uid:{} length:{}"
                + receiver + ", " + pswd + ", " + msgid + ", " + reportTime + ", " + mobile + ", " + status + ", "
                + notifyTime + ", " + statusDesc + ", " + uid + ", " + length);
        try {
            String account = PropertyManager.getProperty("jbb.mgt.chuanglang.callback.account");
            String password = PropertyManager.getProperty("jbb.mgt.chuanglang.callback.pswd");
            boolean isVerify = PropertyManager.getBooleanProperty("jbb.mgt.chuanglang.callback.verify.account", false);
            if (isVerify) {
                if (!receiver.equals(account) || !pswd.equals(password)) {
                    log.debug("getMsgReport verify account different");
                    return;
                }
            }
            if (!StringUtil.isEmpty(statusDesc)) {
                statusDesc = URLDecoder.decode(statusDesc, "utf-8");
            }
            chuangLanService.insertMsgReport(msgid, reportTime, mobile, status, notifyTime, statusDesc, uid, length);
        } catch (UnsupportedEncodingException e) {
            log.warn("statusDesc URLDecoder error" + e.toString());
        }
        log.debug("<getMsgReport()");
    }

}
