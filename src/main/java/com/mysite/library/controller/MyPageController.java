package com.mysite.library.controller;

import com.mysite.library.dto.RentDTO;
import com.mysite.library.dto.ReservationDTO;
import com.mysite.library.dto.UserDTO;
import com.mysite.library.service.RentService;
import com.mysite.library.service.ReservationService;
import com.mysite.library.service.UserService_;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
//마이페이지: 회원정보 수정, 회원정보 삭제(계정탈퇴), 회원도서대여 현황 확인
public class MyPageController {

    private final UserService_ userService_;
    private final RentService rentService;
    private final ReservationService reservationService;

    @GetMapping("/mypage")
    public String myPage() {
        return "/mypage/mypage";
    }

    //update: 수정 페이지 리턴
    @GetMapping("/update/{idx}")
    public String updateForm(@PathVariable("idx") Long idx, Model model){
        UserDTO userDTO = userService_.findById(idx);
        if (userDTO != null) {
            model.addAttribute("userDTO", userDTO);
            return "/user/update";
        } else {
            model.addAttribute("error", "회원정보를 찾을 수 없습니다");
            return "error";
        }
    }

    //update: 수정된 정보 전송
    @PostMapping("/update")
    public String update(@ModelAttribute UserDTO userDTO, Model model) {
        UserDTO updateUser = userService_.update(userDTO);
        System.out.println(updateUser+ "유저");
        if (updateUser != null) {
            model.addAttribute("user", updateUser);
            model.addAttribute("message", "업데이트 성공했습니다 !");
            return "/main";
        } else {
            model.addAttribute("error", "업데이트 오류. 수정을 실패했습니다.");
            return "/user/update";
        }
    }

    // borrow
    @GetMapping("/rent-list")
    public String rentBook(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        List<RentDTO> rents = rentService.getRentInfo(name);
        model.addAttribute("rents", rents);
        return "mypage/mypage-rent-list";
    }

    // reserve
    @GetMapping("/reservation-list")
    public String reserveBook(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        List<ReservationDTO> rsvs = reservationService.getRsvInfo(name);
        model.addAttribute("rsvs", rsvs);
        return "mypage/mypage-rsv-list";
    }

    //delete
    @GetMapping("/user/delete")
    public String delete(Principal principal, Model model) {
        String name = principal.getName();
        System.out.println(name);
        UserDTO user = userService_.findByName(name);
        System.out.println("유저: "+user);
        model.addAttribute("user", user);
        System.out.println("유저번호:" + user.getIdx());
        return "/user/delete";
    }

    //delete
    @PostMapping("/user/delete")
    public String deleteById(@ModelAttribute("user") UserDTO userDTO) {
        System.out.println(userDTO);
        userService_.deleteById(userDTO.getIdx());

        return "redirect:/user/logout";
    }
}