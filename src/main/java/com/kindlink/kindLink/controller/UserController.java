package com.kindlink.kindLink.controller;

import com.kindlink.kindLink.entity.User;
import com.kindlink.kindLink.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    public UserController(UserService service) { this.service = service; }

    @GetMapping
    public List<User> getAll() { return service.getAllUsers(); }

    @PostMapping
    public User create(@RequestBody User user) { return service.createUser(user); }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) { return service.getUserById(id); }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) { return service.updateUser(id, user); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.deleteUser(id); }
}
