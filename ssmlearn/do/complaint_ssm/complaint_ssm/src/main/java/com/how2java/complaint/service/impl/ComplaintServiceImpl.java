package com.how2java.complaint.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.complaint.mapper.ComplaintMapper;
import com.how2java.complaint.pojo.Complaint;
import com.how2java.complaint.pojo.ComplaintExample;
import com.how2java.complaint.pojo.ComplaintImage;
import com.how2java.complaint.service.ComplaintImageService;
import com.how2java.complaint.service.ComplaintService;

@Service
public class ComplaintServiceImpl implements ComplaintService {

	@Autowired
	ComplaintMapper mapper;
	@Autowired
	ComplaintImageService complaintImageService;

	@Override
	public void add(Complaint complaint) {
		// TODO Auto-generated method stub
		mapper.insertSelective(complaint);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public Complaint get(int id) {
		// TODO Auto-generated method stub
		Complaint complaint = mapper.selectByPrimaryKey(id);
		List<ComplaintImage> complaintImageList = complaintImageService
				.list(complaint.getId());
		complaint.setComplaintImageList(complaintImageList);
		if (complaintImageList != null && !complaintImageList.isEmpty()) {
			ComplaintImage firstComplaintImage = complaintImageList.get(0);
			complaint.setFirstComplaintImage(firstComplaintImage);
		}
		return complaint;
	}

	@Override
	public void update(Complaint complaint) {
		// TODO Auto-generated method stub
		mapper.updateByPrimaryKeyWithBLOBs(complaint);
	}

	@Override
	public List<Complaint> list() {
		// TODO Auto-generated method stub
		ComplaintExample example = new ComplaintExample();
		example.setOrderByClause("id desc");
		// 如果有text类型字段，那么要用WithBLOBs方法，否则text类型字段查不出来
		List<Complaint> complaintList = mapper
				.selectByExampleWithBLOBs(example);
		for (Complaint complaint : complaintList) {
			List<ComplaintImage> complaintImageList = complaintImageService
					.list(complaint.getId());
			complaint.setComplaintImageList(complaintImageList);
			if (complaintImageList != null && !complaintImageList.isEmpty()) {
				ComplaintImage firstComplaintImage = complaintImageList.get(0);
				complaint.setFirstComplaintImage(firstComplaintImage);
			}
		}
		return complaintList;
	}

	@Override
	public List<Complaint> listByUser(int uid) {
		// TODO Auto-generated method stub
		ComplaintExample example = new ComplaintExample();
		example.createCriteria().andUidEqualTo(uid);
		example.setOrderByClause("id desc");
		// 如果有text类型字段，那么要用WithBLOBs方法，否则text类型字段查不出来
		List<Complaint> complaintList = mapper
				.selectByExampleWithBLOBs(example);
		for (Complaint complaint : complaintList) {
			List<ComplaintImage> complaintImageList = complaintImageService
					.list(complaint.getId());
			complaint.setComplaintImageList(complaintImageList);
			if (complaintImageList != null && !complaintImageList.isEmpty()) {
				ComplaintImage firstComplaintImage = complaintImageList.get(0);
				complaint.setFirstComplaintImage(firstComplaintImage);
			}
		}
		return complaintList;
	}

}
