package com.hameed.springboot.pharmacyms.model.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "sales_item", schema = "pharmacy_directory")
public class SalesItem extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // reference to sale header
    @ManyToOne
    @JoinColumn(name = "sales_id")
    private Sale sale;

    // reference to medication
    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication medication;


    @Column(name = "quantity")
    private Integer quantity;

    // remember that unit price here is provided because it is not always
    // sold to the customer with the same original price of the medication
    // a discount might be applied
    // Thus, in UI we will update the unit_price directly to be the same
    // as the original medication price by default allowing the seller
    // to possibly change the actual price of the sold unit
    @Column(name = "unit_price")
    private Double unitPrice;

    public SalesItem() {
    }

    public SalesItem(String createdBy, String lastUpdateBy, Double unitPrice, Integer quantity, Medication medication, Sale sale) {
        super(createdBy, lastUpdateBy);
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.medication = medication;
        this.sale = sale;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

}
