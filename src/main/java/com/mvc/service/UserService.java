package com.mvc.service;

import com.mvc.pojo.User;

import java.util.List;

public interface UserService {
    List<User> findUsers(String name);
}
