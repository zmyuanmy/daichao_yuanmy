package com.jbb.mgt.core.domain;

public class Property {
    
    
    public static String SYS_LOAN_POLICY ="sys.loan.policy";
    
    public static String PREFIX_SPECIAL_ENTRY_ORGS ="sys.poliyc.entry.org.";

    private String name;
    private String value;

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

}
