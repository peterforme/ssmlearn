package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Property;

public interface PropertyService {

	public List<Property> list(int cid);
	public void add(Property property);
	public void delete(int id);
	public Property get(int id);
	public void update(Property property);
}
