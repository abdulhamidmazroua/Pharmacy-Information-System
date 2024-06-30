package com.hameed.springboot.pharmacyms.util;

import com.hameed.springboot.pharmacyms.model.entity.UnitOfMeasure;
import com.hameed.springboot.pharmacyms.service.UnitOfMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUnitOfMeasureConverter implements Converter<String, UnitOfMeasure> {

    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public StringToUnitOfMeasureConverter(UnitOfMeasureService unitOfMeasureService) {
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @Override
    public UnitOfMeasure convert(String source) {
        return unitOfMeasureService.getUnitOfMeasureByCode(source);
    }
}
