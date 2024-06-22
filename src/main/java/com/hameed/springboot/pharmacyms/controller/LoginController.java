package com.hameed.springboot.pharmacyms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/showLogin")
    public String showLogin() {
        return "login";
    }

}
