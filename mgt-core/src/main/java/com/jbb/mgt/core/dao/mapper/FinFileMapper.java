package com.jbb.mgt.core.dao.mapper;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.FinFile;
import org.apache.ibatis.annotations.Param;



public interface FinFileMapper {
    
    FinFile selectRoleById(@Param("fileId") int fileId);


    /**
     * 删除file 修改delete状态为true
     * @param fileId
     * @return
     */
    int deleteFinFileById(@Param("fileId") int fileId);


    /**
     * 插入文件
     * @param finFile
     * @return
     */
    int insertFinFile(FinFile finFile);

    List<FinFile> selectFinFile(@Param("orgId") int orgId, @Param("fileDate") Date fileDate, @Param("fileType") int fileType);

}
