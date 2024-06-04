package com.mysite.library.controller;

import com.mysite.library.entity.Answer;
import com.mysite.library.form.AnswerForm;
import com.mysite.library.service.AnswerService;
import com.mysite.library.entity.Question;
import com.mysite.library.service.QuestionService;
import com.mysite.library.entity.UserEntity;
import com.mysite.library.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private QuestionService qService;
    @Autowired
    private AnswerService aService;
    @Autowired
    private UserService uService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(@PathVariable Long id, Model model,
                               @Valid AnswerForm answerForm, BindingResult result,
                               Principal principal) {
        Question question = qService.getQuestionById(id);
        UserEntity user = uService.findByName(principal.getName());
        if(result.hasErrors()) {
            model.addAttribute("question", question);
            return "question/question_detail"; //질문상세페이지로 돌아감
        }
        //답변저장하기
        aService.create(question, answerForm.getContent(), user);
        return String.format("redirect:/question/detail/%d", id);
    }

    //수정 페이지 보이기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable int id,
                               Principal principal) {
        Answer answer = aService.getAnswer(id);
        UserEntity user = uService.findByName(principal.getName());

        if (!answer.getAuthor().getName().equals(user.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.getAContent());
        return "question/answer_form";
    }


    //수정하기
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question/answer_form";
        }
        Answer answer = this.aService.getAnswer(id);
        UserEntity user = uService.findByName(principal.getName());

        if (!answer.getAuthor().getName().equals(user.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.aService.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getQIdx());
    }

    //삭제하기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
        Answer answer = this.aService.getAnswer(id);
        UserEntity user = uService.findByName(principal.getName());

        if (!answer.getAuthor().getName().equals(user.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.aService.delete(answer);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getQIdx());
    }

//    //관리자만 답글 달기
//    @PostMapping("/create/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String submitReply(@RequestParam String answer, Model model){
//        return "redirect:/question/detail/%s";
//    }
}
