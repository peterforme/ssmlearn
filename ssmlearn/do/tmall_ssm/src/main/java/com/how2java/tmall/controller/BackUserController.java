package com.how2java.tmall.controller;
import com.how2java.tmall.pojo.BackUser;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.BackUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 


import java.util.List;


@Controller
@RequestMapping("")
public class BackUserController {

	@Autowired
	BackUserService backUserService;
	
	@RequestMapping("admin_backuser_list")
	public String list(Model model){
        List<BackUser> backUserList= backUserService.list();
        model.addAttribute("backUserList", backUserList);
        return "admin/listBackUser";
    }
	
}
