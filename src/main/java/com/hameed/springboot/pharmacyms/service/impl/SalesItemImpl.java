package com.hameed.springboot.pharmacyms.service.impl;

import com.hameed.springboot.pharmacyms.dao.SaleDAO;
import com.hameed.springboot.pharmacyms.dao.SalesItemDAO;
import com.hameed.springboot.pharmacyms.entity.Sale;
import com.hameed.springboot.pharmacyms.entity.SalesItem;
import com.hameed.springboot.pharmacyms.service.SalesItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesItemImpl implements SalesItemService {

    private SalesItemDAO salesItemDAO;
    private SaleDAO saleDAO;

    @Autowired
    public SalesItemImpl(SalesItemDAO salesItemDAO, SaleDAO saleDAO) {
        this.salesItemDAO = salesItemDAO;
        this.saleDAO = saleDAO;
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
    public SalesItem createSalesItem(SalesItem salesItem) {
        salesItemDAO.save(salesItem);
        return salesItem;
    }

    @Override
    public SalesItem updateSalesItem(SalesItem salesItem) {
        salesItemDAO.save(salesItem);
        return salesItem;
    }

    @Override
    public void deleteSalesItem(Long id) {
        salesItemDAO.deleteById(id);
    }
}
