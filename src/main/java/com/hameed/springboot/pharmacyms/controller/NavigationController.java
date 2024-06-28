package com.hameed.springboot.pharmacyms.controller;

import com.hameed.springboot.pharmacyms.model.entity.Medication;
import com.hameed.springboot.pharmacyms.model.entity.Sale;
import com.hameed.springboot.pharmacyms.service.CategoryService;
import com.hameed.springboot.pharmacyms.service.MedicationService;
import com.hameed.springboot.pharmacyms.service.UnitOfMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/")
public class NavigationController {

    private MedicationService medicationService;
    private CategoryService categoryService;
    private UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public NavigationController(MedicationService medicationService, CategoryService categoryService, UnitOfMeasureService unitOfMeasureService) {
        this.medicationService = medicationService;
        this.categoryService = categoryService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/dashboard/")
    public String showContentDashboard() {
        return "/fragments/dashboard-frag";
    }

    @GetMapping("/medications/")
    public String showContentMedications(Model model) {
        model.addAttribute("medication", new Medication());
        model.addAttribute("medications", medicationService.getAllMedications());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("unitOfMeasures", unitOfMeasureService.getAllUnitsOfMeasures());
        return "/fragments/medications-frag";
    }

    @GetMapping("/sales/")
    public String showContentSales(Model model) {
        return "/fragments/sales-frag";
    }

    @GetMapping("/reports/")
    public String showContentReports() {
        return "/fragments/reports-frag";
    }

    @GetMapping("/settings/")
    public String showContentSettings() {
        return "/fragments/settings-frag";
    }

    @GetMapping("/new-sale/")
    public String newSale(Model model) {
        model.addAttribute("sale", new Sale());
        return "/fragments/new-sale-frag";
    }

}
