package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.XjlRenewalLog;

public interface XjlRenewalLogMapper {

	 List<XjlRenewalLog> queryRenewalLogList(@Param("applyId")String applyId,@Param("renewalAmount")String renewalAmount,
			 @Param("userId")Integer userId, @Param("creationDate") Timestamp  creationDate);
	 
	 XjlRenewalLog selectXjlRenewalLogByLogId(@Param("logId")int logId);
		
	 int updateXjlRenewalLog(@Param("xjlRenewalLog")XjlRenewalLog xjlRenewalLog);
		
	 XjlRenewalLog insertXjlRenewalLog(@Param("xjlRenewalLog")XjlRenewalLog xjlRenewalLog);
		
	 int deleteXjlRenewalLog(@Param("logId")int logId);
}
