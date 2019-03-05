package com.how2java.tmall.controller;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.BackUser;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.BackUserService;
import com.how2java.tmall.util.Page;

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
	public String list(Model model,Page page){
		PageHelper.offsetPage(page.getStart(),page.getCount());
        List<BackUser> backUserList= backUserService.list();
        int total = (int) new PageInfo<>(backUserList).getTotal();
        page.setTotal(total);
        model.addAttribute("backUserList", backUserList);
        model.addAttribute("page", page);
        return "admin/listBackUser";
    }
	
}
