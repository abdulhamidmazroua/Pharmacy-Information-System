package com.hameed.springboot.pharmacyms.controller;

import com.hameed.springboot.pharmacyms.model.entity.Medication;
import com.hameed.springboot.pharmacyms.model.entity.Sale;
import com.hameed.springboot.pharmacyms.model.entity.SalesItem;
import com.hameed.springboot.pharmacyms.service.SaleService;
import com.hameed.springboot.pharmacyms.service.SalesItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/sales")
public class SalesController {

    private SaleService saleService;

    private SalesItemService salesItemService;

    @Autowired
    public SalesController(SaleService saleService, SalesItemService salesItemService) {
        this.saleService = saleService;
        this.salesItemService = salesItemService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping
    public String showSales(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {

        model.addAttribute("sales", saleService.getAllSales());

        model.addAttribute("fragment", "/fragments/sales-frag");
        model.addAttribute("fragment_id", "sales-frag");
        return "XMLHttpRequest".equals(requestedWith) ? "/fragments/sales-frag" : "layout";
    }

    @GetMapping("/new-sale")
    public String newSale(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {

        model.addAttribute("sale", new Sale());

        model.addAttribute("fragment", "/fragments/new-sale-frag");
        model.addAttribute("fragment_id", "new-sale-frag");
        return "XMLHttpRequest".equals(requestedWith) ? "/fragments/new-sale-frag" : "layout";
    }

    @PostMapping("/new-sale/add")
    public String addMedication(@ModelAttribute("sale") Sale sale, @RequestHeader(value = "X-Requested-With", required = true) String requestedWith) {
        if (!"XMLHttpRequest".equals(requestedWith)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
        }
        saleService.createSale(sale);
        return "redirect:/sales";
    }

    @PostMapping("/update")
    public String updateMedication(@ModelAttribute("medication") Sale sale, @RequestHeader(value = "X-Requested-With", required = true) String requestedWith) {
        if (!"XMLHttpRequest".equals(requestedWith)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
        }
        saleService.updateSale(sale);
        return "redirect:/sales";
    }

    @PostMapping("/{id}")
    public String deleteMedication(@PathVariable Long id, @RequestHeader(value = "X-Requested-With", required = true) String requestedWith) {
        if (!"XMLHttpRequest".equals(requestedWith)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
        }
        saleService.deleteSale(id);
        return "redirect:/sales";
    }

}
