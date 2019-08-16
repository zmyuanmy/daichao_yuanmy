package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.FinFileDao;
import com.jbb.mgt.core.dao.mapper.FinFileMapper;
import com.jbb.mgt.core.domain.FinFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository("FinFileDao")
public class FinFileDaoImpl implements FinFileDao {

    @Autowired
    private FinFileMapper finFileMapper;

    @Override
    public boolean deleteFinFileById(int fileId) {
        return finFileMapper.deleteFinFileById(fileId) > 0;
    }

    @Override
    public boolean insertFinFile(FinFile finFile) {
        return finFileMapper.insertFinFile(finFile) > 0;
    }

    @Override
    public List<FinFile> selectFinFile(int orgId, Date fileDate, int fileType) {
        return finFileMapper.selectFinFile(orgId, fileDate, fileType);
    }
}
