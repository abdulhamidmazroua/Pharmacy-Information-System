package com.hameed.springboot.pharmacyms.service.impl;

import com.hameed.springboot.pharmacyms.dao.CategoryDAO;
import com.hameed.springboot.pharmacyms.model.entity.Category;
import com.hameed.springboot.pharmacyms.service.CategoryService;
import com.hameed.springboot.pharmacyms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryDAO categoryDAO;
    private UserService userService;

    @Autowired
    public CategoryServiceImpl(CategoryDAO categoryDAO, UserService userService) {
        this.categoryDAO = categoryDAO;
        this.userService = userService;
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryDAO.findById(id);
    }

    @Override
    public Category getCategoryByCode(String code) {
        return categoryDAO.findByCode(code);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        category.setCreatedBy(userService.getLoggedInUsername());
        category.setLastUpdateBy("-1");
        category.setCreationDate(new Date());
        category.setLastUpdateDate(new Date());
        categoryDAO.save(category);
        return category;
    }

    @Override
    @Transactional
    public Category updateCategory(Category category) {
        category.setLastUpdateBy(userService.getLoggedInUsername());
        category.setLastUpdateDate(new Date());
        categoryDAO.save(category);
        return category;
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryDAO.deleteById(id);
    }

}
