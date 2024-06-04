package com.mysite.library.service;

import com.mysite.library.entity.Question;
import com.mysite.library.entity.UserEntity;
import com.mysite.library.DataNotFoundException;
import com.mysite.library.repository.QuestionRepository;
import com.mysite.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository qRepo;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getQuestionsByUser(UserEntity author){
        return qRepo.findByAuthor(author);
    }

    public List<Question> findAll() {
        return qRepo.findAll();
    }

    public Question getQuestionById(Long id) {
        Optional<Question> q = qRepo.findById(id);
        if (q.isPresent()) {
            return q.get();
        } else {
            //id에 해당하는 질문을 못찾을 경우 에러를 발생하며 메세지 표시 , 404 상태코드
            throw new DataNotFoundException("question not found");
        }
    }

    public void createQuestion(String subject, String content, UserEntity name) {
        Question q = new Question();
        q.setQSubject(subject);
        q.setQContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setQName(name.getName());
        q.setAuthor(name); //글쓴이 추가
        qRepo.save(q);
    }


    //페이지에 맞는 질문들을 가져옴 (사이즈 10개)
    public Page<Question> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createDate").descending()); //페이지번호, 한페이지10개

        return qRepo.findAll(pageable);
    }

    //질문 수정하기
    public  void modify(Question question, String subject, String content) {
        question.setQSubject(subject);
        question.setQContent(content);
        question.setModifyDate(LocalDateTime.now());
        qRepo.save(question);
    }

    //질문 삭제하기
    public void delete(Question question) {
        qRepo.delete(question);
    }

    //이름으로 게시글 리스트
    public List<Question> findByAuthor(UserEntity author) {

        return qRepo.findByAuthor(author);
    }

    public List<Question> getQuestionsByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        return user != null ? user.getQuestions() : null;
    }


    public Page<Question> findByAuthor(UserEntity author, Pageable pageable) {
        return questionRepository.findByAuthor(author, pageable);
    }
}
