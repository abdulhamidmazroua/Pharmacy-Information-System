package com.hameed.springboot.pharmacyms.service.impl;

import com.hameed.springboot.pharmacyms.dao.SaleDAO;
import com.hameed.springboot.pharmacyms.dao.SalesItemDAO;
import com.hameed.springboot.pharmacyms.model.entity.Sale;
import com.hameed.springboot.pharmacyms.model.entity.SalesItem;
import com.hameed.springboot.pharmacyms.model.entity.User;
import com.hameed.springboot.pharmacyms.service.SalesItemService;
import com.hameed.springboot.pharmacyms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SalesItemImpl implements SalesItemService {

    private SalesItemDAO salesItemDAO;
    private SaleDAO saleDAO;
    private UserService userService;

    @Autowired
    public SalesItemImpl(SalesItemDAO salesItemDAO, SaleDAO saleDAO, UserService userService) {
        this.salesItemDAO = salesItemDAO;
        this.saleDAO = saleDAO;
        this.userService = userService;
    }

    @Override
    public SalesItem getSalesItemById(Long id) {
        return salesItemDAO.findById(id);
    }

    @Override
    public List<SalesItem> getAllSalesItems() {
        return salesItemDAO.findAll();
    }

    @Override
    public List<SalesItem> getSalesItemsBySaleId(Long saleId) {
        Sale sale = saleDAO.findById(saleId);
        return sale.getSalesItems();
    }

    @Override
    @Transactional
    public SalesItem createSalesItem(SalesItem salesItem) {
        salesItem.setCreatedBy(userService.getLoggedInUsername());
        salesItem.setLastUpdateBy("-1");
        salesItem.setCreationDate(new Date());
        salesItem.setLastUpdateDate(new Date());
        salesItemDAO.save(salesItem);
        return salesItem;
    }

    @Override
    @Transactional
    public SalesItem updateSalesItem(SalesItem salesItem) {
        salesItem.setLastUpdateBy(userService.getLoggedInUsername());
        salesItem.setLastUpdateDate(new Date());
        salesItemDAO.save(salesItem);
        return salesItem;
    }

    @Override
    @Transactional
    public void deleteSalesItem(Long id) {
        salesItemDAO.deleteById(id);
    }
}
