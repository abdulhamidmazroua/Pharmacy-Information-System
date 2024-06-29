package com.hameed.springboot.pharmacyms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @GetMapping
    public String showSales(Model model, @RequestHeader(value = "X-Requested-With", required = true) String requestedWith) {
        model.addAttribute("fragment", "/fragments/sales-frag");
        return "XMLHttpRequest".equals(requestedWith) ? "/fragments/sales-frag" : "layout";
    }

    @GetMapping("/new-sale")
    public String newSale(Model model, @RequestHeader(value = "X-Requested-With", required = true) String requestedWith) {
        model.addAttribute("fragment", "/fragments/new-sale-frag");
        return "XMLHttpRequest".equals(requestedWith) ? "/fragments/new-sale-frag" : "layout";
    }


}
