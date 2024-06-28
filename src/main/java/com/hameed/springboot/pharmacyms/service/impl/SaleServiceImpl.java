package com.hameed.springboot.pharmacyms.service.impl;

import com.hameed.springboot.pharmacyms.dao.SaleDAO;
import com.hameed.springboot.pharmacyms.model.entity.Sale;
import com.hameed.springboot.pharmacyms.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private SaleDAO saleDAO;

    @Autowired
    public SaleServiceImpl(SaleDAO saleDAO) {
        this.saleDAO = saleDAO;
    }

    @Override
    public Sale getSaleById(Long id) {
        return saleDAO.findById(id);
    }

    @Override
    public List<Sale> getAllSales() {
        return saleDAO.findAll();
    }

    @Override
    @Transactional
    public Sale createSale(Sale salesHeader) {
        saleDAO.save(salesHeader);
        return salesHeader;
    }

    @Override
    @Transactional
    public Sale updateSale(Sale salesHeader) {
        saleDAO.save(salesHeader);
        return salesHeader;
    }

    @Override
    @Transactional
    public void deleteSale(Long id) {
        saleDAO.deleteById(id);
    }
}
