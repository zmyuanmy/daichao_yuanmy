package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.UserCardDao;
import com.jbb.mgt.core.dao.mapper.UserCardMapper;
import com.jbb.mgt.core.dao.mapper.UserMapper;
import com.jbb.mgt.core.domain.UserCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserCardDao")
public class UserCardDaoImpl implements UserCardDao {

    @Autowired
    private UserCardMapper userCardMapper;

    @Override
    public List<UserCard> selectUserCards(Integer userId, String payProductId, boolean accept) {
        return userCardMapper.selectUserCards(userId, payProductId, accept);
    }

    @Override
    public void insertUserCard(UserCard userCard) {
        userCardMapper.insertUserCard(userCard);
    }

    @Override
    public void updateUserCard(UserCard userCard) {
        userCardMapper.updateUserCard(userCard);
    }

    @Override
    public UserCard selectUserCardByCardNo(int userId, String cardNo, String payProductId) {
        return userCardMapper.selectUserCardByCardNo(userId, cardNo, payProductId);
    }

    @Override
    public void removeUserCardAccept(Integer userId) {
        userCardMapper.removeUserCardAccept(userId);
    }

    @Override
    public void acceptUserCard(String cardNo, String payProductId) {
        userCardMapper.acceptUserCard(cardNo, payProductId);
    }
}
