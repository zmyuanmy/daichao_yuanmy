package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.UserCard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCardDao {

    List<UserCard> selectUserCards(Integer userId, String payProductId, boolean accept);

    void insertUserCard(UserCard userCard);

    void updateUserCard(UserCard userCard);

    UserCard selectUserCardByCardNo(int userId, String cardNo, String payProductId);

    void removeUserCardAccept(Integer userId);

    void acceptUserCard(String cardNo, String payProductId);

}
