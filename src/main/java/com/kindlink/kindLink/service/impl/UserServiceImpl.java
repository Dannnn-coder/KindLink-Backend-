package com.kindlink.kindLink.service.impl;

import com.kindlink.kindLink.entity.User;
import com.kindlink.kindLink.repository.UserRepository;
import com.kindlink.kindLink.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) { this.repo = repo; }

    @Override
    public List<User> getAllUsers() { return repo.findAll(); }

    @Override
    public User createUser(User user) { return repo.save(user); }

    @Override
    public User getUserById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> opt = repo.findById(id);
        if (opt.isEmpty()) return null;
        User existing = opt.get();
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setFullname(user.getFullname());
        existing.setRole(user.getRole());
        return repo.save(existing);
    }

    @Override
    public void deleteUser(Long id) { repo.deleteById(id); }
}
