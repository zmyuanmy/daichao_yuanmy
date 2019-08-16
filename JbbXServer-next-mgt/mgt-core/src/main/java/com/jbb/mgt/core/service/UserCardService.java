package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.UserCard;

import java.util.List;

public interface UserCardService {

    List<UserCard> selectUserCards(Integer userId, String payProductId);

    void insertUserCard(UserCard userCard);

    void updateUserCard(UserCard userCard);

    UserCard selectUserCardByCardNo(int userId, String cardNo, String payProductId);

    void acceptUserCard(Integer userId, String cardNo, String payProductId);

    UserCard selectAcceptUserCard(int userId, String payProductId);

}
