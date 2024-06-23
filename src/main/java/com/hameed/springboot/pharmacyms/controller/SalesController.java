package com.hameed.springboot.pharmacyms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @GetMapping("/")
    public String showSales(Model model) {
        model.addAttribute("fragment", "/fragments/sales-frag");
        return "layout";
    }

    @GetMapping("/new-sale")
    public String newSale(Model model) {
        model.addAttribute("fragment", "/fragments/new-sale-frag");
        return "layout";
    }


}
