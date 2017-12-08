package com.cyougs.mail.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cyougs.mail.dao.Users;

@Mapper
public interface UsersMapper {
    int deleteByPrimaryKey(String username);

    int insert(Users record);

    Users selectByPrimaryKey(String username);

    List<Users> selectAll();

    int updateByPrimaryKey(Users record);
}