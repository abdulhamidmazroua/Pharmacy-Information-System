package com.hameed.springboot.pharmacyms.service.impl;

import com.hameed.springboot.pharmacyms.dao.MedicationDAO;
import com.hameed.springboot.pharmacyms.model.entity.Medication;
import com.hameed.springboot.pharmacyms.service.MedicationService;
import com.hameed.springboot.pharmacyms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {

    private MedicationDAO medicationDAO;
    private UserService userService;
    @Autowired
    public MedicationServiceImpl(MedicationDAO medicationDAO, UserService userService) {
        this.medicationDAO = medicationDAO;
        this.userService = userService;
    }

    @Override
    public Medication getMedicationById(Long id) {
        return medicationDAO.findById(id);
    }

    @Override
    public List<Medication> getAllMedications() {
        return medicationDAO.findAll();
    }

    @Override
    @Transactional
    public void createMedication(Medication medication) {
        medication.setCreatedBy(userService.getLoggedInUsername());
        medication.setLastUpdateBy("-1");
        medication.setCreationDate(new Date());
        medication.setLastUpdateDate(new Date());
        medicationDAO.save(medication);
//        return medication;
    }

    @Override
    @Transactional
    public void updateMedication(Medication medication) {
        medication.setLastUpdateBy(userService.getLoggedInUsername());
        medication.setLastUpdateDate(new Date());
        medicationDAO.save(medication);
//        return medication;
    }

    @Override
    @Transactional
    public void deleteMedication(Long id) {
        medicationDAO.deleteById(id);
    }

    @Override
    public Medication getMedicationByName(String name) {
        return medicationDAO.findByName(name);
    }

    @Override
    @Transactional
    public List<Medication> searchMedications(String name, String categoryCode) {
        return medicationDAO.queryMedications(name, categoryCode);
    }

}
