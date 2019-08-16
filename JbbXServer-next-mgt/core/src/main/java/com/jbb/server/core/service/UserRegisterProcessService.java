package com.jbb.server.core.service;

import java.util.Set;

import com.jbb.server.core.domain.User;

public interface UserRegisterProcessService {

    Set<User> applyToLendUser(int userId);

    void printCnt();
}
