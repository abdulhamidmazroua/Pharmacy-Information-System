package com.hameed.springboot.pharmacyms.dao.impl;

import com.hameed.springboot.pharmacyms.dao.UserDAO;
import com.hameed.springboot.pharmacyms.model.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    private EntityManager entityManager;

    public UserDAOImpl(EntityManager entityManager ) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("FROM User WHERE username = :name ", User.class);
        query.setParameter("name", username);
        return query.getSingleResult();
    }
}
