package com.how2java.complaint.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.complaint.pojo.BackUser;
import com.how2java.complaint.service.BackUserService;
import com.how2java.complaint.util.Page;
import com.how2java.complaint.util.SaltUtil;

@Controller
@RequestMapping("")
public class BackUserController {

	@Autowired
	BackUserService backUserService;

	//目前这种分成前台和后台用户两个表的方式并不好，应该放一个表然后设置权限
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

	@RequestMapping("admin_backuser_logout")
	public String logout(Model model, HttpSession session) {
		session.removeAttribute("backuser");
		return "redirect:/admin_login";
	}

	@RequestMapping("admin_backuser_login")
	public String login(Model model, BackUser backUser, HttpSession session,
			String needRem, HttpServletResponse response) {
		String userName = backUser.getName();
		String origin = backUser.getPassword();

		// needRem表示是否记住密码,值会为on或者null
		// 安全起见的话，应该以序列化或者加密的方式来保存到cookie中,更为安全的方法是使用token加https
		if (needRem != null) {
			Cookie cookie = new Cookie("backuser", userName + "," + origin);
			cookie.setMaxAge(30 * 24 * 60 * 60);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("backuser", null);
			cookie.setMaxAge(0);
			// 进行覆盖
			response.addCookie(cookie);
		}

		BackUser qureyBackUser = backUserService.get(userName);
		if (qureyBackUser != null) {
			String hashToDb = qureyBackUser.getPassword();
			if (SaltUtil.varify(origin, hashToDb)) {
				// 表示密码正确
				session.setAttribute("backuser", qureyBackUser);
				return "redirect:/admin_backuser_list";
			}
		}
		model.addAttribute("msg", "账号密码错误");
		return "admin/login";
	}

}
