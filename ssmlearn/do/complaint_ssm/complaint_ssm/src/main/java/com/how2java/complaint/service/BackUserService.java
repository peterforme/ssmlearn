package com.how2java.complaint.service;

import java.util.List;

import com.how2java.complaint.pojo.BackUser;

public interface BackUserService {

	public List<BackUser> list();

	public void add(BackUser backUser);

	public BackUser get(String userName);

	public void delete(int id);
}
