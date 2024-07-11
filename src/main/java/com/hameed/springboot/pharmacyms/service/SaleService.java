package com.hameed.springboot.pharmacyms.service;

import com.hameed.springboot.pharmacyms.model.entity.Sale;
import com.hameed.springboot.pharmacyms.model.entity.SalesItem;

import java.util.List;

public interface SaleService {
    // sales headers
    Sale getSaleById(Long id);
    List<Sale> getAllSales();
    Sale createSale(Sale salesHeader);
    Sale updateSale(Sale salesHeader);
    void deleteSale(Long id);
}
