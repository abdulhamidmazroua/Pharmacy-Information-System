package com.hameed.springboot.pharmacyms.util;

import com.hameed.springboot.pharmacyms.dao.CategoryDAO;
import com.hameed.springboot.pharmacyms.model.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoryConverter implements Converter<String, Category> {

    private CategoryDAO categoryDAO;

    @Autowired
    public StringToCategoryConverter(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public Category convert(String source) {
        Long categoryId = Long.valueOf(source);

        return categoryDAO.findById(categoryId);
    }
}
