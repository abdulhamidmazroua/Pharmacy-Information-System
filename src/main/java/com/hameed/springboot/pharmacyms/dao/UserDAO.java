package com.hameed.springboot.pharmacyms.dao;

import com.hameed.springboot.pharmacyms.model.entity.User;

public interface UserDAO {
    User findByUsername(String username);
}
