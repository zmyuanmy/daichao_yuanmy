package com.jbb.mgt.core.domain;

import java.util.List;

public class LargeLoanField {
    private Integer fieldId;
    private String fieldName;
    private String desc;
    private Integer type;
    private Integer area;
    private Integer displyIndex;
    private boolean required;
    private List<LargeLoanFieldValue> largeLoanFieldValues;

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getDisplyIndex() {
        return displyIndex;
    }

    public void setDisplyIndex(Integer displyIndex) {
        this.displyIndex = displyIndex;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public List<LargeLoanFieldValue> getLargeLoanFieldValues() {
        return largeLoanFieldValues;
    }

    public void setLargeLoanFieldValues(List<LargeLoanFieldValue> largeLoanFieldValues) {
        this.largeLoanFieldValues = largeLoanFieldValues;
    }

    @Override
    public String toString() {
        return "LargeLoanField [fieldId=" + fieldId + ", fieldName=" + fieldName + ", desc=" + desc + ", type=" + type
            + ", area=" + area + ", displyIndex=" + displyIndex + ", required=" + required + ", largeLoanFieldValues="
            + largeLoanFieldValues + "]";
    }

}
