package com.how2java.tmall.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.BackUser;
import com.how2java.tmall.service.BackUserService;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.SaltUtil;

@Controller
@RequestMapping("")
public class BackUserController {

	@Autowired
	BackUserService backUserService;

	@RequestMapping("admin_backuser_list")
	public String list(Model model, Page page) {
		PageHelper.offsetPage(page.getStart(), page.getCount());
		List<BackUser> backUserList = backUserService.list();
		int total = (int) new PageInfo<>(backUserList).getTotal();
		page.setTotal(total);
		model.addAttribute("backUserList", backUserList);
		model.addAttribute("page", page);
		return "admin/listBackUser";
	}

	@RequestMapping("admin_backuser_add")
	public String add(Model model, BackUser backUser) {
		String name = backUser.getName();
		BackUser qureyBackUser = backUserService.get(name);
		if (qureyBackUser != null) {
			model.addAttribute("msg", "账号已经存在");
			return "forward:/admin_backuser_list";
		}

		String origin = backUser.getPassword();
		String hashToDb = SaltUtil.MD5WithSalt(origin);
		backUser.setPassword(hashToDb);

		backUserService.add(backUser);
		return "redirect:/admin_backuser_list";
	}

	@RequestMapping("admin_backuser_delete")
	public String delete(int id) {
		backUserService.delete(id);
		return "redirect:/admin_backuser_list";
	}

	@RequestMapping("admin_backuser_login")
	public String login(Model model, BackUser backUser, HttpSession session) {
		String userName = backUser.getName();
		String origin = backUser.getPassword();

		BackUser qureyBackUser = backUserService.get(userName);
		if (qureyBackUser != null) {
			String hashToDb = qureyBackUser.getPassword();
			if (SaltUtil.varify(origin, hashToDb)) {
				// 表示密码正确
				session.setAttribute("user", qureyBackUser);
				return "redirect:/admin_backuser_list";
			}
		}
		model.addAttribute("msg", "账号密码错误");
		return "admin/login";
	}

}
