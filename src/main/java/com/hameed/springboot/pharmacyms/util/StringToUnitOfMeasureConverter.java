package com.hameed.springboot.pharmacyms.util;

import com.hameed.springboot.pharmacyms.dao.UnitOfMeasureDAO;
import com.hameed.springboot.pharmacyms.model.entity.UnitOfMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUnitOfMeasureConverter implements Converter<String, UnitOfMeasure> {

    private UnitOfMeasureDAO unitOfMeasureDAO;

    @Autowired
    public StringToUnitOfMeasureConverter(UnitOfMeasureDAO unitOfMeasureDAO) {
        this.unitOfMeasureDAO = unitOfMeasureDAO;
    }

    @Override
    public UnitOfMeasure convert(String source) {
        Long unitOfMeasureId = Long.valueOf(source);
        return unitOfMeasureDAO.findById(unitOfMeasureId);
    }
}
