package com.kindlink.kindLink.service;

import com.kindlink.kindLink.entity.User;

public interface UserService {
    User register(User user);
    User findByEmail(String email);
}
