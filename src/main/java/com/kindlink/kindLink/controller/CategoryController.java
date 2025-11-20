package com.kindlink.kindLink.controller;

import com.kindlink.kindLink.entity.Category;
import com.kindlink.kindLink.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryService service;
    public CategoryController(CategoryService service) { this.service = service; }

    @GetMapping
    public List<Category> getAll() { return service.getAllCategories(); }

    @PostMapping
    public Category create(@RequestBody Category category) { return service.createCategory(category); }

    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) { return service.getCategoryById(id); }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category) { return service.updateCategory(id, category); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.deleteCategory(id); }
}
