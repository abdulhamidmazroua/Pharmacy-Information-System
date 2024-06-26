package com.hameed.springboot.pharmacyms.dao.impl;

import com.hameed.springboot.pharmacyms.dao.MedicationDAO;
import com.hameed.springboot.pharmacyms.entity.Medication;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicationDAOImpl implements MedicationDAO {

    private EntityManager entityManager;

    @Autowired
    public MedicationDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Medication m) {
        entityManager.persist(m);
    }

    @Override
    public void saveAll(List<Medication> medications) {
        for (Medication m : medications) {
            save(m);
        }
    }

    @Override
    public Medication findById(Long id) {
        return entityManager.find(Medication.class, id);
    }

    @Override
    public Medication findByName(String name) {
        TypedQuery<Medication> query = entityManager.createQuery("FROM Medication m where m.medicationName=:name", Medication.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<Medication> findAll(List<Long> ids) {
        TypedQuery<Medication> query = entityManager.createQuery("FROM Medication m WHERE m.id in (:ids)", Medication.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }

    @Override
    public List<Medication> findAll() {
        TypedQuery<Medication> query = entityManager.createQuery("FROM Medication", Medication.class);
        return query.getResultList();
    }

    @Override
    public boolean existsById(Long id) {
        return (findById(id) == null);
    }

    @Override
    public boolean existsByName(String name) {
        return (findByName(name) == null);
    }

    @Override
    public int count() {
        TypedQuery<Integer> query = entityManager.createQuery("SELECT COUNT(m) FROM Medication m", Integer.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public void delete(Medication m) {
        entityManager.remove(m);
    }

    @Override
    public void deleteAll(List<Medication> medications) {
        entityManager.remove(medications);
    }

    @Override
    public void deleteAll() {
        entityManager.remove(findAll());
    }
}
