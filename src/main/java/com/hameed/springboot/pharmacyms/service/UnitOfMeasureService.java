package com.hameed.springboot.pharmacyms.service;


import com.hameed.springboot.pharmacyms.model.entity.UnitOfMeasure;

import java.util.List;

public interface UnitOfMeasureService {
    UnitOfMeasure getUnitOfMeasureById(Long id);
    UnitOfMeasure getUnitOfMeasureByCode(String code);
    List<UnitOfMeasure> getAllUnitsOfMeasures();
    UnitOfMeasure createUnitOfMeasure(UnitOfMeasure unitOfMeasure);
    UnitOfMeasure updateUnitOfMeasure(UnitOfMeasure unitOfMeasure);
    void deleteUnitOfMeasure(Long id);
}