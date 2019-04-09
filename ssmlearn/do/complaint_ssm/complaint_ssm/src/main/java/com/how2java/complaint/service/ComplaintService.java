package com.how2java.complaint.service;

import java.util.List;

import com.how2java.complaint.pojo.Complaint;

public interface ComplaintService {

	public void add(Complaint complaint);

	public List<Complaint> list();

	public List<Complaint> listByUser(int uid);

	public void delete(int id);

	public Complaint get(int id);

	public void update(Complaint complaint);

}
