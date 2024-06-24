package com.hameed.springboot.pharmacyms.dao;

import com.hameed.springboot.pharmacyms.entity.Category;

import java.util.List;

public interface CategoryDAO {
    public void save(Category cat);
    public void saveAll(List<Category> categories);
    public Category findById(Long id);
    public Category findByCode(String code);
    public List<Category> findAll(List<Long> id);
    public List<Category> findAll();
    public boolean existsById(Long id);
    public boolean existsByCode(String code);
    public int count();
    public void deleteById(Long id);
    public void delete(Category uom);
    public void deleteAll(List<Category> categories);
    public void deleteAll();
}
