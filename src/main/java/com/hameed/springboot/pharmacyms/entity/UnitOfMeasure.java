package com.hameed.springboot.pharmacyms.entity;

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

    @Override
    public String toString() {
        return "UnitOfMeasure{" +
                "uomCode='" + uomCode + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
