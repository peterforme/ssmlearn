package com.how2java.tmall.interceptor;
import java.util.Arrays;
import java.util.List;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
 
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.OrderItemService;
  
public class OtherInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderItemService orderItemService;
     /**
     * ��ҵ��������������֮ǰ������
     * �������false
     *     �ӵ�ǰ������������ִ��������������afterCompletion(),���˳���������
     * �������true
     *    ִ����һ��������,ֱ�����е���������ִ�����
     *    ��ִ�б����ص�Controller
     *    Ȼ�������������,
     *    �����һ������������ִ�����е�postHandle()
     *    �����ٴ����һ������������ִ�����е�afterCompletion()
     */
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        return true;
 
    }
  
    /**
     * ��ҵ��������������ִ����ɺ�,������ͼ֮ǰִ�еĶ���
     * ����modelAndView�м������ݣ����統ǰʱ��
     */
 
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,   
            ModelAndView modelAndView) throws Exception {
        /*�����ǻ�ȡ���༯����Ϣ�����ڷ�������������*/
        List<Category> cs = categoryService.list();
        request.getSession().setAttribute("cs", cs);
 
        /*�����ǻ�ȡ��ǰ��contextPath:tmall_ssm,����������Ͻ��Ǹ����ν�գ����֮����ܹ���ת����ҳ��������֮��Ҳ����ͣ���ڵ�ǰҳ��*/
        HttpSession session = request.getSession();
        String contextPath=session.getServletContext().getContextPath();
        request.getSession().setAttribute("contextPath", contextPath);
 
        /*�����ǻ�ȡ���ﳵ��һ���ж�������*/
        User user =(User)  session.getAttribute("user");
        int  cartTotalItemNumber = 0;
        if(null!=user) {
            List<OrderItem> ois = orderItemService.listByUser(user.getId());
            for (OrderItem oi : ois) {
                cartTotalItemNumber+=oi.getNumber();
            }
         
        }
        request.getSession().setAttribute("cartTotalItemNumber", cartTotalItemNumber);
 
    }
  
    /** 
     * ��DispatcherServlet��ȫ����������󱻵���,������������Դ��  
     *  
     * �����������׳��쳣ʱ,��ӵ�ǰ����������ִ�����е���������afterCompletion() 
     */
      
    public void afterCompletion(HttpServletRequest request,   
            HttpServletResponse response, Object handler, Exception ex) 
    throws Exception { 
            
//        System.out.println("afterCompletion(), �ڷ�����ͼ֮�󱻵���");
    } 
        
}