package com.how2java.tmall.service.impl;
 
import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductExample;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 


import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;
 
    @Override
    public void add(Product p) {
        productMapper.insert(p);
    }
 
    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }
 
    @Override
    public void update(Product p) {
        productMapper.updateByPrimaryKeySelective(p);
    }
 
    @Override
    public Product get(int id) {
        Product p = productMapper.selectByPrimaryKey(id);
        setCategory(p);
        return p;
    }
 
    public void setCategory(List<Product> ps){
        for (Product p : ps)
            setCategory(p);
    }
    public void setCategory(Product p){
        int cid = p.getCid();
        Category c = categoryService.get(cid);
        p.setCategory(c);
    }
 
    public void setProductFirstImage(List<Product> products){
    	for (Product p : products)
    		setProductFirstImage(p);
    }
    
    public void setProductFirstImage(Product p){
    	int pid = p.getId();
    	List<ProductImage> productImageList = productImageService.list(pid, ProductImageService.type_single);
    	if(!productImageList.isEmpty()){
    		p.setFirstProductImage(productImageList.get(0));
    	}
    }
    
    @Override
    public List list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List result = productMapper.selectByExample(example);
        setCategory(result);
        setProductFirstImage(result);
        return result;
    }
}