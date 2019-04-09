package com.how2java.complaint.interceptor;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.how2java.complaint.pojo.User;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	/**
	 * ��ҵ��������������֮ǰ������ �������false
	 * �ӵ�ǰ������������ִ��������������afterCompletion(),���˳���������
	 * �������true ִ����һ��������,ֱ�����е���������ִ����� ��ִ�б����ص�Controller
	 * Ȼ�������������, �����һ������������ִ�����е�postHandle()
	 * �����ٴ����һ������������ִ�����е�afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession();
		String contextPath = session.getServletContext().getContextPath();
		String[] noNeedAuthPage = new String[] { "home", "checkLogin",
				"register", "loginAjax", "login", "product", "category",
				"search" };

		String uri = request.getRequestURI();
		uri = StringUtils.remove(uri, contextPath);
		// System.out.println(uri);
		if (uri.startsWith("/fore")) {
			String method = StringUtils.substringAfterLast(uri, "/fore");
			if (!Arrays.asList(noNeedAuthPage).contains(method)) {
				User user = (User) session.getAttribute("user");
				if (null == user) {
					response.sendRedirect("loginPage");
					return false;
				}
			}
		}

		return true;

	}

	/**
	 * ��ҵ��������������ִ����ɺ�,������ͼ֮ǰִ�еĶ���
	 * ����modelAndView�м������ݣ����統ǰʱ��
	 */

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * ��DispatcherServlet��ȫ����������󱻵���,������������Դ��
	 * 
	 * �����������׳��쳣ʱ,��ӵ�ǰ����������ִ�����е���������afterCompletion()
	 */

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}