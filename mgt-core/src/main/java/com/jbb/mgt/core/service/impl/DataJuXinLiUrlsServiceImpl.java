package com.jbb.mgt.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.DataJuXinLiUrlsDao;
import com.jbb.mgt.core.domain.DataJuXinLiUrls;
import com.jbb.mgt.core.domain.DataYxUrls;
import com.jbb.mgt.core.service.DataJuXinLiUrlsService;

@Service("DataJuXinLiUrlsService")
public class DataJuXinLiUrlsServiceImpl implements DataJuXinLiUrlsService {
    @Autowired
    private DataJuXinLiUrlsDao dataJuXinLiUrlsDao;

    @Override
    public DataJuXinLiUrls selectDataYxUrlsByUserIdAndReportType(int userId, String reportType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void insertDataYxUrls(DataYxUrls dataYxUrls) {
        // TODO Auto-generated method stub

    }

    @Override
    public void insertDataJuXinLiUrls(DataJuXinLiUrls dataJuXinLiUrls) {
        dataJuXinLiUrlsDao.insertDataJuXinLiUrls(dataJuXinLiUrls);
    }

    @Override
    public void updateDataJuXinLiUrls(DataJuXinLiUrls dataJuXinLiUrls) {
        dataJuXinLiUrlsDao.updateDataJuXinLiUrls(dataJuXinLiUrls);
    }

}
