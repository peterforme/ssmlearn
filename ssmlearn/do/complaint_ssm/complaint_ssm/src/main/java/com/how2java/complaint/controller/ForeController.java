package com.how2java.complaint.controller;

import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.complaint.pojo.Complaint;
import com.how2java.complaint.pojo.ComplaintImage;
import com.how2java.complaint.pojo.User;
import com.how2java.complaint.service.ComplaintImageService;
import com.how2java.complaint.service.ComplaintService;
import com.how2java.complaint.service.UserService;
import com.how2java.complaint.util.ImageUtil;
import com.how2java.complaint.util.Page;
import com.how2java.complaint.util.SaltUtil;
import com.how2java.complaint.util.UploadedImageFile;

@Controller
@RequestMapping("")
public class ForeController {
	@Autowired
	UserService userService;
	@Autowired
	ComplaintService complaintService;
	@Autowired
	ComplaintImageService complaintImageService;

	@RequestMapping("forehome")
	public String home(Model model, Page page, HttpSession session) {
		User user = (User) session.getAttribute("user");

		PageHelper.offsetPage(page.getStart(), page.getCount());
		List<Complaint> complaintList = complaintService.list();
		int total = (int) new PageInfo<>(complaintList).getTotal();
		page.setTotal(total);

		// 登录了并且吐槽列表中有该账号对应的吐槽才设置为true
		boolean isShowDel = false;
		if (user != null) {
			int uid = user.getId();
			for (Complaint complaint : complaintList) {
				if (complaint.getUid() != null && uid == complaint.getUid()) {
					isShowDel = true;
					break;
				}
			}
		}

		model.addAttribute("isShowDel", isShowDel);
		model.addAttribute("page", page);
		model.addAttribute("cs", complaintList);
		return "fore/home";
	}

	@RequestMapping("foreregister")
	public String foreregister(Model model, User user) {
		String name = user.getName();
		name = HtmlUtils.htmlEscape(name);
		user.setName(name);
		if (userService.isExist(user.getName())) {
			model.addAttribute("msg", "该用户名已经存在");
			return "fore/register";
		}

		String origin = user.getPassword();
		String hashToDb = SaltUtil.MD5WithSalt(origin);
		user.setPassword(hashToDb);

		userService.add(user);
		return "fore/registerSuccess";
	}

	@RequestMapping("forelogin")
	public String forelogin(Model model, User user, HttpSession session,
			String needRem, HttpServletResponse response) {
		String userName = user.getName();
		String origin = user.getPassword();

		// needRem表示是否记住密码,值会为on或者null
		// 安全起见的话，应该以序列化或者加密的方式来保存到cookie中,更为安全的方法是使用token加https
		if (needRem != null) {
			Cookie cookie = new Cookie("user", userName + "," + origin);
			cookie.setMaxAge(30 * 24 * 60 * 60);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("user", null);
			cookie.setMaxAge(0);
			// 进行覆盖
			response.addCookie(cookie);
		}

		User qureyUser = userService.get(userName);
		if (qureyUser != null) {
			String hashToDb = qureyUser.getPassword();
			if (SaltUtil.varify(origin, hashToDb)) {
				// 表示密码正确
				session.setAttribute("user", qureyUser);
				return "redirect:/forehome";
			}
		}
		model.addAttribute("msg", "账号密码错误");
		return "fore/login";

	}

	@RequestMapping("forelogout")
	public String forelogout(Model model, HttpSession session) {
		session.removeAttribute("user");
		return "redirect:forehome";
	}

	@RequestMapping("forecheckLogin")
	@ResponseBody
	public String forecheckLogin(Model model, HttpSession session) {
		if (session.getAttribute("user") != null) {
			return "success";
		} else {
			return "fail";
		}
	}

