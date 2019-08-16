package com.jbb.mgt.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.AreaPlatformVo;
import com.jbb.mgt.core.domain.LoanPlatformTag;
import com.jbb.mgt.core.domain.LoanTag;
import com.jbb.mgt.core.domain.Platform;
import com.jbb.mgt.core.domain.PlatformVo;

/**
 * @author wyq
 * @date 2018/8/30 16:26
 */
public interface LoanPlatformMapper {

    void insertLoanTag(LoanTag tag);

    void updateLoanTag(LoanTag tag);

    // 删除标签，删除同时，删除表mgt_loan_platform_tags 下相同tagId的所有记录
    void deleteLoanTag(@Param("tagId") int tagId);

    // 从表mgt_loan_tags中获取, 按pos排序
    List<LoanTag> selectLoanTags(@Param("isHidden") Boolean isHidden);

    void insertPlatform(Platform platform);

    void updatePlatform(Platform platform);

    void updatePlatformStatus(@Param("platformId") int platformId, @Param("isDeleted") boolean isDeleted);

    Platform selectPlatformById(@Param("platformId") int platformId);

    void deletePlatformTagByTagId(@Param("tagId") int tagId);

    List<PlatformVo> selectPlatforms(@Param("isDeleted") Boolean isDeleted, @Param("areas") int[] areas,
        @Param("tagNames") String[] tagNames, @Param("groupName") String groupName, @Param("type") Integer type,
        @Param("platformName") String platformName, @Param("salesId") Integer salesId);

    List<AreaPlatformVo> selectPlatformsFromUI(@Param("areas") int[] areas, @Param("os") String os);

    LoanTag selectLoanTagByAreaTagId(@Param("areaTagId") int areaTagId);

    List<LoanPlatformTag> selectPlatformTags(@Param("areaId") Integer areaId, @Param("tagName") String tagName,
        @Param("platformId") Integer platformId, @Param("pos") Integer pos);

    List<LoanTag> selectLoanTagByAreaId(@Param("areaId") int areaId);

    List<String> getPlatformGroupName();

    void savePlatform(Platform platform);

    Platform getPlatformByShortName(@Param("shortName") String shortName, @Param("platformId") Integer platformId);

    // 随机获取一个首页强推
    Platform selectPlatformHomePagePush(@Param("os") String os);

    void updateLoanTagsStatus(@Param("areaTagId") Integer areaTagId, @Param("freeze") boolean freeze);
}
