package com.mysite.library.controller;


import com.mysite.library.entity.Faq;
import com.mysite.library.dto.FaqDTO;
import com.mysite.library.form.FaqForm;
import com.mysite.library.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class FaqController {

    @Autowired
    private FaqService faqService;

    @GetMapping("/faq")
    public String faq(Model model) {
        List<Faq> faqs = faqService.getAllFaqs();
        model.addAttribute("faq", faqs);
        return "faq/faq";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/faq/create")
    public String create(FaqForm faqForm) {
        return "faq/faq_form";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/faq/create")
    public String addNewFAQ(@ModelAttribute FaqForm faqForm) {
        System.out.println("폼 : " + faqForm);
        faqService.addNewFaq(new FaqDTO(faqForm.getSubject(), faqForm.getContent()));
        return "redirect:/faq";
    }


}
