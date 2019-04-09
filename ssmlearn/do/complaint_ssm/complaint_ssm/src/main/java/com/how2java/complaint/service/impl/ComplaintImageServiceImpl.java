package com.how2java.complaint.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.complaint.mapper.ComplaintImageMapper;
import com.how2java.complaint.pojo.ComplaintImage;
import com.how2java.complaint.pojo.ComplaintImageExample;
import com.how2java.complaint.service.ComplaintImageService;

@Service
public class ComplaintImageServiceImpl implements ComplaintImageService {

	@Autowired
	ComplaintImageMapper complaintImageMapper;

	@Override
	public void add(ComplaintImage complaintImage) {
		// TODO Auto-generated method stub
		complaintImageMapper.insertSelective(complaintImage);
	}

	@Override
	public List<ComplaintImage> list(int cid) {
		// TODO Auto-generated method stub
		ComplaintImageExample example = new ComplaintImageExample();
		example.createCriteria().andCidEqualTo(cid);
		return complaintImageMapper.selectByExample(example);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		complaintImageMapper.deleteByPrimaryKey(id);
	}

	@Override
	public ComplaintImage get(int id) {
		// TODO Auto-generated method stub
		return complaintImageMapper.selectByPrimaryKey(id);
	}

}
