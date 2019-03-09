package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.PropertyValue;

public interface PropertyValueService {
	public List<PropertyValue> list(int pid);
	public void add(PropertyValue propertyValue);
	public void update(PropertyValue propertyValue);
	public PropertyValue get(int pid,int ptid);
}
