package com.how2java.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.util.Page;

@Controller
@RequestMapping("")
public class PropertyController {

	@Autowired
	PropertyService propertyService;
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping("admin_property_list")
	public String list(Model model, Page page,int cid) {
		PageHelper.offsetPage(page.getStart(), page.getCount());
		List<Property> propertyList = propertyService.list(cid);
		
		Category category = categoryService.get(cid);
		
		int total = (int) new PageInfo<>(propertyList).getTotal();
		page.setTotal(total);
		page.setParam("&cid="+cid);
		model.addAttribute("page", page);
		model.addAttribute("ps", propertyList);
		model.addAttribute("c", category);
		return "admin/listProperty";
	}
	
	@RequestMapping("admin_property_add")
	public String add(Model model,Property property){
		propertyService.add(property);
		return "redirect:/admin_property_list?cid="+property.getCid();
	}
	
	@RequestMapping("admin_property_delete")
	public String delete(Model model,int id){
		Property temp = propertyService.get(id);
		int cid = temp.getCid();
		propertyService.delete(id);
		return "redirect:/admin_property_list?cid="+cid;
	}
	
	@RequestMapping("admin_property_edit")
	public String edit(Model model,int id){
		Property property = propertyService.get(id);
		int cid = property.getCid();
		Category category = categoryService.get(cid);
		property.setCategory(category);
		model.addAttribute("p",property);
		return "admin/editProperty";
	}
	
	@RequestMapping("admin_property_update")
	public String update(Model model,Property property){
		propertyService.update(property);
		return "redirect:/admin_property_list?cid="+property.getCid();
	}
	
	
}
