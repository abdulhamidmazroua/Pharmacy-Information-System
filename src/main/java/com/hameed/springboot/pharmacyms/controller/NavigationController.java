package com.hameed.springboot.pharmacyms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/")
public class NavigationController {

    @GetMapping("/dashboard/")
    public String showContentDashboard() {
        return "/fragments/dashboard-frag";
    }

    @GetMapping("/medications/")
    public String showContentMedications() {
        return "/fragments/medications-frag";
    }

    @GetMapping("/sales/")
    public String showContentSales() {
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
    public String newSale() {
        return "/fragments/new-sale-frag";
    }

}
