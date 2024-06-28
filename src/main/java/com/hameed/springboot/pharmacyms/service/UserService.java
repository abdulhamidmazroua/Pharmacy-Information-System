package com.hameed.springboot.pharmacyms.service;

import com.hameed.springboot.pharmacyms.model.entity.User;

public interface UserService {

    public User getByUsername(String username);
    public String getLoggedInUsername();

}
