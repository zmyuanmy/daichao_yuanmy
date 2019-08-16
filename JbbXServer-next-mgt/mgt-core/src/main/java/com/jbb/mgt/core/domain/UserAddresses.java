package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;

/**
 * 用户地址信息实体类
 *
 * @author wyq
 * @date 2018/8/16 10:37
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAddresses {
    private int addressId;
    private int type;
    private int userId;
    private String province;
    private String city;
    private String area;
    private String address;
    private int isDeleted;
    private Timestamp creationDate;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserAddresses() {
        super();
    }

    public UserAddresses(int addressId, int type, int userId, String province, String city, String area, String address,
        int isDeleted, Timestamp creationDate) {
        this.addressId = addressId;
        this.type = type;
        this.userId = userId;
        this.province = province;
        this.city = city;
        this.area = area;
        this.address = address;
        this.isDeleted = isDeleted;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "UserAddresses{" + "addressId=" + addressId + ", type=" + type + ", userId=" + userId + ", province='"
            + province + '\'' + ", city='" + city + '\'' + ", area='" + area + '\'' + ", address='" + address + '\''
            + ", isDeleted=" + isDeleted + ", creationDate=" + creationDate + '}';
    }
}
