package com.how2java.complaint.service;

import java.util.List;

import com.how2java.complaint.pojo.BackUser;
import com.how2java.complaint.pojo.User;

public interface UserService {
	void add(User c);

	void delete(int id);

	void update(User c);

	User get(int id);

	List list();

	boolean isExist(String name);

	User get(String name, String password);
	
	User get(String userName);
}