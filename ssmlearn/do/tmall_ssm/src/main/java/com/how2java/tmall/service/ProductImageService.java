package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.ProductImage;

public interface ProductImageService {
	String type_single = "type_single";
	String type_detail = "type_detail";
	
	public List<ProductImage> list(int pid,String type);
	public void delete(int id);
	public void add(ProductImage productImage);
	public ProductImage get(int id);
}
