package com.kindlink.kindLink.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // ──────────────────────────────
    // Required by JPA
    // ──────────────────────────────
    public Category() {}

    // ──────────────────────────────
    // Your missing constructor
    // ──────────────────────────────
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // ──────────────────────────────
    // Getters & Setters
    // ──────────────────────────────
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
