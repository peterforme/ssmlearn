package com.how2java.tmall.service.impl;
 
import java.util.List;
 





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 



import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.how2java.tmall.mapper.OrderMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderExample;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.service.UserService;
 
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
 
    @Autowired
    UserService userService;
 
    @Autowired
    OrderItemService orderItemService;
    
    @Override
    public void add(Order c) {
        orderMapper.insert(c);
    }
 
    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }
 
    @Override
    public void update(Order c) {
        orderMapper.updateByPrimaryKeySelective(c);
    }
 
    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }
 
    public List<Order> list(){
        OrderExample example =new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> result =orderMapper.selectByExample(example);
        setUser(result);
        orderItemService.fill(result);
        return result;
    }
    public void setUser(List<Order> os){
        for (Order o : os)
            setUser(o);
    }
    public void setUser(Order o){
        int uid = o.getUid();
        User u = userService.get(uid);
        o.setUser(u);
    }

	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
	public float add(Order c, List<OrderItem> ois) {
		// 该方法使用了事务,多表或者批量操作要用事务
		float total = 0;
		orderMapper.insert(c);
		int orderId = c.getId();
		for (OrderItem orderItem : ois) {
			orderItem.setOid(orderId);
			orderItemService.update(orderItem);
			total+=orderItem.getProduct().getPromotePrice()*orderItem.getNumber();
		}
		
		return total;
	}
 
}