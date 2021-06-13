package com.example.demo.controller;

import com.example.demo.model.Approval;
import com.example.demo.model.Enquiry;
import com.example.demo.repository.EnquiryRepository;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class AppController {

    private EnquiryRepository enquiryRepository;

    public AppController(EnquiryRepository enquiryRepository) {
        this.enquiryRepository = enquiryRepository;
    }

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("enquiries",enquiryRepository.findAll());
        return "home";
    }

    @GetMapping("/enquiry")
    public String enquiryForm(Model model){
        model.addAttribute("enquiry", new Enquiry());
        return "enquiry";
    }

    @PostMapping("/enquiry")
    public String enquirySubmit(Authentication authentication, @ModelAttribute Enquiry enquiry, BindingResult result, Model model,RedirectAttributes redirectAttrs){
        if (result.hasErrors()) {
            return "errors/enquiry";
        }
        if (enquiry.getInterestRate() > 16){
            Approval approval = new Approval();
            approval.setApprovedUser(authentication.getName());
            approval.setApprovalStatus(Boolean.TRUE);
            enquiry.setApproval(approval);
        }
        Enquiry savedEnquiry = enquiryRepository.save(enquiry);
        redirectAttrs.addAttribute("success","Enquiry "+savedEnquiry.getId()+" created");
        return "redirect:/enquiry";
    }

    @GetMapping("/enquiry/{id}")
    public String enquiryForm(@PathVariable Long id, Model model){
        Optional<Enquiry> enquiry = enquiryRepository.findById(id);
        if (enquiry.isPresent() && enquiry.get().getApproval().getApprovalStatus()){
            model.addAttribute("readonly",Boolean.TRUE);
        }else {
            model.addAttribute("readonly",Boolean.FALSE);
        }
        model.addAttribute("enquiry", enquiry);
        return "enquiry";
    }

    @PostMapping("/enquiry/recommend/{id}")
    public String recommend(@PathVariable Long id, Authentication authentication, RedirectAttributes redirectAttrs){
        if (Optional.ofNullable(id).isPresent()){
            Enquiry enquiry = enquiryRepository.findById(id).get();
            if (enquiry.getInterestRate() >= 14 && enquiry.getInterestRate() <= 16 && authentication.getName().equals("role2")){
                redirectAttrs.addAttribute("success","Recommendation not required for enquiry : "+id +" can be approved by role2");
            }
            if ((enquiry.getInterestRate() > 12 && enquiry.getInterestRate() < 14 && authentication.getName().equals("role2"))
            || (enquiry.getInterestRate() <= 12 && List.of("role2","role3").contains(authentication.getName()))){
            Approval approval = new Approval();
            approval.setRecommendedUser(authentication.getName());
            approval.setApprovalStatus(Boolean.FALSE);
            enquiry.setApproval(approval);
            enquiryRepository.save(enquiry);
            redirectAttrs.addAttribute("success","Recommendation successful for enquiry : "+id);
            }
        }else {
            redirectAttrs.addAttribute("error","Invalid Request");
        }
        return "redirect:/home";
    }

    @PostMapping("/enquiry/approval/{id}")
    public String approval(@PathVariable Long id,Authentication authentication,RedirectAttributes redirectAttrs){
        if (Optional.ofNullable(id).isPresent()){
            Enquiry enquiry = enquiryRepository.findById(id).get();
            if ((enquiry.getInterestRate() >= 14 && enquiry.getInterestRate() <= 16 && authentication.getName().equals("role2"))
                    ||(enquiry.getInterestRate() > 12 && enquiry.getInterestRate() < 14 && authentication.getName().equals("role3"))
                    || (enquiry.getInterestRate() <= 12 && authentication.getName().equals("role4"))
            ) {
                Approval approval = Optional.ofNullable(enquiry.getApproval()).orElse(new Approval());
                approval.setApprovedUser(authentication.getName());
                approval.setApprovalStatus(Boolean.TRUE);
                enquiry.setApproval(approval);
                enquiryRepository.save(enquiry);
                redirectAttrs.addAttribute("success", "Approval successful for enquiry : " + id);
            }else {
                redirectAttrs.addAttribute("error","User :"+authentication.getName()+" not allowed tp approve enquiry :"+id);
            }
        }else {
            redirectAttrs.addAttribute("error","Invalid Request");
        }
        return "redirect:/home";
    }
}