	@RequestMapping("foreloginAjax")
	@ResponseBody
	public String foreloginAjax(Model model, String name, String password,
			HttpSession session) {
		String userName = HtmlUtils.htmlEscape(name);
		String origin = password;

		User qureyUser = userService.get(userName);
		if (qureyUser != null) {
			String hashToDb = qureyUser.getPassword();
			if (SaltUtil.varify(origin, hashToDb)) {
				// 表示密码正确
				session.setAttribute("user", qureyUser);
				return "success";
			}
		}
		return "fail";

	}

	@RequestMapping("complaint_add")
	public String complaint_add(Model model, HttpSession session,
			Complaint complaint, UploadedImageFile uploadedImageFile)
			throws IOException {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			complaint.setUid(user.getId());
		}
		complaintService.add(complaint);

		// 对于图片的处理
		int cid = complaint.getId();

		// 支持多图
		MultipartFile[] imageFiles = uploadedImageFile.getImage();
		for (MultipartFile multipartFile : imageFiles) {
			ComplaintImage complaintImage = new ComplaintImage();
			complaintImage.setCid(cid);
			complaintImage.setType(ComplaintImageService.TYPE_DETAIL);
			complaintImageService.add(complaintImage);

			int ciid = complaintImage.getId();
			String fileName = ciid + ".jpg";
			String imageFolder = session.getServletContext().getRealPath(
					"img/complaint");
			String imageFolder_small = session.getServletContext().getRealPath(
					"img/complaint_small");
			String imageFolder_middle = session.getServletContext()
					.getRealPath("img/complaint_middle");

			// D:\eclipse-win64\myworkspace\.metadata\.plugins\
			// org.eclipse.wst.server.core\tmp0\wtpwebapps\complaint_ssm\img\complaint
			// System.out.println("图片存储路径:" + imageFolder);

			File f = new File(imageFolder, fileName);
			f.getParentFile().mkdirs();
			try {
				multipartFile.transferTo(f);
				BufferedImage img = ImageUtil.change2jpg(f);
				ImageIO.write(img, "jpg", f);
				File f_small = new File(imageFolder_small, fileName);
				File f_middle = new File(imageFolder_middle, fileName);

				ImageUtil.resizeImage(f, 56, 56, f_small);
				ImageUtil.resizeImage(f, 217, 190, f_middle);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "redirect:/forehome";
	}

	@RequestMapping("complaint_delete")
	public String complaint_delete(Model model, HttpSession session, int id)
			throws IOException {
		String fileName = "";
		String imageFolder = session.getServletContext().getRealPath(
				"img/complaint");
		String imageFolder_small = session.getServletContext().getRealPath(
				"img/complaint_small");
		String imageFolder_middle = session.getServletContext().getRealPath(
				"img/complaint_middle");

		Complaint complaint = complaintService.get(id);
		List<ComplaintImage> complaintImageList = complaint
				.getComplaintImageList();
		for (ComplaintImage complaintImage : complaintImageList) {
			int ciid = complaintImage.getId();
			fileName = ciid + ".jpg";

			File imageFile = new File(imageFolder, fileName);
			File f_small = new File(imageFolder_small, fileName);
			File f_middle = new File(imageFolder_middle, fileName);
			imageFile.delete();
			f_small.delete();
			f_middle.delete();
			complaintImageService.delete(ciid);
		}

		complaintService.delete(id);
		return "redirect:/forehome";
	}

	@RequestMapping("complaint_detail")
	public String complaint_detail(Model model, HttpSession session, int id) {
		Complaint complaint = complaintService.get(id);

		boolean isShowEdit = false;
		User user = (User) session.getAttribute("user");
		if (user != null && user.getId() == complaint.getUid()) {
			isShowEdit = true;
		}

		model.addAttribute("isShowEdit", isShowEdit);
		model.addAttribute("c", complaint);
		return "fore/complaintDetail";
	}

	@RequestMapping("foremycomplaint")
	public String foremycomplaint(Model model, HttpSession session, Page page) {
		PageHelper.offsetPage(page.getStart(), page.getCount());
		User user = (User) session.getAttribute("user");
		List<Complaint> complaintList = complaintService.listByUser(user
				.getId());
		int total = (int) new PageInfo<>(complaintList).getTotal();
		page.setTotal(total);

		boolean isShowDel = true;
		model.addAttribute("isShowDel", isShowDel);
		model.addAttribute("page", page);
		model.addAttribute("cs", complaintList);
		return "fore/myComplaint";
	}

	@RequestMapping("forecomplaintedit")
	public String forecomplaintedit(Model model, HttpSession session, int id) {
		User user = (User) session.getAttribute("user");
		Complaint complaint = complaintService.get(id);

		model.addAttribute("c", complaint);
		return "fore/editComplaint";
	}

	//目前是整个页面刷新的方式，想要更好可以考虑用ajax来局部刷新
	@RequestMapping("complaintImageDelete")
	public String complaintImageDelete(Model model, HttpSession session, int id)
			throws IOException {
		String fileName = "";
		String imageFolder = session.getServletContext().getRealPath(
				"img/complaint");
		String imageFolder_small = session.getServletContext().getRealPath(
				"img/complaint_small");
		String imageFolder_middle = session.getServletContext().getRealPath(
				"img/complaint_middle");

		fileName = id + ".jpg";

		File imageFile = new File(imageFolder, fileName);
		File f_small = new File(imageFolder_small, fileName);
		File f_middle = new File(imageFolder_middle, fileName);
		imageFile.delete();
		f_small.delete();
		f_middle.delete();
		
		int cid = complaintImageService.get(id).getCid();
		complaintImageService.delete(id);
		
		Complaint complaint = complaintService.get(cid);

		model.addAttribute("c", complaint);
		
		//表示需要页面跳转到图片列表处
		model.addAttribute("jump", true);
		return "fore/editComplaint";
	}
	
	@RequestMapping("complaintImageAdd")
	public String complaintImageAdd(Model model, HttpSession session,
			int id, UploadedImageFile uploadedImageFile)
			throws IOException {
		// 支持多图
		MultipartFile[] imageFiles = uploadedImageFile.getImage();
		for (MultipartFile multipartFile : imageFiles) {
			ComplaintImage complaintImage = new ComplaintImage();
			complaintImage.setCid(id);
			complaintImage.setType(ComplaintImageService.TYPE_DETAIL);
			complaintImageService.add(complaintImage);

			int ciid = complaintImage.getId();
			String fileName = ciid + ".jpg";
			String imageFolder = session.getServletContext().getRealPath(
					"img/complaint");
			String imageFolder_small = session.getServletContext().getRealPath(
					"img/complaint_small");
			String imageFolder_middle = session.getServletContext()
					.getRealPath("img/complaint_middle");

			// D:\eclipse-win64\myworkspace\.metadata\.plugins\
			// org.eclipse.wst.server.core\tmp0\wtpwebapps\complaint_ssm\img\complaint
			// System.out.println("图片存储路径:" + imageFolder);

			File f = new File(imageFolder, fileName);
			f.getParentFile().mkdirs();
			try {
				multipartFile.transferTo(f);
				BufferedImage img = ImageUtil.change2jpg(f);
				ImageIO.write(img, "jpg", f);
				File f_small = new File(imageFolder_small, fileName);
				File f_middle = new File(imageFolder_middle, fileName);

				ImageUtil.resizeImage(f, 56, 56, f_small);
				ImageUtil.resizeImage(f, 217, 190, f_middle);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Complaint complaint = complaintService.get(id);
		model.addAttribute("c", complaint);
		//表示需要页面跳转到图片列表处
		model.addAttribute("jump", true);
		return "fore/editComplaint";
	}
	
	@RequestMapping("forecomplaintupdate")
	public String forecomplaintupdate(Model model, HttpSession session, Complaint complaint) {
		complaintService.update(complaint);
		model.addAttribute("id", complaint.getId());
		return "redirect:/complaint_detail";
	}
}