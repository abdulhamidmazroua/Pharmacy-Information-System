package com.hameed.springboot.pharmacyms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class NavigationController {

    @GetMapping("dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("fragment", "/fragments/dashboard");
        return "layout";
    }

    @GetMapping("medications")
    public String showMedications(Model model) {
        model.addAttribute("fragment", "fragments/medications");
        return "layout";
    }

    @GetMapping("sales")
    public String showSales(Model model) {
        model.addAttribute("fragment", "/fragments/sales");
        return "layout";
    }

    @GetMapping("reports")
    public String showReports(Model model) {
        model.addAttribute("fragment", "/fragments/reports");
        return "layout";
    }

    @GetMapping("settings")
    public String showSettings(Model model) {
        model.addAttribute("fragment", "/fragments/settings");
        return "layout";
    }

    @GetMapping("/content/dashboard")
    public String showContentDashboard() {
        return "/fragments/dashboard";
    }

    @GetMapping("/content/medications")
    public String showContentMedications() {
        return "/fragments/medications";
    }

    @GetMapping("/content/sales")
    public String showContentSales() {
        return "/fragments/sales";
    }

    @GetMapping("/content/reports")
    public String showContentReports() {
        return "/fragments/reports";
    }

    @GetMapping("/content/settings")
    public String showContentSettings() {
        return "/fragments/settings";
    }
}
