package com.how2java.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class PageController {

	@RequestMapping("admin_login")
	public String login(Model model) {
		return "admin/login";
	}

}
