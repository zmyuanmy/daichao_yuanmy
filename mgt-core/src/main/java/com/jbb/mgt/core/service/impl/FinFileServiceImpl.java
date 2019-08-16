package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.FinFileDao;
import com.jbb.mgt.core.domain.FinFile;
import com.jbb.mgt.core.service.FinFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("FinFileService")
public class FinFileServiceImpl implements FinFileService {

    @Autowired
    private FinFileDao finFileDao;

    @Override
    public boolean deleteFinFileById(int fileId) {
        return finFileDao.deleteFinFileById(fileId);
    }

    @Override
    public boolean insertFinFile(FinFile finFile) {
        return finFileDao.insertFinFile(finFile);
    }
}
