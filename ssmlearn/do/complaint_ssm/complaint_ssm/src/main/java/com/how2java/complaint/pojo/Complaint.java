package com.how2java.complaint.pojo;

import java.util.List;

public class Complaint {
	private Integer id;

	private Integer uid;

	private String title;

	private String content;

	// 非数据库字段
	private List<ComplaintImage> complaintImageList;
	private ComplaintImage firstComplaintImage;

	public ComplaintImage getFirstComplaintImage() {
		return firstComplaintImage;
	}

	public void setFirstComplaintImage(ComplaintImage firstComplaintImage) {
		this.firstComplaintImage = firstComplaintImage;
	}

	public List<ComplaintImage> getComplaintImageList() {
		return complaintImageList;
	}

	public void setComplaintImageList(List<ComplaintImage> complaintImageList) {
		this.complaintImageList = complaintImageList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}
}