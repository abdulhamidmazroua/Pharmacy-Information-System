package com.hameed.springboot.pharmacyms.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonMerge;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CATEGORY", schema = "pharmacy_directory")
public class Category extends AbstractEntity implements Serializable  {

    private static final long serialVersionUID = 1L;


    @Column(name = "code")
    private String categoryCode;

    @Column(name = "full_name")
    private String categoryName;

    @Column(name = "description")
    private String categoryDescription;

    // Self Join: referencing super category (target) as a child(owner of relation)
    @ManyToOne
    @JoinColumn(name = "super_category_code")
    @JsonBackReference
    private Category superCategory;

    // Self Join: referencing all subcategories (owners) as a parent (target)
    @OneToMany(mappedBy = "superCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Category> subCategories;

    // one-to-many relation with medication
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Medication> medications;


    public Category() {
    }

    public Category(String createdBy, String lastUpdateBy, String categoryCode, String categoryName, String categoryDescription, Category superCategory, List<Category> subCategories, List<Medication> medications) {
        super(createdBy, lastUpdateBy);
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.superCategory = superCategory;
        this.subCategories = subCategories;
        this.medications = medications;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Category getSuperCategory() {
        return superCategory;
    }

    public void setSuperCategory(Category superCategory) {
        this.superCategory = superCategory;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }
}
