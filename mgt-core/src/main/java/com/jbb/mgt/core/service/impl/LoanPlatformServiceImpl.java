package com.jbb.mgt.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.jbb.mgt.core.dao.LoanPlatformDao;
import com.jbb.mgt.core.dao.LoanPlatformTagDao;
import com.jbb.mgt.core.domain.AreaPlatformVo;
import com.jbb.mgt.core.domain.LoanPlatformTag;
import com.jbb.mgt.core.domain.LoanTag;
import com.jbb.mgt.core.domain.Platform;
import com.jbb.mgt.core.domain.PlatformVo;
import com.jbb.mgt.core.service.LoanPlatformService;

/**
 * @author wyq
 * @date 2018/8/30 16:24
 */
@Service("LoanPlatformService")
public class LoanPlatformServiceImpl implements LoanPlatformService {

    @Autowired
    private LoanPlatformDao dao;
    @Autowired
    private LoanPlatformTagDao loanPlatformTagDao;
    @Autowired
    private PlatformTransactionManager txManager;
    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    private static Logger logger = LoggerFactory.getLogger(LoanPlatformServiceImpl.class);

    private void rollbackTransaction(TransactionStatus txStatus) {
        if (txStatus == null) {
            return;
        }
        try {
            txManager.rollback(txStatus);
        } catch (Exception er) {
            logger.warn("Cannot rollback transaction", er);
        }
    }

    @Override
    public void saveLoanTag(LoanTag tag) {
        dao.insertLoanTag(tag);
    }

    @Override
    public void updateLoanTag(LoanTag tag) {
        dao.updateLoanTag(tag);
    }

    @Override
    public void deleteLoanTag(int tagId) {
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            dao.deleteLoanTag(tagId);
            dao.deletePlatformTagByTagId(tagId);
            txManager.commit(txStatus);
            txStatus = null;
        } catch (Exception e) {
            rollbackTransaction(txStatus);
        }
    }

    @Override
    public List<LoanTag> getLoanTags(Boolean isHidden) {
        return dao.selectLoanTags(isHidden);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertPlatform(Platform platform) {
        dao.insertPlatform(platform);
        List<LoanPlatformTag> loanPlatformTags = platform.getPlatformTags();
        if (CollectionUtils.isNotEmpty(loanPlatformTags)) {
            for (int i = 0; i < loanPlatformTags.size(); i++) {
                loanPlatformTagDao.insertLoanPlatformTag(loanPlatformTags.get(i));
            }
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePlatform(Platform platform) {
        dao.updatePlatform(platform);
        /*loanPlatformTagDao.deleteLoanPlatformTag(platform.getPlatformId(), null, null);
        List<LoanPlatformTag> loanPlatformTags = platform.getPlatformTags();
        if (CollectionUtils.isNotEmpty(loanPlatformTags)) {
            for (int i = 0; i < loanPlatformTags.size(); i++) {
                loanPlatformTagDao.insertLoanPlatformTag(loanPlatformTags.get(i));
            }
        }*/

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePlatformStatus(int platformId, boolean isDeleted) {
        dao.updatePlatformStatus(platformId, isDeleted);
        if (isDeleted) {
            loanPlatformTagDao.deleteLoanPlatformTag(platformId, null, null);
        }
    }

    @Override
    public Platform getPlatformById(int platformId) {
        return dao.selectPlatformById(platformId);
    }

    @Override
    public void deletePlatformTagByTagId(int tagId) {
        dao.deletePlatformTagByTagId(tagId);
    }

    @Override
    public List<PlatformVo> getPlatforms(Boolean isDeleted, int[] areas, String[] tagNames, String groupName,
        Integer type, String platformName, Integer salesId) {
        return dao.selectPlatforms(isDeleted, areas, tagNames, groupName, type, platformName, salesId);
    }

    @Override
    public List<AreaPlatformVo> getPlatformsFromUI(int[] areas, String os) {
        List<AreaPlatformVo> areaList = dao.selectPlatformsFromUI(areas, os);
        for (int i = 0; i < areaList.size(); i++) {
            AreaPlatformVo areaPlatformVo = areaList.get(i);
            if (areaPlatformVo != null && areaPlatformVo.getAreaId().equals(6)) {
                List<Platform> list = new ArrayList<Platform>();
                if (dao.selectPlatformHomePagePush(os) != null) {
                    list.add(dao.selectPlatformHomePagePush(os));
                }
                areaPlatformVo.setPlatforms(list);
            }
        }
        return areaList;
    }

    @Override
    public LoanTag getLoanTagByAreaTagId(int areaTagId) {
        return dao.selectLoanTagByAreaTagId(areaTagId);
    }

    @Override
    public List<LoanPlatformTag> getPlatformTags(Integer areaId, String tagName, Integer platformId, Integer pos) {
        return dao.selectPlatformTags(areaId, tagName, platformId, pos);
    }

    @Override
    public List<LoanTag> getLoanTagByAreaId(int areaId) {
        return dao.selectLoanTagByAreaId(areaId);
    }

    @Override
    public List<String> getPlatformGroupName() {
        return dao.getPlatformGroupName();
    }

    @Override
    public void savePlatform(Platform platform) {
        dao.savePlatform(platform);

    }

    @Override
    public Platform getPlatformByShortName(String shortName, Integer platformId) {
        return dao.getPlatformByShortName(shortName, platformId);
    }

    @Override
    public Platform getPlatformHomePagePush(String os) {
        return dao.selectPlatformHomePagePush(os);
    }

    @Override
    public void updateLoanTagsStatus(Integer areaTagId, boolean freeze) {
        dao.updateLoanTagsStatus(areaTagId, freeze);
    }
}
