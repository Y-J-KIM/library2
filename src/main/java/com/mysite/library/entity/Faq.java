package com.mysite.library.entity;

import com.mysite.library.dto.FaqDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String fSubject;     //제목

    @Column(columnDefinition = "TEXT")
    private String fContent;     //내용

//    @ManyToOne
//    private UserEntity author;

    public static Faq toFaq(FaqDTO faqDTO){
        Faq faq = new Faq();

        faq.setFSubject(faqDTO.getFSubject());
        faq.setFContent(faqDTO.getFContent());
        return faq;
    }

//
}
