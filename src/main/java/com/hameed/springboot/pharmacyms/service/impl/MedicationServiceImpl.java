package com.hameed.springboot.pharmacyms.service.impl;

import com.hameed.springboot.pharmacyms.dao.MedicationDAO;
import com.hameed.springboot.pharmacyms.entity.Medication;
import com.hameed.springboot.pharmacyms.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {

    private MedicationDAO medicationDAO;

    @Autowired
    public MedicationServiceImpl(MedicationDAO medicationDAO) {
        this.medicationDAO = medicationDAO;
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
    public Medication createMedication(Medication medication) {
        medicationDAO.save(medication);
        return medication;
    }

    @Override
    public Medication updateMedication(Medication medication) {
        medicationDAO.save(medication);
        return medication;
    }

    @Override
    public void deleteMedication(Long id) {
        medicationDAO.deleteById(id);
    }
}
