package com.jbb.server.core.dao;

public interface UserPrivDao {
    void saveUserPriv(int applyUserId, int userId, String privName, boolean privValue);

    boolean checkUserPrivByPrivName(int applyUserId, int userId, String privName);
}
