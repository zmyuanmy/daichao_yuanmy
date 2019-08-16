package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.LoanPlatformDao;
import com.jbb.mgt.core.dao.mapper.LoanPlatformMapper;
import com.jbb.mgt.core.domain.AreaPlatformVo;
import com.jbb.mgt.core.domain.LoanPlatformTag;
import com.jbb.mgt.core.domain.LoanTag;
import com.jbb.mgt.core.domain.Platform;
import com.jbb.mgt.core.domain.PlatformVo;

/**
 * @author wyq
 * @date 2018/8/30 16:26
 */
@Repository("LoanPlatformDao")
public class LoanPlatformDaoImpl implements LoanPlatformDao {

    @Autowired
    private LoanPlatformMapper mapper;

    @Override
    public void insertLoanTag(LoanTag tag) {
        mapper.insertLoanTag(tag);
    }

    @Override
    public void updateLoanTag(LoanTag tag) {
        mapper.updateLoanTag(tag);
    }

    @Override
    public void deleteLoanTag(int tagId) {
        mapper.deleteLoanTag(tagId);
    }

    @Override
    public List<LoanTag> selectLoanTags(Boolean isHidden) {
        return mapper.selectLoanTags(isHidden);
    }

    @Override
    public void deletePlatformTagByTagId(int tagId) {
        mapper.deletePlatformTagByTagId(tagId);
    }

    @Override
    public void insertPlatform(Platform platform) {
        mapper.insertPlatform(platform);

    }

    @Override
    public void updatePlatform(Platform platform) {
        mapper.updatePlatform(platform);

    }

    @Override
    public void updatePlatformStatus(int platformId, boolean isDeleted) {
        mapper.updatePlatformStatus(platformId, isDeleted);

    }

    @Override
    public Platform selectPlatformById(int platformId) {
        return mapper.selectPlatformById(platformId);
    }

    @Override
    public List<PlatformVo> selectPlatforms(Boolean isDeleted, int[] areas, String[] tagNames, String groupName,
        Integer type, String platformName, Integer salesId) {
        return mapper.selectPlatforms(isDeleted, areas, tagNames, groupName, type, platformName, salesId);
    }

    @Override
    public List<AreaPlatformVo> selectPlatformsFromUI(int[] areas, String os) {
        return mapper.selectPlatformsFromUI(areas, os);
    }

    @Override
    public LoanTag selectLoanTagByAreaTagId(int areaTagId) {
        return mapper.selectLoanTagByAreaTagId(areaTagId);
    }

    @Override
    public List<LoanPlatformTag> selectPlatformTags(Integer areaId, String tagName, Integer platformId, Integer pos) {
        return mapper.selectPlatformTags(areaId, tagName, platformId, pos);
    }

    @Override
    public List<LoanTag> selectLoanTagByAreaId(int areaId) {
        return mapper.selectLoanTagByAreaId(areaId);
    }

    @Override
    public List<String> getPlatformGroupName() {
        return mapper.getPlatformGroupName();
    }

    @Override
    public void savePlatform(Platform platform) {
        mapper.savePlatform(platform);

    }

    @Override
    public Platform getPlatformByShortName(String shortName, Integer platformId) {
        // TODO Auto-generated method stub
        return mapper.getPlatformByShortName(shortName, platformId);
    }

    @Override
    public Platform selectPlatformHomePagePush(String os) {
        return mapper.selectPlatformHomePagePush(os);
    }

    @Override
    public void updateLoanTagsStatus(Integer areaTagId, boolean freeze) {
        mapper.updateLoanTagsStatus(areaTagId, freeze);
    }
}
