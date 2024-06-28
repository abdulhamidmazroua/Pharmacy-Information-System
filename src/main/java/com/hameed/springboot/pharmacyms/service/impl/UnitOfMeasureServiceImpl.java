package com.hameed.springboot.pharmacyms.service.impl;

import com.hameed.springboot.pharmacyms.dao.UnitOfMeasureDAO;
import com.hameed.springboot.pharmacyms.model.entity.UnitOfMeasure;
import com.hameed.springboot.pharmacyms.service.UnitOfMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private UnitOfMeasureDAO unitOfMeasureDAO;


    @Autowired
    public UnitOfMeasureServiceImpl(UnitOfMeasureDAO unitOfMeasureDAO) {
        this.unitOfMeasureDAO = unitOfMeasureDAO;
    }

    @Override
    public UnitOfMeasure getUnitOfMeasureById(Long id) {
        return unitOfMeasureDAO.findById(id);
    }

    @Override
    public List<UnitOfMeasure> getAllUnitsOfMeasures() {
        return unitOfMeasureDAO.findAll();
    }

    @Override
    @Transactional
    public UnitOfMeasure createUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        unitOfMeasureDAO.save(unitOfMeasure);
        return unitOfMeasure;
    }

    @Override
    @Transactional
    public UnitOfMeasure updateUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        unitOfMeasureDAO.save(unitOfMeasure);
        return unitOfMeasure;
    }

    @Override
    @Transactional
    public void deleteUnitOfMeasure(Long id) {
        unitOfMeasureDAO.deleteById(id);
    }
}
