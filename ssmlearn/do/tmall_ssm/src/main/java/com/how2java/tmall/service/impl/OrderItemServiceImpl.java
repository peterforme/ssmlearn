package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.OrderItemMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.OrderItemExample;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.ProductService;

@Service
public class OrderItemServiceImpl implements OrderItemService{

	@Autowired
	OrderItemMapper mapper;
	@Autowired
	ProductService productService;
	
	@Override
	public int add(OrderItem c) {
		// TODO Auto-generated method stub
		return mapper.insert(c);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(OrderItem c) {
		// TODO Auto-generated method stub
		mapper.updateByPrimaryKey(c);
	}

	@Override
	public OrderItem get(int id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<OrderItem> getItemListNotInOrder(int uid,int pid) {
		// TODO Auto-generated method stub
		OrderItemExample example = new OrderItemExample();
		example.setOrderByClause("id desc");
		example.createCriteria().andUidEqualTo(uid).andPidEqualTo(pid).andOidIsNull();
		List orderItemList = mapper.selectByExample(example);
		return orderItemList;
	}
	
	@Override
	public List list() {
		// TODO Auto-generated method stub
		OrderItemExample example = new OrderItemExample();
		example.setOrderByClause("id desc");
		List orderItemList = mapper.selectByExample(example);
		return orderItemList;
	}

	@Override
	public void fill(List<Order> os) {
		// TODO Auto-generated method stub
		for (Order order : os) {
			fill(order);
		}
	}

	@Override
	public void fill(Order o) {
		// TODO Auto-generated method stub
		OrderItemExample example = new OrderItemExample();
		example.createCriteria().andOidEqualTo(o.getId());
		List<OrderItem> orderItemList = mapper.selectByExample(example);
		float total = 0;
        int totalNumber = 0;
		for (OrderItem orderItem : orderItemList) {
			Product product = productService.get(orderItem.getPid());
			orderItem.setProduct(product);
			total+=orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
            totalNumber+=orderItem.getNumber();
		}
		o.setTotal(total);
        o.setTotalNumber(totalNumber);
		o.setOrderItems(orderItemList);
	}

	@Override
	public int getSaleCount(int pid) {
		// TODO Auto-generated method stub
		OrderItemExample example = new OrderItemExample();
		example.createCriteria().andPidEqualTo(pid);
		List<OrderItem> orderItemList = mapper.selectByExample(example);
		int count = 0;
		for (OrderItem orderItem : orderItemList) {
			count += orderItem.getNumber();
		}
		
		return count;
	}

}
