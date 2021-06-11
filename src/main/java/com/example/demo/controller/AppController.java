package com.example.demo.controller;

import com.example.demo.model.Enquiry;
import com.example.demo.repository.EnquiryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {

    private EnquiryRepository enquiryRepository;

    public AppController(EnquiryRepository enquiryRepository) {
        this.enquiryRepository = enquiryRepository;
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/enquiry")
    public String enquiryForm(Model model){
        model.addAttribute("enquiry", new Enquiry());
        return "enquiry";
    }

    @PostMapping("/enquiry")
    public String enquirySubmit(@ModelAttribute Enquiry enquiry, Model model){
        enquiryRepository.save(enquiry);
        return "enquiry";
    }

    @GetMapping("/enquiry/{id}")
    public String enquiryForm(@PathVariable Long id, Model model){
        model.addAttribute("enquiry", enquiryRepository.findById(id));
        return "enquiry";
    }
}
