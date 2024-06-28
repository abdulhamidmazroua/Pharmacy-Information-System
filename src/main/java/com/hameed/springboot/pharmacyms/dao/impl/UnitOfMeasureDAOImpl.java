package com.hameed.springboot.pharmacyms.dao.impl;

import com.hameed.springboot.pharmacyms.dao.UnitOfMeasureDAO;
import com.hameed.springboot.pharmacyms.model.entity.UnitOfMeasure;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UnitOfMeasureDAOImpl implements UnitOfMeasureDAO {

    private EntityManager entityManager;

    @Autowired
    public UnitOfMeasureDAOImpl(EntityManager em) {
        this.entityManager = em;
    }

    @Override
    public void save(UnitOfMeasure uom) {
        entityManager.persist(uom);
    }

    @Override
    public void saveAll(List<UnitOfMeasure> unitOfMeasures) {
        for (UnitOfMeasure uom : unitOfMeasures) {
            save(uom);
        }
    }

    @Override
    public UnitOfMeasure findById(Long id) {
        return entityManager.find(UnitOfMeasure.class, id);
    }

    @Override
    public UnitOfMeasure findByCode(String code) {
        TypedQuery<UnitOfMeasure> query = entityManager.createQuery("SELECT c FROM UnitOfMeasure c WHERE c.code = :code", UnitOfMeasure.class);
        query.setParameter("code", code);
        return query.getSingleResult();
    }

    @Override
    public List<UnitOfMeasure> findAll(List<Long> ids) {
        TypedQuery<UnitOfMeasure> query = entityManager.createQuery("FROM UnitOfMeasure c WHERE c.id in (:ids)", UnitOfMeasure.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }

    @Override
    public List<UnitOfMeasure> findAll() {
        TypedQuery<UnitOfMeasure> query = entityManager.createQuery("FROM UnitOfMeasure c", UnitOfMeasure.class);
        return query.getResultList();
    }

    @Override
    public boolean existsById(Long id) {
        return (findById(id) != null);
    }

    @Override
    public boolean existsByCode(String code) {
        return (findByCode(code) != null);
    }

    @Override
    public int count() {
        TypedQuery<Integer> query = entityManager.createQuery("SELECT COUNT(m) FROM UnitOfMeasure uom", Integer.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public void delete(UnitOfMeasure uom) {
        entityManager.remove(uom);
    }

    @Override
    public void deleteAll(List<UnitOfMeasure> unitOfMeasures) {
        entityManager.remove(unitOfMeasures);
    }

    @Override
    public void deleteAll() {
        entityManager.remove(findAll());
    }

}
