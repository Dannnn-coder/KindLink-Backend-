package com.kindlink.kindLink.service;

import com.kindlink.kindLink.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
    User getUserById(Long id);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    Optional<User> findByEmail(String email);     
    boolean existsByEmail(String email);          
}