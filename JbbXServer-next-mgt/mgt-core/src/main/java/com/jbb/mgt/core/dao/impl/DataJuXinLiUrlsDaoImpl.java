package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.DataJuXinLiUrlsDao;
import com.jbb.mgt.core.dao.mapper.DataJuXinLiUrlsMapper;
import com.jbb.mgt.core.domain.DataJuXinLiUrls;

@Repository("DataJuXinLiUrlsDao")
public class DataJuXinLiUrlsDaoImpl implements DataJuXinLiUrlsDao {
    @Autowired
    private DataJuXinLiUrlsMapper dataJuXinLiUrlsMapper;

    @Override
    public void insertDataJuXinLiUrls(DataJuXinLiUrls dataJuXinLiUrls) {
        dataJuXinLiUrlsMapper.insertDataJuXinLiUrls(dataJuXinLiUrls);
    }

    @Override
    public void updateDataJuXinLiUrls(DataJuXinLiUrls dataJuXinLiUrls) {
        dataJuXinLiUrlsMapper.updateDataJuXinLiUrls(dataJuXinLiUrls);

    }

}
