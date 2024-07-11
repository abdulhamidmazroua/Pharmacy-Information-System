package com.hameed.springboot.pharmacyms.controller;

import com.hameed.springboot.pharmacyms.model.entity.Sale;
import com.hameed.springboot.pharmacyms.service.MedicationService;
import com.hameed.springboot.pharmacyms.service.SaleService;
import com.hameed.springboot.pharmacyms.util.JsonUtil;
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

    private final SaleService saleService;

    private final MedicationService medicationService;

    private final JsonUtil jsonUtil;

    @Autowired
    public SalesController(SaleService saleService, MedicationService medicationService, JsonUtil jsonUtil) {
        this.saleService = saleService;
        this.medicationService = medicationService;
        this.jsonUtil = jsonUtil;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping
    public String showSales(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {

        model.addAttribute("sales", saleService.getAllSales());
        model.addAttribute("jsonUtil", jsonUtil);

        model.addAttribute("fragment", "/fragments/sales-frag");
        model.addAttribute("fragment_id", "sales-frag");
        return "XMLHttpRequest".equals(requestedWith) ? "/fragments/sales-frag" : "layout";
    }

    @GetMapping("/new-sale")
    public String newSale(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {

        model.addAttribute("sale", new Sale());
        model.addAttribute("medications", medicationService.getAllMedications());
        model.addAttribute("jsonUtil", jsonUtil);

        model.addAttribute("fragment", "/fragments/new-sale-frag");
        model.addAttribute("fragment_id", "new-sale-frag");
        return "XMLHttpRequest".equals(requestedWith) ? "/fragments/new-sale-frag" : "layout";
    }

    @PostMapping("/new-sale/add")
    public String addSale(@ModelAttribute("sale") Sale sale, @RequestHeader(value = "X-Requested-With", required = true) String requestedWith) {
        if (!"XMLHttpRequest".equals(requestedWith)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
        }


        saleService.createSale(sale);
        return "redirect:/sales";
    }

    @PostMapping("/update")
    public String updateSale(@ModelAttribute("medication") Sale sale, @RequestHeader(value = "X-Requested-With", required = true) String requestedWith) {
        if (!"XMLHttpRequest".equals(requestedWith)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
        }
        saleService.updateSale(sale);
        return "redirect:/sales";
    }

    @PostMapping("/{id}")
    public String deleteSale(@PathVariable Long id, @RequestHeader(value = "X-Requested-With", required = true) String requestedWith) {
        if (!"XMLHttpRequest".equals(requestedWith)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
        }
        saleService.deleteSale(id);
        return "redirect:/sales";
    }


    // this will be used later in the above showSales endpoint
//    @GetMapping("/page")
//    public String showSalesPage(@RequestParam("page") int page, @RequestParam("size") int size, Model model) {
//        Pageable pageable = (Pageable) PageRequest.of(page, size);
//        model.addAttribute("sales", salesService.getAllSales(pageable));
//        return "sales-page";
//    }

}
