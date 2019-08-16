package com.jbb.server.core.domain;

public class RecordAttr {
    /**
     * 借款记录ID
     */
    private int recordId;
    /**
     * 属性名称
     */
    private String name;
    /**
     * 属性值
     */
    private String value;

    public RecordAttr() {

    }

    public RecordAttr(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    public RecordAttr(int recordId, String name, String value) {
        this.recordId = recordId;
        this.name = name;
        this.value = value;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LoanRecordAttr [recordId=" + recordId + ", name=" + name + ", value=" + value + "]";
    }

}