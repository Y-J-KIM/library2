package com.mysite.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aIdx;

    @Column(columnDefinition = "TEXT")
    private String aContent;

    private LocalDateTime createDate;

    private String aName;

    //질문을 참조(외래키)
    @ManyToOne
    private Question question; //질문 하나에 답변 여러개 관계

    @ManyToOne
    private UserEntity author;
    //글쓴이 한명이 답변 여러개 작성가능하기 때문에 @ManyToOne 관계 성립

    private LocalDateTime modifyDate; //수정일자
}
