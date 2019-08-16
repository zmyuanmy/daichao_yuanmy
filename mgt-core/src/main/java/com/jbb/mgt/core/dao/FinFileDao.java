package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.FinFile;

import java.sql.Date;
import java.util.List;

public interface FinFileDao {

    boolean deleteFinFileById(int fileId);

    boolean insertFinFile(FinFile finFile);

    List<FinFile> selectFinFile(int orgId, Date fileDate,int fileType);
}
