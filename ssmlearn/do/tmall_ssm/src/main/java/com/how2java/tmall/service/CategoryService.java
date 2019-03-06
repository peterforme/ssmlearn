package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Category;

public interface CategoryService {

	public List<Category> list();

	public void add(Category category);

	public void delete(int id);

	public Category get(int id);

	public void update(Category category);
}
