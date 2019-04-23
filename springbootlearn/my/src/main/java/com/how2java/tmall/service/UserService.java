package com.how2java.tmall.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
 
import com.how2java.tmall.dao.UserDAO;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.util.Page4Navigator;
 
@Service
public class UserService {
     
    @Autowired UserDAO userDAO;
 
    public Page4Navigator<User> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =userDAO.findAll(pageable);
        
        int num = pageFromJPA.getContent().size();
        System.out.println(num);
        
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }
 
}