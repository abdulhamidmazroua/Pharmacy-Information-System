package com.hameed.springboot.pharmacyms.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "sales_header", schema = "pharmacy_directory")
public class Sale extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "total_amount")
    private String totalAmount;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SalesItem> salesItems;

    public Sale() {
    }

    public Sale(String createdBy, String lastUpdateBy, String customerName, String totalAmount) {
        super(createdBy, lastUpdateBy);
        this.customerName = customerName;
        this.totalAmount = totalAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<SalesItem> getSalesItems() {
        return salesItems;
    }

    public void setSalesItems(List<SalesItem> salesItems) {
        this.salesItems = salesItems;
    }
}
