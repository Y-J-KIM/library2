package com.mysite.library.repository;

import com.mysite.library.entity.Question;
import com.mysite.library.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
//    Question findByQ_subject(String subject);
    //Question findByContent(String content);
    //Question findByQ_subjectAndQ_content(String subject, String content);
    //질문제목에 해당하는 문자열 있을경우 모두 가져옴
    //List<Question> findByQ_subjectLike(String subject);
    //페이징 리스트 (페이지,사이즈등이 입력된 pageable 객체 입력)
    Page<Question> findAll(Pageable pageable);

    List<Question> findByAuthor(UserEntity author);

    Page<Question> findByAuthor(UserEntity author, Pageable pageable);

}
