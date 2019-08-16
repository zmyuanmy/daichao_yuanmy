package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.XjlRenewalLog;

public interface XjlRenewalLogDao {

	List<XjlRenewalLog> queryRenewalLogList(String applyId,String renewalAmount, Integer userId, Timestamp creationDate);
	
    XjlRenewalLog selectXjlRenewalLogByLogId(int logId);
	
	int updateXjlRenewalLog(XjlRenewalLog xjlRenewalLog);
	
	XjlRenewalLog insertXjlRenewalLog(XjlRenewalLog xjlRenewalLog);
	
	int deleteXjlRenewalLog(int logId);
}
