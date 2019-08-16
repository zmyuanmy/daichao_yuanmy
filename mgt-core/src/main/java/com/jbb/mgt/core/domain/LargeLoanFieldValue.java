package com.jbb.mgt.core.domain;

public class LargeLoanFieldValue {
    private Integer fieldId;
    private String fieldValue;
    private String desc;
    private Integer displayIndex;
    public Integer getFieldId() {
        return fieldId;
    }
    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }
    public String getFieldValue() {
        return fieldValue;
    }
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public Integer getDisplayIndex() {
        return displayIndex;
    }
    public void setDisplayIndex(Integer displayIndex) {
        this.displayIndex = displayIndex;
    }
    @Override
    public String toString() {
        return "LargeLoanFieldValue [fieldId=" + fieldId + ", fieldValue=" + fieldValue + ", desc=" + desc
            + ", displayIndex=" + displayIndex + "]";
    }

   

}
