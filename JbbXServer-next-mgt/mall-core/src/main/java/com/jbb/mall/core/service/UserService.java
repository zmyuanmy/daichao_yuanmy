package com.jbb.mall.core.service;

import com.jbb.mall.core.dao.domain.User;
import com.jbb.server.common.exception.WrongUserKeyException;

public interface UserService {

    // 账户通过userKey登录
    User login(String userKey) throws WrongUserKeyException;

}
