package com.how2java.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.util.Page;

@Controller
@RequestMapping("")
public class ProductController {

	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping("admin_product_add")
    public String add(Model model, Product p) {
		productService.add(p);
        return "redirect:admin_product_list?cid="+p.getCid();
    }
	
	@RequestMapping("admin_product_delete")
    public String delete(Model model, int id) {
		Product product = productService.get(id);
		productService.delete(id);
        return "redirect:admin_product_list?cid="+product.getCid();
    }
	
	@RequestMapping("admin_product_edit")
    public String edit(Model model, int id) {
		Product product = productService.get(id);
		Category category = categoryService.get(product.getCid());
		product.setCategory(category);
		model.addAttribute("p", product);
		return "admin/editProduct";
	}
	
	@RequestMapping("admin_product_update")
    public String update(Model model, Product product) {
		productService.update(product);
		return "redirect:admin_product_list?cid="+product.getCid();
	}
	
	@RequestMapping("admin_product_list")
    public String list(int cid, Model model,  Page page) {
		Category category = categoryService.get(cid);
		
		PageHelper.offsetPage(page.getStart(),page.getCount());
		List<Product> productList = productService.list(cid);
		
		int total = (int) new PageInfo<>(productList).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+cid);
 
        model.addAttribute("ps", productList);
        model.addAttribute("c", category);
        model.addAttribute("page", page);
 
        return "admin/listProduct";
	}
	
}
