package com.how2java.tmall.controller;
 
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.pojo.UserExample;
import com.how2java.tmall.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 



import org.springframework.web.util.HtmlUtils;

import java.util.List;

import javax.servlet.http.HttpSession;
 
@Controller
@RequestMapping("")
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    PropertyService propertyService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewSevice reviewSevice;
 
    @RequestMapping("forehome")
    public String home(Model model) {
        List<Category> cs= categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
         model.addAttribute("cs", cs);
        return "fore/home";
    }
 
    @RequestMapping("foreregister")
    public String foreregister(Model model, User user){
    	String name =  user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
    	if( userService.isExist(user.getName()) ){
    		model.addAttribute("msg", "该用户名已经存在");
    		return "fore/register";
    	}
    	userService.add(user);
    	return "fore/registerSuccess";
    }
    
    @RequestMapping("forelogin")
    public String forelogin(Model model, User user,HttpSession session){
    	User temp = userService.get(HtmlUtils.htmlEscape(user.getName()), user.getPassword());
    	if(temp == null){
    		model.addAttribute("msg", "用户名和密码不对");
    		return "fore/login";
    	}
    	session.setAttribute("user", temp);
    	return "redirect:/forehome";
    }
    
    @RequestMapping("forelogout")
    public String forelogout(Model model,HttpSession session){
    	session.removeAttribute("user");
        return "redirect:forehome";
    }
    
    @RequestMapping("foreproduct")
    public String foreproduct(Model model,int pid){
    	
    	Product p = productService.get(pid);
    	productService.setSaleAndReviewNumber(p);
    	
    	Category category = p.getCategory();
    	List<PropertyValue> pvs = propertyValueService.list(pid);
    	List<Review> reviews =  reviewSevice.list(pid);
    	
    	List<ProductImage> productSingleImages = productImageService.list(pid, ProductImageService.type_single);
    	List<ProductImage> productDetailImages = productImageService.list(pid, ProductImageService.type_detail);
    	p.setProductSingleImages(productSingleImages);
    	p.setProductDetailImages(productDetailImages);
    	
    	model.addAttribute("p", p);
    	model.addAttribute("pvs", pvs);
    	model.addAttribute("reviews", reviews);
    	return "fore/product";
    }
    
}