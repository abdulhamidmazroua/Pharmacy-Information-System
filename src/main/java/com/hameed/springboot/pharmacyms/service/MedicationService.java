package com.hameed.springboot.pharmacyms.service;

import com.hameed.springboot.pharmacyms.model.entity.Medication;

import java.util.List;

public interface MedicationService {
    Medication getMedicationById(Long id);
    List<Medication> getAllMedications();
    void createMedication(Medication medication);
    void updateMedication(Medication medication);
    void deleteMedication(Long id);
    Medication getMedicationByName(String name);
    List<Medication> searchMedications(String name, String categoryCode);
}
