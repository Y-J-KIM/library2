package com.mysite.library.entity;

import com.mysite.library.dto.QuestionDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qIdx;         //질문번호

    @Column(length = 200)
    private String qSubject;     //제목

    @Column(columnDefinition = "TEXT")
    private String qContent;     //내용

    private LocalDateTime createDate; //작성일자

    private String qName;

    //답변 리스트
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserEntity author;
    //글쓴이 한명이 질문 여러개 작성가능하기 때문에 @ManyToOne 관계 성립

    private LocalDateTime modifyDate; //수정일자

    public static Question toQuestion(QuestionDTO questionDTO, UserEntity author){
            Question question = new Question();

        question.setQSubject(questionDTO.getQSubject());
        question.setQContent(questionDTO.getQContent());
        question.setCreateDate(LocalDateTime.now());
        question.setQName(questionDTO.getName());
        question.setAuthor(author);
        question.setModifyDate(LocalDateTime.now()); // 처음 생성 시 수정일자도 현재 시간으로 설정

            return question;
    }

}
