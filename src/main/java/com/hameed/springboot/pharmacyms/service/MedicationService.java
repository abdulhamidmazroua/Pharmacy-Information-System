package com.hameed.springboot.pharmacyms.service;

import com.hameed.springboot.pharmacyms.model.entity.Medication;

import java.util.List;

public interface MedicationService {
    Medication getMedicationById(Long id);
    List<Medication> getAllMedications();
    Medication createMedication(Medication medication);
    Medication updateMedication(Medication medication);
    void deleteMedication(Long id);
}
