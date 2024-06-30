package com.hameed.springboot.pharmacyms.dao.impl;

import com.hameed.springboot.pharmacyms.dao.CategoryDAO;
import com.hameed.springboot.pharmacyms.model.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    private EntityManager entityManager;

    @Autowired
    public CategoryDAOImpl(EntityManager em) {
        this.entityManager = em;
    }

    @Override
    public void save(Category cat) {
        entityManager.merge(cat);
    }

    @Override
    public void saveAll(List<Category> categories) {
        for (Category cat : categories) {
            save(cat);
        }
    }

    @Override
    public Category findById(Long id) {
        return entityManager.find(Category.class, id);
    }

    @Override
    public Category findByCode(String code) {
        TypedQuery<Category> query = entityManager.createQuery("SELECT c FROM Category c WHERE c.categoryCode = :code", Category.class);
        query.setParameter("code", code);
        return query.getSingleResult();
    }

    @Override
    public List<Category> findAll(List<Long> ids) {
        TypedQuery<Category> query = entityManager.createQuery("FROM Category c WHERE c.id in (:ids)", Category.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }

    @Override
    public List<Category> findAll() {
        TypedQuery<Category> query = entityManager.createQuery("FROM Category c", Category.class);
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
        TypedQuery<Integer> query = entityManager.createQuery("SELECT COUNT(c) FROM Category c", Integer.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public void delete(Category category) {
        entityManager.remove(category);
    }

    @Override
    public void deleteAll(List<Category> categories) {
        entityManager.remove(categories);
    }

    @Override
    public void deleteAll() {
        entityManager.remove(findAll());
    }
}
