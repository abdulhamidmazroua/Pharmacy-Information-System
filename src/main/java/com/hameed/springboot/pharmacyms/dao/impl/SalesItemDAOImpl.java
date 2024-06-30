package com.hameed.springboot.pharmacyms.dao.impl;

import com.hameed.springboot.pharmacyms.dao.SalesItemDAO;
import com.hameed.springboot.pharmacyms.model.entity.SalesItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SalesItemDAOImpl implements SalesItemDAO {

    private EntityManager entityManager;

    @Autowired
    public SalesItemDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(SalesItem s) {
        entityManager.merge(s);
    }

    @Override
    public void saveAll(List<SalesItem> salesItems) {
        for (SalesItem s : salesItems) {
            save(s);
        }
    }

    @Override
    public SalesItem findById(Long id) {
        return entityManager.find(SalesItem.class, id);
    }


    @Override
    public List<SalesItem> findAll(List<Long> ids) {
        TypedQuery<SalesItem> query = entityManager.createQuery("FROM SalesItem s WHERE s.id in (:ids)", SalesItem.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }

    @Override
    public List<SalesItem> findAll() {
        TypedQuery<SalesItem> query = entityManager.createQuery("FROM SalesItem", SalesItem.class);
        return query.getResultList();
    }

    @Override
    public boolean existsById(Long id) {
        return (findById(id) == null);
    }

    @Override
    public int count() {
        TypedQuery<Integer> query = entityManager.createQuery("SELECT COUNT(s) FROM SalesItem s", Integer.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public void delete(SalesItem s) {
        entityManager.remove(s);
    }

    @Override
    public void deleteAll(List<SalesItem> sales) {
        entityManager.remove(sales);
    }

    @Override
    public void deleteAll() {
        entityManager.remove(findAll());
    }


}
