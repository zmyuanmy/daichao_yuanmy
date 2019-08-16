package com.jbb.mall.core.dao;

import com.jbb.mall.core.dao.domain.User;

public interface UserDao {
    /**
     * 查询 user信息
     * 
     * @param userKey 用户key
     * @return
     */
    User selectUserByUserKey(String userKey);

}
