package com.jbb.mgt.core.domain;

import java.util.Set;

public class UserContact {
    private String displayName;
    private Set<String> phoneNumbers;

    public UserContact() {
        super();
    }

    public UserContact(String displayName, Set<String> phoneNumbers) {
        super();
        this.displayName = displayName;
        this.phoneNumbers = phoneNumbers;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Set<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

}
