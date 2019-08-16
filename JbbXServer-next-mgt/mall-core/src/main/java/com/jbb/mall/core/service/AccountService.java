package com.jbb.mall.core.service;

import com.jbb.mall.core.dao.domain.Account;
import com.jbb.server.common.exception.WrongUserKeyException;

public interface AccountService {

    // 账户通过userKey登录
    Account login(String userKey) throws WrongUserKeyException;

}
