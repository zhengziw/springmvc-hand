package com.mvc.service.serviceImpl;

import com.mvc.annotation.Service;
import com.mvc.pojo.User;
import com.mvc.service.UserService;

import java.util.ArrayList;
import java.util.List;
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    public List<User> findUsers(String name) {
        System.out.println("查询参数"+name);
        List list=new ArrayList();
        User user=new User();
        user.setName(name);
        list.add(user);
        return list;
    }
}
