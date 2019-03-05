package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.BackUserMapper;
import com.how2java.tmall.pojo.BackUser;
import com.how2java.tmall.pojo.BackUserExample;
import com.how2java.tmall.service.BackUserService;

@Service
public class BackUserServiceImpl implements BackUserService{

	@Autowired
	BackUserMapper backUserMapper;
	
	@Override
	public List<BackUser> list() {
		// TODO Auto-generated method stub
		BackUserExample example = new BackUserExample();
		example.setOrderByClause("id desc");
		return  backUserMapper.selectByExample(example);
	}

}
