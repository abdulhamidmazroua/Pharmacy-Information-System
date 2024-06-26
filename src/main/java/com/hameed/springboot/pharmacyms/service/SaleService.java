package com.hameed.springboot.pharmacyms.service;

import com.hameed.springboot.pharmacyms.entity.Sale;

import java.util.List;

public interface SaleService {
    Sale getSaleById(Long id);
    List<Sale> getAllSales();
    Sale createSale(Sale salesHeader);
    Sale updateSale(Sale salesHeader);
    void deleteSale(Long id);
}
