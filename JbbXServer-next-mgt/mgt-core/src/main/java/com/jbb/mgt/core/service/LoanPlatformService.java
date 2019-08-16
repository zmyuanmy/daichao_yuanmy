package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.AreaPlatformVo;
import com.jbb.mgt.core.domain.LoanPlatformTag;
import com.jbb.mgt.core.domain.LoanTag;
import com.jbb.mgt.core.domain.Platform;
import com.jbb.mgt.core.domain.PlatformVo;

/**
 * @author wyq
 * @date 2018/8/30 16:23
 */
public interface LoanPlatformService {

    void saveLoanTag(LoanTag tag);

    void updateLoanTag(LoanTag tag);

    // 删除标签，删除同时，删除表mgt_loan_platform_tags 下相同tagId的所有记录
    void deleteLoanTag(int tagId);

    // 从表mgt_loan_tags中获取, 按pos排序
    List<LoanTag> getLoanTags(Boolean isHidden);

    LoanTag getLoanTagByAreaTagId(int areaTagId);

    void insertPlatform(Platform platform);

    void updatePlatform(Platform platform);

    void updatePlatformStatus(int platformId,boolean isDeleted);

    Platform getPlatformById(int platformId);

    List<PlatformVo> getPlatforms(Boolean isDeleted, int[] areas, String[] tagNames, String groupName, Integer type,
        String platformName, Integer salesId);

    List<AreaPlatformVo> getPlatformsFromUI(int[] areas, String os);

    void deletePlatformTagByTagId(int tagId);

    List<LoanPlatformTag> getPlatformTags(Integer areaId, String tagName, Integer platformId, Integer pos);

    List<LoanTag> getLoanTagByAreaId(int areaId);

    List<String> getPlatformGroupName();

    void savePlatform(Platform platform);

    Platform getPlatformByShortName(String shortName, Integer platformId);

    //随机获取一个首页强推
    Platform getPlatformHomePagePush(String os);

    void updateLoanTagsStatus(Integer areaTagId, boolean freeze);
}
