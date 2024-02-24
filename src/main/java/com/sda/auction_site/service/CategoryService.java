package com.sda.auction_site.service;

import com.sda.auction_site.model.Category;
import com.sda.auction_site.repository.CategoryRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category findById(Long id){

        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("category not found"));
    }
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category findByName(String name){

        return categoryRepository.findByName(name);

    }


}
