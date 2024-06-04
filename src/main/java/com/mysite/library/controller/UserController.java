package com.mysite.library.controller;

import com.mysite.library.dto.UserDTO;
import com.mysite.library.entity.Question;
import com.mysite.library.entity.UserEntity;
import com.mysite.library.form.UserCreateForm;
import com.mysite.library.service.QuestionService;
import com.mysite.library.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    // 생성자 주입
    private final UserService userService;

    private final QuestionService questionService;

    // 회원가입 페이지 출력
    @GetMapping(value = {"/user/register","/register"})
    public String registerForm(Model model) {
        model.addAttribute("userCreateForm", new UserCreateForm());
        return "user/register";
    }

    // %%%%% object랑 field랑 th: 넣기 ! %%%%%
    @PostMapping(value = {"/user/register","/register"})
    // @RequestParam은 "userEmail"의 값을 String userEmail에 대입시키는 역할을 한다
    // @ModelAttribute는 @RequestParam처럼 하나씩 불러오지 않고 한번에 매개변수를 연결해준다
    public String register(@Valid @ModelAttribute("userCreateForm") UserCreateForm userCreateForm,
                           BindingResult bindingResult,
                           Model model) {

        System.out.println("PostMapping register page");
        System.out.println("userDTO = " + userCreateForm);

        if (bindingResult.hasErrors()) {
            return "user/register";
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setName(userCreateForm.getName());
        userDTO.setEmail(userCreateForm.getEmail());
        userDTO.setPassword(userCreateForm.getPassword1());
        userDTO.setPhone(userCreateForm.getPhone());
        userDTO.setAddress(userCreateForm.getAddress());
        userService.save(userDTO);

        model.addAttribute("success", true);
        return "user/login";
    }

    @GetMapping(value = {"/user/login","/login"})
    public String loginForm(Principal principal) {
        System.out.println("GetMapping login page");

        if(principal == null) {
            return "user/login";
        }
        return "main";
    }

//    @PostMapping(value = {"/user/login","/login"})
//    public String login(@ModelAttribute UserDTO userDTO, HttpSession session) {
//        System.out.println("PostMapping login page");
//        // loginResult라는 변수를 만든 이유는 로그인이 성공했을때만 값을 넣는 변수를 만들기 위해서이다
//        UserDTO loginResult = userService.login(userDTO);
//        if (loginResult != null) {
//            // 로그인 성공
//            // "loginEmail"이라는 파라미터에 loginResult.getEmail()의 값을 넣는것.
//            session.setAttribute("loginEmail", loginResult.getEmail());
//            return "main";
//        } else {
//            // 로그인 실패
//            return "fail404";
//        }
//    }

    @GetMapping("/admin/user")
    public String listUsers(Model model) {
        List<UserDTO> users = userService.getAllUsers();
        model.addAttribute("users",users);
        return "admin/admin_user";
    }

    @GetMapping("/user/{userId}/posts")
    public List<Question> getUserPosts(@PathVariable Long userId) {
        return questionService.getQuestionsByUserId(userId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/mypage")
    public String myPage(Model model, Principal principal) {
        UserEntity user = userService.findByName(principal.getName());
        model.addAttribute("userID", user.getIdx());
        model.addAttribute("name", principal.getName());
        return "mypage/mypage";
    }

    //logout
    @GetMapping("/user/logout")
    public String logoutPage() {
        return "/";
    }

}
