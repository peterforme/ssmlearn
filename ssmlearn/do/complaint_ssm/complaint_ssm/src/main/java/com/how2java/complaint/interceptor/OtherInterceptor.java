package com.how2java.complaint.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class OtherInterceptor extends HandlerInterceptorAdapter {

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

		// System.out.println("afterCompletion(), �ڷ�����ͼ֮�󱻵���");
	}

}