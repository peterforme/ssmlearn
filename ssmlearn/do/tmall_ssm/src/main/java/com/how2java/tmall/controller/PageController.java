package com.how2java.tmall.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class PageController {

	@RequestMapping("admin_login")
	public String login(Model model,HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		//遍历所有的cookie,然后根据cookie的key值来获取value值
		if (cookies!=null) {
		            for (Cookie cookie : cookies) {
		                if (cookie.getName().equals("backuser")) {
		                  String cookieVal = cookie.getValue();
		                  model.addAttribute("cookieVal", cookieVal);
		                }
		            }
		        }
		return "admin/login";
	}

	@RequestMapping("registerPage")
	public String registerPage(Model model){
		return "fore/register";
	}
	
	@RequestMapping("loginPage")
    public String loginPage(Model model){
    	return "fore/login";
    }	
	
	@RequestMapping("registerSuccessPage")
    public String registerSuccessPage() {
        return "fore/registerSuccess";
    }
}
