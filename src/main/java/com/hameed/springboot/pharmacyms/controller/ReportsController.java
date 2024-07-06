package com.hameed.springboot.pharmacyms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportsController {

    @GetMapping
    public String showReports(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        model.addAttribute("fragment", "/fragments/reports-frag");
        model.addAttribute("fragment_id", "reports-frag");
        return "XMLHttpRequest".equals(requestedWith) ? "/fragments/reports-frag" : "layout";
    }
}
