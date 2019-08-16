package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.FinFile;

public interface FinFileService {

    boolean deleteFinFileById(int fileId);

    boolean insertFinFile(FinFile finFile);
}
