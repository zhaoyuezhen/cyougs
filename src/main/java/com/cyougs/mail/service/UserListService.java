package com.cyougs.mail.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyougs.mail.dao.Users;
import com.cyougs.mail.mapper.UsersMapper;

@Service
public class UserListService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UsersMapper usersMapper;
	public List<Users> selectAll(){
		logger.info("-- UserListService.selectAll() --");
		return usersMapper.selectAll();
	}  
}
