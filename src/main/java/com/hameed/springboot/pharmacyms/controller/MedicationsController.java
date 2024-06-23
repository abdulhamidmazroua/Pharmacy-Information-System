package com.hameed.springboot.pharmacyms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medications")
public class MedicationsController {

    @GetMapping("/")
    public String showMedications(Model model) {
        model.addAttribute("fragment", "/fragments/medications-frag");
        return "layout";
    }


}
