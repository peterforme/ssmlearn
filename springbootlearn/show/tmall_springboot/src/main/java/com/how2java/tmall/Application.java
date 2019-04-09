package com.how2java.tmall;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.how2java.tmall.util.PortUtil; 
@SpringBootApplication
@EnableCaching
@EnableElasticsearchRepositories(basePackages = "com.how2java.tmall.es")
@EnableJpaRepositories(basePackages = {"com.how2java.tmall.dao", "com.how2java.tmall.pojo"})
@ServletComponentScan
/**
 * 其他源代码去哪了？
 * 这里仅仅作为演示用，表示本项目是可用的，建立大家学习的信心，但是当前下载不提供完整源代码，完整源代码请于如下地址下载：
 * http://how2j.cn/k/tmall_springboot/tmall_springboot-1866/1866.html
 */
public class Application extends SpringBootServletInitializer{
	static {
    	PortUtil.checkPort(6379,"Redis 服务端",true);
    	PortUtil.checkPort(9300,"ElasticSearch 服务端",true);
    	PortUtil.checkPort(5601,"Kibana 工具", true);
	}
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
 
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);    	
    }

    
}
