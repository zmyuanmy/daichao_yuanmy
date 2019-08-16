/**
 * 
 */
package com.jbb.mgt.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.UserComplaintDao;
import com.jbb.mgt.core.domain.UserComplaint;
import com.jbb.mgt.core.service.UserComplaintService;

/**
 * @author zcp
 *
 * 2019年1月24日 下午7:09:10
 */
@Service("UserComplaintService")
public class UserComplaintServiceImpl implements UserComplaintService {

	@Autowired
	private UserComplaintDao dao;
	
	@Override
	public void insertUserComplaint(UserComplaint userComplain) {
		
		dao.insertUserComplaint(userComplain);
	}

}
