package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.PropertyValueMapper;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.PropertyValueExample;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.service.PropertyValueService;

@Service
public class PropertyValueServiceImpl implements PropertyValueService{

	@Autowired
	PropertyValueMapper mapper;
	
	@Autowired
	PropertyService propertyService;
	
	@Autowired
	ProductService productService;
	
	public void init(int pid){
		int cid = productService.get(pid).getCid();
		List<Property> propertyList = propertyService.list(cid);
		for (Property property : propertyList) {
			int ptid = property.getId();
			PropertyValue temp = get(pid, ptid);
			if(temp == null){
				PropertyValue propertyValue = new PropertyValue();
				propertyValue.setPid(pid);
				propertyValue.setPtid(ptid);
				add(propertyValue);
			}
		}
	}
	
	@Override
	public List<PropertyValue> list(int pid) {
		// TODO Auto-generated method stub
		init(pid);
		PropertyValueExample example = new PropertyValueExample();
		example.createCriteria().andPidEqualTo(pid);
		List<PropertyValue> result =  mapper.selectByExample(example);
		for (PropertyValue pv : result) {
            Property property = propertyService.get(pv.getPtid());
            pv.setProperty(property);
        }
        return result;
		
	}

	@Override
	public void add(PropertyValue propertyValue) {
		// TODO Auto-generated method stub
		mapper.insert(propertyValue);
	}

	@Override
	public void update(PropertyValue propertyValue) {
		// TODO Auto-generated method stub
		mapper.updateByPrimaryKeySelective(propertyValue);
	}

	@Override
	public PropertyValue get(int pid, int ptid) {
		// TODO Auto-generated method stub
		PropertyValueExample example = new PropertyValueExample();
		example.createCriteria().andPidEqualTo(pid).andPtidEqualTo(ptid);
		List<PropertyValue> pvs = mapper.selectByExample(example);
		if (pvs.isEmpty())
            return null;
        return pvs.get(0);
	}
	
}
