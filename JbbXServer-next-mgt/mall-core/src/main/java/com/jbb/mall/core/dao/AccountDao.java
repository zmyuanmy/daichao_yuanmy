package com.jbb.mall.core.dao;

import com.jbb.mall.core.dao.domain.Account;

public interface AccountDao {

    Account authenticate(String userKey);

    int[] getAccountPermissions(int accountId);

}
