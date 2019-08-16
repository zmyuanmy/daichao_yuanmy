package com.jbb.mgt.core.service;

public interface EntryService {

    void check(Integer userId);

    void insertEntry(Integer userId, String name, String value, boolean isHidden);
}
