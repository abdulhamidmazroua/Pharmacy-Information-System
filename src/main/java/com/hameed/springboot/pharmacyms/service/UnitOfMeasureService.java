package com.hameed.springboot.pharmacyms.service;


import com.hameed.springboot.pharmacyms.entity.UnitOfMeasure;

import java.util.List;

public interface UnitOfMeasureService {
    UnitOfMeasure getUnitOfMeasureById(Long id);
    List<UnitOfMeasure> getAllUnitsOfMeasures();
    UnitOfMeasure createUnitOfMeasure(UnitOfMeasure unitOfMeasure);
    UnitOfMeasure updateUnitOfMeasure(UnitOfMeasure unitOfMeasure);
    void deleteUnitOfMeasure(Long id);
}