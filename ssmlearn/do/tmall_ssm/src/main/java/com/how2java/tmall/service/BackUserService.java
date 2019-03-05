package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.BackUser;

public interface BackUserService {

	public List<BackUser> list();

	public void add(BackUser backUser);

	public BackUser get(String userName);
}
