package com.hameed.springboot.pharmacyms.model.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "MEDICATION", schema = "pharmacy_directory")
public class Medication extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String medicationName;

    @Column(name = "description")
    private String description;

    // references primary unit of measure
    @ManyToOne
    @JoinColumn(name = "primary_uom_code", referencedColumnName = "code")
    private UnitOfMeasure primaryUom;

    // references category many to many
    @ManyToMany
    @JoinTable(
            name = "MEDICATIONS_CATEGORIES",
            joinColumns = {@JoinColumn(name = "medication_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private Set<Category> categories;


    @Column(name = "exp_date")
    private Date expDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "dosage_strength")
    private Integer dosageStrength;

    public Medication() {
    }

    public Medication(String createdBy, String lastUpdateBy, String description, String medicationName, UnitOfMeasure primaryUom, Set<Category> categories, Date expDate, Double price, Integer quantity, Integer dosageStrength) {
        super(createdBy, lastUpdateBy);
        this.description = description;
        this.medicationName = medicationName;
        this.primaryUom = primaryUom;
        this.categories = categories;
        this.expDate = expDate;
        this.price = price;
        this.quantity = quantity;
        this.dosageStrength = dosageStrength;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UnitOfMeasure getPrimaryUom() {
        return primaryUom;
    }

    public void setPrimaryUom(UnitOfMeasure primaryUom) {
        this.primaryUom = primaryUom;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDosageStrength() {
        return dosageStrength;
    }

    public void setDosageStrength(Integer dosageStrength) {
        this.dosageStrength = dosageStrength;
    }
}
