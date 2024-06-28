package com.hameed.springboot.pharmacyms.dao.impl;

import com.hameed.springboot.pharmacyms.dao.SaleDAO;
import com.hameed.springboot.pharmacyms.model.entity.Sale;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SaleDAOImpl implements SaleDAO {
    private EntityManager entityManager;

    @Autowired
    public SaleDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Sale s) {
        entityManager.persist(s);
    }

    @Override
    public void saveAll(List<Sale> sales) {
        for (Sale s : sales) {
            save(s);
        }
    }

    @Override
    public Sale findById(Long id) {
        return entityManager.find(Sale.class, id);
    }

    @Override
    public Sale findByCustomerName(String name) {
        TypedQuery<Sale> query = entityManager.createQuery("FROM Sale s where s.customerName=:customerName", Sale.class);
        query.setParameter("customerName", name);
        return query.getSingleResult();
    }

    @Override
    public List<Sale> findAll(List<Long> ids) {
        TypedQuery<Sale> query = entityManager.createQuery("FROM Sale m WHERE m.id in (:ids)", Sale.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }

    @Override
    public List<Sale> findAll() {
        TypedQuery<Sale> query = entityManager.createQuery("FROM Sale", Sale.class);
        return query.getResultList();
    }

    @Override
    public boolean existsById(Long id) {
        return (findById(id) == null);
    }

    @Override
    public boolean existsByCustomerName(String name) {
        return (findByCustomerName(name) == null);
    }

    @Override
    public int count() {
        TypedQuery<Integer> query = entityManager.createQuery("SELECT COUNT(s) FROM Sale s", Integer.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public void delete(Sale m) {
        entityManager.remove(m);
    }

    @Override
    public void deleteAll(List<Sale> sales) {
        entityManager.remove(sales);
    }

    @Override
    public void deleteAll() {
        entityManager.remove(findAll());
    }
}
