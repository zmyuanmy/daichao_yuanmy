package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

import com.jbb.server.common.util.DateUtil;

public class JuXinLiReport {

    public static int STATUS_INIT = 0;
    public static int STATUS_COMPLATE = 99;
    public static int STATUS_COllECT = 2;
    public static int STATUS_ERROR = 1;

    public static final String SPLIT_TASK_ID_CHAR = "_";
    public static final String TYPE_JIEDAIBAO = "jiedaibao";
    public static final String TYPE_MIFANG = "mifang";
    public static final String TYPE_WUYOU = "wuyoujietiao";
    public static final String TYPE_JINJIEDAO = "jinjiedao";

    private String token;
    private int userId;
    private int applyId;
    private String reportType;
    private int status;
    private Timestamp creationDate;
    private String data;

    public JuXinLiReport() {

    }

    public JuXinLiReport(String taskId, int userId, int applyId, String reportType, String token) {
        super();
        this.token = token;
        this.userId = userId;
        this.applyId = applyId;
        this.reportType = reportType;

        this.status = STATUS_INIT;
        this.creationDate = DateUtil.getCurrentTimeStamp();
    }

    public JuXinLiReport(JuXinLiNotify notify) {
        this.setToken(notify.getToken());
        String[] arr = notify.getUser_id().split(SPLIT_TASK_ID_CHAR);
        this.setUserId(Integer.valueOf(arr[0]));
        this.setApplyId(Integer.valueOf(arr[1]));
        this.setReportType(notify.getWebsite());
        this.status = STATUS_INIT;
        this.creationDate = DateUtil.getCurrentTimeStamp();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JuXinLiReport [token=" + token + ", userId=" + userId + ", applyId=" + applyId + ", reportType="
            + reportType + ", status=" + status + ", creationDate=" + creationDate + ", data=" + data + "]";
    }

}
