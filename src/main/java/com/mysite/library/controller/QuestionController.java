package com.mysite.library.controller;

import com.mysite.library.form.AnswerForm;
import com.mysite.library.entity.Question;
import com.mysite.library.form.QuestionForm;
import com.mysite.library.service.QuestionService;
import com.mysite.library.entity.UserEntity;
import com.mysite.library.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService qService;
    @Autowired
    private UserService uService;
    @Autowired
    private QuestionService questionService;


    @RequestMapping("/list")
    public String questionList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Question> paging = qService.getList(page);
        model.addAttribute("paging", paging);
        return "question/question_list";
    }

    @GetMapping("/question/list")
    public String getQuestionList(Model model) {
        List<Question> questions = qService.findAll();
        model.addAttribute("questions", questions);
        return "question/question_list";
    }

    //url 주소에 /{변수} => PathVariable 주소변수
    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id,
                         AnswerForm answerForm, Principal principal) {
        Question q = qService.getQuestionById(id);
        model.addAttribute("question", q);
        return "question/question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(QuestionForm questionForm) {

        return "question/question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@Valid QuestionForm questionForm,
                         BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "question/question_form"; //되돌아감
        }
        UserEntity user = uService.findByName(principal.getName());
        //질문 저장하기
        qService.createQuestion(questionForm.getSubject(), questionForm.getContent(), user);
        return "redirect:/question/list";
    }

    //수정하기 페이지 보이기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(QuestionForm questionForm, @PathVariable Long id,
                         Principal principal) {
        Question question = qService.getQuestionById(id);


        if (!question.getAuthor().getName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        questionForm.setSubject(question.getQSubject());
        questionForm.setContent(question.getQContent());
        return "question/question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@Valid QuestionForm questionForm, BindingResult result,
                         @PathVariable Long id, Principal principal) {
        if (result.hasErrors()) {
            return "question/question_form"; //되돌리기
        }
        Question question = qService.getQuestionById(id);

        if (!question.getAuthor().getName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        qService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%d", id);
    }

    //삭제하기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Principal principal) {

        Question question = qService.getQuestionById(id);
        UserEntity user = uService.findByName(principal.getName());

        if (!question.getAuthor().getName().equals(user.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        qService.delete(question);
        return "redirect:/question/list";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/mypage/question")
    public String getMyQuestions(Model model, Authentication authentication, @RequestParam(value = "page", defaultValue = "0") int page) {
        String username = authentication.getName();
        UserEntity user = uService.findByName(username);

        Pageable pageable = PageRequest.of(page, 10);
        Page<Question> paging = qService.findByAuthor(user, pageable);
        model.addAttribute("paging", paging);
        return "mypage/mypage_question";
    }
}
