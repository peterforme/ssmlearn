package com.how2java.tmall.controller;
 
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.pojo.UserExample;
import com.how2java.tmall.service.*;

import comparator.ProductAllComparator;
import comparator.ProductDateComparator;
import comparator.ProductPriceComparator;
import comparator.ProductReviewComparator;
import comparator.ProductSaleCountComparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 



import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.Collections;
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
    
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String forecheckLogin(Model model,HttpSession session){
    	if(session.getAttribute("user") != null){
    		return "success";
    	}
    	else{
    		return "fail";
    	}
    }
    
    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String foreloginAjax(Model model,String name,String password,HttpSession session){
    	User temp = userService.get(HtmlUtils.htmlEscape(name), password);
    	if(temp == null){
    		return "fail";
    	}
    	session.setAttribute("user", temp);
    	return "success";
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
    
    @RequestMapping("forecategory")
    public String forecategory(Model model, int cid,String sort){
    	Category c = categoryService.get(cid);
        productService.fill(c);
        productService.setSaleAndReviewNumber(c.getProducts());
 
        if(null!=sort){
            switch(sort){
                case "review":
                    Collections.sort(c.getProducts(),new ProductReviewComparator());
                    break;
                case "date" :
                    Collections.sort(c.getProducts(),new ProductDateComparator());
                    break;
 
                case "saleCount" :
                    Collections.sort(c.getProducts(),new ProductSaleCountComparator());
                    break;
 
                case "price":
                    Collections.sort(c.getProducts(),new ProductPriceComparator());
                    break;
 
                case "all":
                    Collections.sort(c.getProducts(),new ProductAllComparator());
                    break;
            }
        }
 
        model.addAttribute("c", c);
        return "fore/category";
    }
    
    
    @RequestMapping("foresearch")
    public String foresearch(Model model, String keyword){
    	List<Product> productList = productService.search(keyword);
    	model.addAttribute("ps", productList);
    	return "fore/searchResult";
    }
    
    @RequestMapping("forebuyone")
    public String forebuyone(Model model, HttpSession session,int num,int pid){
    	User user = (User) session.getAttribute("user");
    	int uid = user.getId();
    	
    	List<OrderItem> orderItemList = orderItemService.getItemListNotInOrder(uid, pid);
    	int oiid = -1;
    	
    	if(orderItemList.size() > 0){
    		OrderItem orderItem = orderItemList.get(0);
    		orderItem.setNumber(orderItem.getNumber() + num);
    		orderItemService.update(orderItem);
    		oiid = orderItem.getId();
    	}else{
    		OrderItem orderItem = new OrderItem();
    		orderItem.setNumber(num);
    		orderItem.setPid(pid);
    		orderItem.setUid(uid);
    		orderItemService.add(orderItem);
    		//insert后会设置主键到该bean中
    		oiid = orderItem.getId();
    	}
    	
    	return "redirect:forebuy?oiid="+oiid;
    }
    
    
}