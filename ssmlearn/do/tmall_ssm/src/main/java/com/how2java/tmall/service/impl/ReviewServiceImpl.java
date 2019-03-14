package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.ReviewMapper;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.ReviewExample;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.ReviewSevice;
import com.how2java.tmall.service.UserService;

@Service
public class ReviewServiceImpl implements ReviewSevice{

	@Autowired
	ReviewMapper mapper;
	
	@Autowired
	UserService userService;
	
	@Override
	public void add(Review c) {
		// TODO Auto-generated method stub
		mapper.insertSelective(c);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Review c) {
		// TODO Auto-generated method stub
		mapper.updateByPrimaryKey(c);
	}

	@Override
	public Review get(int id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List list(int pid) {
		// TODO Auto-generated method stub
		ReviewExample example = new ReviewExample();
		example.createCriteria().andPidEqualTo(pid);
		List<Review> reviewList = mapper.selectByExample(example);
		for (Review review : reviewList) {
			setUser(review);
		}
		return reviewList;
	}

	private void setUser(Review review) {
		// TODO Auto-generated method stub
		User user = userService.get(review.getUid());
		review.setUser(user);
	}

	@Override
	public int getCount(int pid) {
		// TODO Auto-generated method stub
		return list(pid).size();
	}

}
