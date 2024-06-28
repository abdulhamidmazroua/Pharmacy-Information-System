package com.hameed.springboot.pharmacyms.service.impl;

import com.hameed.springboot.pharmacyms.dao.CategoryDAO;
import com.hameed.springboot.pharmacyms.model.entity.Category;
import com.hameed.springboot.pharmacyms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryDAO categoryDAO;

    @Autowired
    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryDAO.findById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        categoryDAO.save(category);
        return category;
    }

    @Override
    @Transactional
    public Category updateCategory(Category category) {
        categoryDAO.save(category);
        return category;
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryDAO.deleteById(id);
    }
}
