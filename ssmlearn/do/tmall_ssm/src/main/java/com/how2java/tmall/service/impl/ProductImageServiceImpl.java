package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.ProductImageMapper;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.ProductImageExample;
import com.how2java.tmall.service.ProductImageService;

@Service
public class ProductImageServiceImpl implements ProductImageService{

	@Autowired
	ProductImageMapper mapper;
	
	@Override
	public List<ProductImage> list(int pid, String type) {
		// TODO Auto-generated method stub
		ProductImageExample example = new ProductImageExample();
		example.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
		return mapper.selectByExample(example);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void add(ProductImage productImage) {
		// TODO Auto-generated method stub
		mapper.insert(productImage);
	}

	@Override
	public ProductImage get(int id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

}
