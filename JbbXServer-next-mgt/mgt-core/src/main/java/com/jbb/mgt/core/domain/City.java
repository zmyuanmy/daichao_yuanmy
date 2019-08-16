package com.jbb.mgt.core.domain;

/**
 * @author xiaoeach
 * @date 2018/12/27
 */
public class City {
    private int zoneId;// 主键
    private int provinceId;// 省ID
    private String provinceName;// 省名
    private int cityId;// 城市ID
    private String cityName;// 城市名

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "City [zoneId=" + zoneId + ", provinceId=" + provinceId + ", provinceName=" + provinceName + ", cityId="
            + cityId + ", cityName=" + cityName + "]";
    }

}
