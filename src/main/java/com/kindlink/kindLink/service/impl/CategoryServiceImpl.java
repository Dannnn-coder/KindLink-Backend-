package com.kindlink.kindLink.service.impl;

import com.kindlink.kindLink.entity.Category;
import com.kindlink.kindLink.repository.CategoryRepository;
import com.kindlink.kindLink.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    public CategoryServiceImpl(CategoryRepository repo) { this.repo = repo; }

    @Override
    public List<Category> getAllCategories() { return repo.findAll(); }

    @Override
    public Category createCategory(Category category) { return repo.save(category); }

    @Override
    public Category getCategoryById(Long id) { return repo.findById(id).orElse(null); }

    @Override
    public Category updateCategory(Long id, Category category) {
        Optional<Category> opt = repo.findById(id);
        if (opt.isEmpty()) return null;
        Category existing = opt.get();
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        return repo.save(existing);
    }

    @Override
    public void deleteCategory(Long id) { repo.deleteById(id); }
}
