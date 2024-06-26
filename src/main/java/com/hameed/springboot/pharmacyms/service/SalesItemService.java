package com.hameed.springboot.pharmacyms.service;

import com.hameed.springboot.pharmacyms.entity.SalesItem;

import java.util.List;

public interface SalesItemService {
    SalesItem getSalesItemById(Long id);
    List<SalesItem> getAllSalesItems();
    List<SalesItem> getSalesItemsBySaleId(Long saleId);
    SalesItem createSalesItem(SalesItem salesItem);
    SalesItem updateSalesItem(SalesItem salesItem);
    void deleteSalesItem(Long id);
}

