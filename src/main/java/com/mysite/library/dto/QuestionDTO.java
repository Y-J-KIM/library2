package com.mysite.library.dto;

import com.mysite.library.entity.Question;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {

    private Long qIdx;
    private String qSubject;
    private String qContent;
    private LocalDateTime createDate; // 작성일자
    private LocalDateTime modifyDate; // 수정일자
    private String name;
    private Long authorId; // 작성자 ID

    public static QuestionDTO toQuestionDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();

        questionDTO.setQIdx(question.getQIdx());
        questionDTO.setQSubject(question.getQSubject());
        questionDTO.setQContent(question.getQContent());
        questionDTO.setCreateDate(question.getCreateDate());
        questionDTO.setModifyDate(question.getModifyDate());
        questionDTO.setName(question.getQName());
        questionDTO.setAuthorId(question.getAuthor().getIdx());

        return questionDTO;
    }
}

