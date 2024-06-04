package com.mysite.library.controller;

import com.mysite.library.dto.RentDTO;
import com.mysite.library.dto.ReservationDTO;
import com.mysite.library.entity.Rent;
import com.mysite.library.service.AdminService;
import com.mysite.library.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController1 {

    private final RentService rentService;
    private final AdminService adminService;

    @GetMapping("/rents")
    public String listRents(
            @RequestParam(value = "rentdate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rentdate,
            @RequestParam(value = "duedate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate duedate,
            @RequestParam(value = "rentUserIdx", required = false, defaultValue = "0") Long idx,
            @RequestParam(value = "rentBookIsbn", required = false) String isbn,
/*            @RequestParam(value = "title", required = false) String title,
           @RequestParam(value = "isbn", required = false) String isbn, */
            Model model) {
        List<Rent> rents = rentService.searchRents(rentdate, duedate, idx, isbn);
        model.addAttribute("rents", rents);
        return "admin/rental_list";
    }

    //    @GetMapping("/reservations")
    //    public String viewReservations(@RequestParam(value="rsvBookIsbn",required = false)String rsvBookIsbn,
    //                                   @RequestParam(value="rsvUserIdx", required = false)Integer rsvUserIdx,
    //                                   @RequestParam(value="rsvDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rsvDate,
    //                                   Model model){
    //        List<Reservation> reservations = adminService.searchReservation(rsvBookIsbn, rsvUserIdx,rsvDate);
    //        model.addAttribute("reservations", reservations);
    //        return "reservation_list";
    //    }
    @GetMapping("/reservations")
    public String viewReservations(@RequestParam(value = "rsvBookIsbn", required = false) String rsvBookIsbn,
                                   @RequestParam(value = "rsvUserIdx", required = false) Long rsvUserIdx,
                                   @RequestParam(value = "rsvDate", required = false) String rsvDate,
                                   @RequestParam(required = false, defaultValue = "false") boolean includeCancelled,
                                   Model model) {
        List<ReservationDTO> reservations = adminService.searchReservations(rsvBookIsbn, rsvUserIdx, rsvDate, includeCancelled);
        model.addAttribute("reservations", reservations);
        model.addAttribute("rsvBookIsbn", rsvBookIsbn);
        model.addAttribute("rsvUserIdx", rsvUserIdx);
        model.addAttribute("rsvDate", rsvDate);
        model.addAttribute("includeCancelled", includeCancelled);
        return "admin/reservation_list";
    }

    //    @GetMapping("/reservation_list")
    //    public String getReservations(Model model){
    //        List<ReservationDTO> reservations = adminService.getAllReservations();
    //        model.addAttribute("reservations", reservations);
    //        return "reservation_list";
    //    }
    @PostMapping("/confirmReservation/{rsvIdx}")
    public String confirmReservation(@PathVariable Integer rsvIdx) {
        adminService.confirmReservation(rsvIdx);
        return "redirect:/reservations";
        // return "reservation_list";
    }

    @PostMapping("/cancelReservation/{rsvIdx}")
    public String cancelReservation(@PathVariable Integer rsvIdx) {
        adminService.cancelReservation(rsvIdx);
        return "redirect:/reservations";
    }


}
