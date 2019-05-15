package cn.how2j.springcloud.web;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
import cn.how2j.springcloud.pojo.Product;
import cn.how2j.springcloud.service.ProductService;
  
@Controller
@RefreshScope
public class ProductController {
    @Autowired ProductService productService;
     
    @Value("${version}")
    String version;
     
    @RequestMapping("/products")
    public Object products(Model m) {
        List<Product> ps = productService.listProducts();
        m.addAttribute("version", version);    
        m.addAttribute("ps", ps);
        return "products";
    }
}