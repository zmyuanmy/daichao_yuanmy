/**
 * 
 */
package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.UserComplaintDao;
import com.jbb.mgt.core.dao.mapper.UserComplaintMapper;
import com.jbb.mgt.core.domain.UserComplaint;

/**
 * @author zcp
 *
 * 2019年1月24日 下午7:10:44
 */
@Repository("UserComplaintDao")
public class UserComplaintDaoImpl implements UserComplaintDao {

	@Autowired
	private UserComplaintMapper mapper;
	
	@Override
	public void insertUserComplaint(UserComplaint userComplain) {
		mapper.insertUserComplaint(userComplain);
	}

}
