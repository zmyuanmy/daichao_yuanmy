package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.UserCard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCardMapper {

    List<UserCard> selectUserCards(@Param(value = "userId") Integer userId,
        @Param(value = "payProductId") String payProductId, @Param(value = "accept") boolean accept);

    void insertUserCard(UserCard userCard);

    void updateUserCard(UserCard userCard);

    void removeUserCardAccept(@Param(value = "userId") Integer userId);

    UserCard selectUserCardByCardNo(@Param(value = "userId") int userId, @Param(value = "cardNo") String cardNo,
        @Param(value = "payProductId") String payProductId);

    void acceptUserCard(@Param(value = "cardNo") String cardNo, @Param(value = "payProductId") String payProductId);

}
