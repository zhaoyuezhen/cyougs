package com.cyougs.mail.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cyougs.mail.Form.UserListForm;
import com.cyougs.mail.common.XMLUtil;
import com.cyougs.mail.dao.Users;
import com.cyougs.mail.service.UserListService;

@Controller
public class UserListController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UserListService userListService;
	@Autowired
	XMLUtil xmlUtil; 
	@ModelAttribute("userListForm")
	public UserListForm userListForm() {
		logger.info("-- UserListController.userListForm --");
		return new UserListForm();
	}
	@RequestMapping("/user_list")
	public ModelAndView initUserListInit(ModelAndView modelAndView) {
		logger.info("-- UserListController.userList --");
		List<Users> usresList = userListService.selectAll();
		modelAndView.addObject("usresList",usresList);
		return modelAndView;
	}
	public ModelAndView userList(ModelAndView model) {
		
		return model;
	}
}
