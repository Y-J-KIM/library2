package com.mysite.library.dto;

import com.mysite.library.entity.Faq;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FaqDTO {

    private String fSubject;
    private String fContent;

    public static FaqDTO toFaqDTO(Faq faq) {
        FaqDTO faqDTO = new FaqDTO();

        faqDTO.setFSubject(faq.getFSubject());
        faqDTO.setFContent(faq.getFContent());
        return faqDTO;
    }
}
