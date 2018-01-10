package com.syfri.userserver.mapper;

import com.github.pagehelper.Page;
import com.syfri.userserver.domain.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserEntityMapper {

	//查询所有用户
	List<UserEntity> getAllUsers();

	//增加用户
	@Insert("INSERT INTO S_USER(USERID, USERNAME, PASSWORD) VALUES ('11111', #{arg0}, #{arg1})")
	int insertUser(String arg0, String arg1);

	@Select("SELECT USERID AS ID, USERNAME, PASSWORD FROM S_USER WHERE USERID = #{arg0}")
	UserEntity findOne(String arg0);

	@Insert("INSERT INTO S_USER(USERID, SEX) VALUES (#{arg0}, #{arg1})")
	int insertSex(String arg0, String arg1);

	Page<UserEntity> findByPage();
}
