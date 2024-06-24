package com.hameed.springboot.pharmacyms.dao;

import com.hameed.springboot.pharmacyms.entity.Sale;

import java.util.List;

public interface SaleDAO {

    public void save(Sale s);
    public void saveAll(List<Sale> sales);
    public Sale findById(int id);
    public Sale findByCustomerName(String name);
    public List<Sale> findAll(List<Long> id);
    public List<Sale> findAll();
    public boolean existsById(Long id);
    public boolean existsByName(String name);
    public int count();
    public void deleteById(Long id);
    public void delete(Sale s);
    public void deleteAll(List<Sale> sales);
    public void deleteAll();


}
