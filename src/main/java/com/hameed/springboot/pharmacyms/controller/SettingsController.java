package com.hameed.springboot.pharmacyms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings")
public class SettingsController {

    @GetMapping
    public String showSettings(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        model.addAttribute("fragment", "/fragments/settings-frag");
        model.addAttribute("fragment_id", "settings-frag");
        return "XMLHttpRequest".equals(requestedWith) ? "/fragments/settings-frag" : "layout";
    }
}
