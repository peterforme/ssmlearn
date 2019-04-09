package com.how2java.complaint.service;

import java.util.List;

import com.how2java.complaint.pojo.ComplaintImage;

public interface ComplaintImageService {

	public static int TYPE_DETAIL = 1;

	public void add(ComplaintImage complaintImage);

	public List<ComplaintImage> list(int cid);

	public void delete(int id);

	public ComplaintImage get(int id);
	// 直接删除，不修改
}
