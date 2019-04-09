package com.how2java.complaint.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.complaint.mapper.UserMapper;
import com.how2java.complaint.pojo.BackUser;
import com.how2java.complaint.pojo.BackUserExample;
import com.how2java.complaint.pojo.User;
import com.how2java.complaint.pojo.UserExample;
import com.how2java.complaint.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserMapper userMapper;

	@Override
	public void add(User u) {
		userMapper.insert(u);
	}

	@Override
	public void delete(int id) {
		userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(User u) {
		userMapper.updateByPrimaryKeySelective(u);
	}

	@Override
	public User get(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public List<User> list() {
		UserExample example = new UserExample();
		example.setOrderByClause("id desc");
		return userMapper.selectByExample(example);

	}

	@Override
	public boolean isExist(String name) {
		UserExample example = new UserExample();
		example.createCriteria().andNameEqualTo(name);
		List<User> result = userMapper.selectByExample(example);
		if (!result.isEmpty())
			return true;
		return false;

	}

	@Override
	public User get(String name, String password) {
		UserExample example = new UserExample();
		example.createCriteria().andNameEqualTo(name)
				.andPasswordEqualTo(password);
		List<User> result = userMapper.selectByExample(example);
		if (result.isEmpty())
			return null;
		return result.get(0);
	}

	@Override
	public User get(String userName) {
		// TODO Auto-generated method stub
		UserExample example = new UserExample();
		example.createCriteria().andNameEqualTo(userName);
		List<User> userList = userMapper.selectByExample(example);
		if (!userList.isEmpty()) {
			return userList.get(0);
		}
		return null;
	}
}