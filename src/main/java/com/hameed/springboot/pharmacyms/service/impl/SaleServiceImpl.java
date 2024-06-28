package com.hameed.springboot.pharmacyms.service.impl;

import com.hameed.springboot.pharmacyms.dao.SaleDAO;
import com.hameed.springboot.pharmacyms.model.entity.Sale;
import com.hameed.springboot.pharmacyms.service.SaleService;
import com.hameed.springboot.pharmacyms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private SaleDAO saleDAO;
    private UserService userService;

    @Autowired
    public SaleServiceImpl(SaleDAO saleDAO, UserService userService) {
        this.saleDAO = saleDAO;
        this.userService = userService;
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
        salesHeader.setCreatedBy(userService.getLoggedInUsername());
        salesHeader.setLastUpdateBy("-1");
        salesHeader.setCreationDate(new Date());
        salesHeader.setLastUpdateDate(new Date());
        saleDAO.save(salesHeader);
        return salesHeader;
    }

    @Override
    @Transactional
    public Sale updateSale(Sale salesHeader) {
        salesHeader.setLastUpdateBy(userService.getLoggedInUsername());
        salesHeader.setLastUpdateDate(new Date());
        saleDAO.save(salesHeader);
        return salesHeader;
    }

    @Override
    @Transactional
    public void deleteSale(Long id) {
        saleDAO.deleteById(id);
    }
}
