package com.hameed.springboot.pharmacyms.service.impl;

import com.hameed.springboot.pharmacyms.dao.UnitOfMeasureDAO;
import com.hameed.springboot.pharmacyms.model.entity.UnitOfMeasure;
import com.hameed.springboot.pharmacyms.service.UnitOfMeasureService;
import com.hameed.springboot.pharmacyms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private UnitOfMeasureDAO unitOfMeasureDAO;
    private UserService userService;

    @Autowired
    public UnitOfMeasureServiceImpl(UnitOfMeasureDAO unitOfMeasureDAO, UserService userService) {
        this.unitOfMeasureDAO = unitOfMeasureDAO;
        this.userService = userService;
    }

    @Override
    public UnitOfMeasure getUnitOfMeasureById(Long id) {
        return unitOfMeasureDAO.findById(id);
    }

    @Override
    public UnitOfMeasure getUnitOfMeasureByCode(String code) {
        return unitOfMeasureDAO.findByCode(code);
    }

    @Override
    public List<UnitOfMeasure> getAllUnitsOfMeasures() {
        return unitOfMeasureDAO.findAll();
    }

    @Override
    @Transactional
    public UnitOfMeasure createUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        unitOfMeasure.setCreatedBy(userService.getLoggedInUsername());
        unitOfMeasure.setLastUpdateBy("-1");
        unitOfMeasure.setCreationDate(new Date());
        unitOfMeasure.setLastUpdateDate(new Date());
        unitOfMeasureDAO.save(unitOfMeasure);
        return unitOfMeasure;
    }

    @Override
    @Transactional
    public UnitOfMeasure updateUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        unitOfMeasure.setLastUpdateBy(userService.getLoggedInUsername());
        unitOfMeasure.setLastUpdateDate(new Date());
        unitOfMeasureDAO.save(unitOfMeasure);
        return unitOfMeasure;
    }

    @Override
    @Transactional
    public void deleteUnitOfMeasure(Long id) {
        unitOfMeasureDAO.deleteById(id);
    }
}
