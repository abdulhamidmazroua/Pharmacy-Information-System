package com.hameed.springboot.pharmacyms.controller;

import com.hameed.springboot.pharmacyms.model.entity.Medication;
import com.hameed.springboot.pharmacyms.service.CategoryService;
import com.hameed.springboot.pharmacyms.service.MedicationService;
import com.hameed.springboot.pharmacyms.service.UnitOfMeasureService;
import com.hameed.springboot.pharmacyms.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/medications")
public class MedicationsController {

    private MedicationService medicationService;
    private CategoryService categoryService;
    private UnitOfMeasureService unitOfMeasureService;
    private UserService userService;

    @Autowired
    public MedicationsController(MedicationService medicationService,
                                 CategoryService categoryService,
                                 UnitOfMeasureService unitOfMeasureService,
                                 UserService userService) {
        this.medicationService = medicationService;
        this.categoryService = categoryService;
        this.unitOfMeasureService = unitOfMeasureService;
        this.userService = userService;
    }

    // @InitBinder works as a pre-processor
    // It will pre-process each web request to our controller
    // Here, we will use it to trim strings,
    // removing all leading and trailing white space
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/")
    public String showAllMedications(Model model) {
        model.addAttribute("medication", new Medication());
        model.addAttribute("medications", medicationService.getAllMedications());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("unitOfMeasures", unitOfMeasureService.getAllUnitsOfMeasures());
        model.addAttribute("fragment", "/fragments/medications-frag");
        return "layout";
    }

    @GetMapping("/{id}")
    public String populateMedication(@PathVariable long id, Model model) {
        Medication medication = medicationService.getMedicationById(id);
        model.addAttribute("medication", medication);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("unitOfMeasures", unitOfMeasureService.getAllUnitsOfMeasures());
        return "layout";
    }

    @PostMapping("/add")
    public String addMedication(@ModelAttribute("medication") Medication medication) {
        medicationService.createMedication(medication);
        return "redirect:/medications/";
    }

    @PostMapping("/update")
    public String updateMedication(@ModelAttribute("medication") Medication medication) {
        medicationService.updateMedication(medication);
        return "redirect:/medications";
    }

    @DeleteMapping("/{id}")
    public String deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
        return "redirect:/medications";
    }

}
