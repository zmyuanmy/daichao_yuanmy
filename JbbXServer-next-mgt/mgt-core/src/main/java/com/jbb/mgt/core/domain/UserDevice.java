package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

/**
 * 设备信息实体类
 * 
 * @author wyq
 * @date 2018/8/22 15:44
 */
public class UserDevice {
    private Integer userId;
    private String uuid;
    private String model;
    private String platform;
    private String version;
    private String manufacturer;
    private Boolean isVirtual;
    private String serial;
    private Timestamp upateDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Boolean getVirtual() {
        return isVirtual;
    }

    public void setVirtual(Boolean virtual) {
        isVirtual = virtual;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Timestamp getUpateDate() {
        return upateDate;
    }

    public void setUpateDate(Timestamp upateDate) {
        this.upateDate = upateDate;
    }

    public UserDevice() {
        super();
    }

    public UserDevice(Integer userId, String uuid, String model, String platform, String version, String manufacturer,
                      Boolean isVirtual, String serial, Timestamp upateDate) {
        this.userId = userId;
        this.uuid = uuid;
        this.model = model;
        this.platform = platform;
        this.version = version;
        this.manufacturer = manufacturer;
        this.isVirtual = isVirtual;
        this.serial = serial;
        this.upateDate = upateDate;
    }

    @Override
    public String toString() {
        return "UserDevice{" + "userId=" + userId + ", uuid='" + uuid + '\'' + ", model='" + model + '\''
            + ", platform='" + platform + '\'' + ", version='" + version + '\'' + ", manufacturer='" + manufacturer
            + '\'' + ", isVirtual=" + isVirtual + ", serial='" + serial + '\'' + ", upateDate=" + upateDate + '}';
    }
}
