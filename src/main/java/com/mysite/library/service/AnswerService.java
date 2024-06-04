package com.mysite.library.service;

import com.mysite.library.repository.AnswerRepository;
import com.mysite.library.entity.Answer;
import com.mysite.library.DataNotFoundException;
import com.mysite.library.entity.Question;
import com.mysite.library.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository aRepo;

    public void create(Question question, String content, UserEntity author) {
       //답변의 부모가 질문이기 때문에 질문필요
        Answer answer = new Answer();
        answer.setAContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question); //질문 입력
        answer.setAuthor(author); //글쓴이 추가
        aRepo.save(answer);

    }

    //답변 조회
    public Answer getAnswer(int id) {
        Optional<Answer> answer = aRepo.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    //답변 수정
    public void modify(Answer answer, String content) {
        answer.setAContent(content);
        answer.setModifyDate(LocalDateTime.now());
        aRepo.save(answer);
    }

    public void delete(Answer answer) {
        aRepo.delete(answer);
    }

}
