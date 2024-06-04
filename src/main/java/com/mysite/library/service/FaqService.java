package com.mysite.library.service;

import com.mysite.library.dto.FaqDTO;
import com.mysite.library.entity.Faq;
import com.mysite.library.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqService {

    @Autowired
    private FaqRepository faqRepository;

    public List<Faq> findAll() {

        return faqRepository.findAll();
    }

    public List<Faq> getAllFaqs() {
        return faqRepository.findAll();
    }

    public FaqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public void addNewFaq(FaqDTO faqDTO) {
        Faq newFaq = Faq.toFaq(faqDTO);
        newFaq.setFSubject(faqDTO.getFSubject());
        newFaq.setFContent(faqDTO.getFContent());
        faqRepository.save(newFaq);
    }

}
