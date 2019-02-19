package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Property;

public interface ProductService {
	void add(Product c);
    void delete(int id);
    void update(Product c);
    Product get(int id);
    List list(int cid);
    void setFirstProductImage(Product p);
    
}
