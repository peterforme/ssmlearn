package com.how2java.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.service.PropertyValueService;
import com.how2java.tmall.util.Page;

@Controller
@RequestMapping("")
public class PropertyValueController {

	@Autowired
	PropertyValueService propertyValueService;
	@Autowired
	PropertyService propertyService;
	@Autowired
	ProductService productService;
	
	@RequestMapping("admin_propertyValue_edit")
	public String list(Model model, int pid) {
		List<PropertyValue> propertyValueList =  propertyValueService.list(pid);
		Product product = productService.get(pid);
		
		for (PropertyValue propertyValue : propertyValueList) {
			int propertyId = propertyValue.getPtid();
			Property property = propertyService.get(propertyId);
			propertyValue.setProperty(property);
			System.out.println(property.getName());
		}
		
		model.addAttribute("pvs", propertyValueList);
		model.addAttribute("p", product);
		return "admin/editPropertyValue";
	}
	
	@RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue pv) {
        propertyValueService.update(pv);
        return "success";
    }
	
}
