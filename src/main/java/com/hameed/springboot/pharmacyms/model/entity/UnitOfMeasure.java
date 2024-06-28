package com.hameed.springboot.pharmacyms.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "UNIT_OF_MEASURES", schema = "pharmacy_directory")
public class UnitOfMeasure extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "code")
    private String uomCode;

    @Column(name = "full_name")
    private String fullName;

    public UnitOfMeasure() {
    }

    public UnitOfMeasure(String createdBy, String lastUpdateBy, String fullName, String uomCode) {
        super(createdBy, lastUpdateBy);
        this.fullName = fullName;
        this.uomCode = uomCode;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
