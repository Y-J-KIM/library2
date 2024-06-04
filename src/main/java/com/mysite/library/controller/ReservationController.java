package com.mysite.library.controller;


import com.mysite.library.dto.ReservationDTO;
import com.mysite.library.entity.UserEntity;
import com.mysite.library.repository.UserRepository;
import com.mysite.library.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final UserRepository userRepository;

    //회원이 예약 버튼 누르면 관리자 페이지로 예약 내역 전달 a로 걸려있기 때문에 GET메소드
    @GetMapping("/reserve")
    public String reserveBook(Principal principal,
                              @RequestParam("rsvBookIsbn") String bookIsbn,
                              Model model){
        //System.out.println(bookIsbn);
        UserEntity user = new UserEntity();
//        if(principal==null){
//            user = userRepository.findById(1).orElseThrow(()->new RuntimeException("Default user not found"));
//        } else {
            user = userRepository.findByEmail(principal.getName()).get();
       // }

        //예약 버튼 활성화 테스트
        //if(bookIsbn != null){
            //reservation 객체 삭제하면 안됨
            ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setRsvUserIdx(user.getIdx());
            reservationDTO.setRsvBookIsbn(bookIsbn);
            reservationDTO.setRsvDate(Date.valueOf(LocalDate.now()));
            System.out.println(reservationDTO);
            reservationService.saveReservation(reservationDTO);
           // }
        //마이페이지로 넘어가야 함
        return "redirect:/reservation-list";
        }
//    @GetMapping("/reservation_list")
//    public String listReservations(Model model) {
//        List<Reservation> reservations = reservationService.findAllReservations();
//        model.addAttribute("reservations", reservations);
//        return "reservation_list"; // 반환되는 문자열은 Thymeleaf 템플릿 파일의 이름
//    }

    }


