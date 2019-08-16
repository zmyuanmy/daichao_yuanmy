package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

import com.jbb.server.common.util.DateUtil;

/**
 * 批次明细数据表实体类
 * 
 * @author wyq
 * @date 2018/04/29
 */
public class TeleMarketingDetail {
    // 0：空号；1：实号；2：停机；3:库无；4:沉默号；5：导入; 6: 重复号；7：不符合电话格式',
    public static int STATUS_EMPTY = 1;
    public static int STATUS_REAL = 2;
    public static int STATUS_STOP = 3;
    public static int STATUS_NONE = 4;
    public static int STATUS_INIT = 5;
    public static int STATUS_SILENCE = 6;
    public static int STATUS_INVALID_FORMAT = 7;

    private int id;
    private int batchId;
    private String telephone;
    private String username;
    private String area;
    private String numberType;
    private String chargesStatus;
    private Timestamp lastDate;
    private int status;
    private Timestamp updateDate;
    private Account account;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    public String getChargesStatus() {
        return chargesStatus;
    }

    public void setChargesStatus(String chargesStatus) {
        this.chargesStatus = chargesStatus;
    }

    public Timestamp getLastDate() {
        return lastDate;
    }

    public void setLastDate(Timestamp lastDate) {
        this.lastDate = lastDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TeleMarketingDetail(int batchId, String telephone, String username, String area, String numberType,
        String chargesStatus, Timestamp lastDate, int status, Timestamp updateDate) {
        super();
        this.batchId = batchId;
        this.telephone = telephone;
        this.username = username;
        this.area = area;
        this.numberType = numberType;
        this.chargesStatus = chargesStatus;
        this.lastDate = lastDate;
        this.status = status;
        this.updateDate = updateDate;
    }

    public TeleMarketingDetail() {
        super();
    }

    public TeleMarketingDetail(String telephone, String username, int status) {
        super();
        this.telephone = telephone;
        this.username = username;
        this.status = status;
        this.updateDate = DateUtil.getCurrentTimeStamp();
    }

    public TeleMarketingDetail(int id, int batchId, String telephone, String username, String area, String numberType,
        String chargesStatus, Timestamp lastDate, int status, Timestamp updateDate, Account account) {
        this.id = id;
        this.batchId = batchId;
        this.telephone = telephone;
        this.username = username;
        this.area = area;
        this.numberType = numberType;
        this.chargesStatus = chargesStatus;
        this.lastDate = lastDate;
        this.status = status;
        this.updateDate = updateDate;
        this.account = account;
    }

    @Override
    public String toString() {
        return "TeleMarketingDetail{" + "id=" + id + ", batchId=" + batchId + ", telephone='" + telephone + '\''
            + ", username='" + username + '\'' + ", area='" + area + '\'' + ", numberType='" + numberType + '\''
            + ", chargesStatus='" + chargesStatus + '\'' + ", lastDate=" + lastDate + ", status=" + status
            + ", updateDate=" + updateDate + ", account=" + account + '}';
    }
}
