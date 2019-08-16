package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.AccountKey;
import com.jbb.mgt.core.domain.LoginLog;
import com.jbb.mgt.core.domain.Roles;

public interface AccountMapper {
    void insertAccount(Account Account);

    Account selectAccountById(@Param(value = "accountId") int accountId, @Param(value = "orgId") Integer orgId);

    Account selectAccountByJbbUserId(@Param(value = "jbbUserId") int jbbUserId);

    List<Account> selectAccounts(@Param(value = "orgId") int orgId);

    void updateAccount(Account Account);

    boolean deleteAccount(@Param(value = "accountId") int accountId);

    boolean updateAccountRoles(@Param(value = "accountId") int accountId, @Param("roles") int[] roles);

    List<Integer> selectAccountRoles(@Param(value = "accountId") int accountId);

    boolean deleteAccountRoles(@Param(value = "accountId") int accountId);

    void insertAccountRole(@Param(value = "accountId") int accountId, @Param(value = "roleId") int roleId);

    void insertUserKey(AccountKey userKey);

    AccountKey selectUserKey(int userId);

    void updateUserKey(AccountKey userKey);

    int deleteUserKey(String userKey);

    void deleteUserKeyByAccountId(@Param(value = "accountId") int accountId);

    Account selectAccountByUsername(@Param(value = "username") String username);

    Account selectAccountByKey(@Param(value = "userKey") String userKey, @Param("expiry") Timestamp expiry);

    int[] selectAccountPermissions(@Param(value = "accountId") int accountId);

    void updatePassword(@Param(value = "accountId") int accountId, @Param(value = "password") String password);

    void updateAccountStatus(@Param(value = "accountId") Integer accountId, @Param(value = "status") boolean status);

    int checkUsernameExist(@Param(value = "username") String username);

    void insertLoginLog(LoginLog loginLog);

    List<LoginLog> selectLoginLogs(@Param(value = "orgId") Integer orgId,
        @Param(value = "accountId") Integer accountId);

    void insertupAccounts(@Param(value = "accountId") int accountId, @Param(value = "upAccountsId") int upAccountsId,
        @Param(value = "roleId") int roleId);

    void insertdownAccounts(@Param(value = "accountId") int accountId,
        @Param(value = "downAccountsId") int downAccountsId, @Param(value = "roleId") int roleId);

    List<Integer> selectupAccounts(@Param(value = "accountId") int accountId);

    List<Integer> selectdownAccounts(@Param(value = "accountId") int accountId);

    void deleteUpAccounts(@Param(value = "accountId") int accountId);

    void deleteDownAccounts(@Param(value = "accountId") int accountId);

    void deleteDUpAccounts(@Param(value = "accountId") int accountId);

    void deleteUDownAccounts(@Param(value = "accountId") int accountId);

    List<Roles> selectAccountRole(@Param(value = "accountId") int accountId);

    List<Integer> select(@Param(value = "accountId") int accountId, @Param(value = "roleId") int roleId,
        @Param(value = "depRelation") int depRelation);

    String selectDescprition(@Param(value = "roleId") int roleId);

    List<Account> selectAccountByRoleId(@Param(value = "orgId") Integer orgId, @Param(value = "roleId") int roleId);

    List<Account> selectDownAccountByRoleId(@Param(value = "accountId") Integer accountId,
        @Param(value = "roleId") int roleId);

    void freezeAccount(@Param(value = "accountId") int accountId);

    void thawAccount(@Param(value = "accountId") int accountId);

    Integer getAccountCount(@Param(value = "orgId") Integer orgId);

    int checkJbbIdExist(@Param(value = "jbbUserId") Integer jbbUserId);

    Account selectOrgAdminAccount(@Param(value = "orgId") int orgId, @Param(value = "orgType") Integer orgType);
    
    List<Account> selectOrgAutoAssignAccounts(@Param(value = "orgId") int orgId, @Param(value = "assignType") int assignType, 
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate,  
        @Param(value = "accountId") Integer accountId);

    List<Account> selectAccountByRoleIds(@Param(value = "orgId")int orgId, @Param(value = "roleIds")int[] roleIds);
    
    
    List<Account> countOrgAccountInitNumber(@Param(value = "orgId") int orgId, @Param(value = "assignType") int assignType, 
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate,  
        @Param(value = "accountId") Integer accountId);

    Account getAccountByNickName(@Param(value = "nickname")String nickname);

}
