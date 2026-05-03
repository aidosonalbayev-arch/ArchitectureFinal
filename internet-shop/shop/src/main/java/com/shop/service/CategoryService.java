package com.shop.service;

import com.shop.exception.ResourceNotFoundException;
import com.shop.model.Category;
import com.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category create(String name, String description) {
        if (categoryRepository.existsByName(name)) {
            throw new IllegalArgumentException("Category already exists: " + name);
        }
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public Category update(Long id, String name, String description) {
        Category category = findById(id);
        category.setName(name);
        category.setDescription(description);
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}
