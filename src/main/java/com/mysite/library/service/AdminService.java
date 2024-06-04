package com.mysite.library.service;

import com.mysite.library.dto.ReservationDTO;
import com.mysite.library.entity.Reservation;
import com.mysite.library.repository.BookRepository;
import com.mysite.library.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminService {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;

    //  Admin-Reservation
  //  예약 리스트 다 가져오기
    public List<ReservationDTO> getAllReservations(){
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    // 검색
  public List<ReservationDTO> searchReservations(String isbn, Long userId, String date, boolean includeCancelled) {
      List<Reservation> reservations;

      LocalDate localDate = (date != null && !date.isEmpty()) ? LocalDate.parse(date) : null;
      String trimmedIsbn = (isbn != null && !isbn.trim().isEmpty()) ? isbn.trim() : null;

      log.info("Searching reservations with isbn: {}, userId: {}, date: {}, includeCancelled: {}", isbn, userId, localDate, includeCancelled);
      if (trimmedIsbn == null && userId == null && localDate == null) {
          // 검색 조건이 없을 때 모든 예약을 반환
          reservations = includeCancelled ? reservationRepository.findAll() : reservationRepository.findByRsvCclDateIsNull();
      } else {
          if (includeCancelled) {
              reservations = getReservations(trimmedIsbn, userId, localDate);
          } else {
              reservations = getActiveReservations(trimmedIsbn, userId, localDate);
          }
      }
      return reservations.stream()
              .map(this::convertToDto)
              .collect(Collectors.toList());
  }


    private List<Reservation> getReservations(String isbn, Long userId, LocalDate date) {
        if (isbn != null && userId != null && date != null) {
            return reservationRepository.findByBookIsbnAndUserEntityIdxAndRsvDate(isbn, userId, date);
        } else if (isbn != null && userId != null) {
            return reservationRepository.findByBookIsbnAndUserEntityIdx(isbn, userId);
        } else if (isbn != null && date != null) {
            return reservationRepository.findByBookIsbnAndRsvDate(isbn, date);
        } else if (userId != null && date != null) {
            return reservationRepository.findByUserEntityIdxAndRsvDate(userId, date);
        } else if (isbn != null) {
            return reservationRepository.findByBookIsbn(isbn);
        } else if (userId != null) {
            return reservationRepository.findByUserEntityIdx(userId);
        } else if (date != null) {
            return reservationRepository.findByRsvDate(date);
        } else {
            return reservationRepository.findAll();
        }
    }

    private List<Reservation> getActiveReservations(String isbn, Long userId, LocalDate date) {
        if (isbn != null && userId != null && date != null) {
            return reservationRepository.findByRsvCclDateIsNullAndBookIsbnAndUserEntityIdxAndRsvDate(isbn, userId, date);
        } else if (isbn != null && userId != null) {
            return reservationRepository.findByRsvCclDateIsNullAndBookIsbnAndUserEntityIdx(isbn, userId);
        } else if (isbn != null && date != null) {
            return reservationRepository.findByRsvCclDateIsNullAndBookIsbnAndRsvDate(isbn, date);
        } else if (userId != null && date != null) {
            return reservationRepository.findByRsvCclDateIsNullAndUserEntityIdxAndRsvDate(userId, date);
        } else if (isbn != null) {
            return reservationRepository.findByRsvCclDateIsNullAndBookIsbn(isbn);
        } else if (userId != null) {
            return reservationRepository.findByRsvCclDateIsNullAndUserEntityIdx(userId);
        } else if (date != null) {
            return reservationRepository.findByRsvCclDateIsNullAndRsvDate(date);
        } else {
            return reservationRepository.findByRsvCclDateIsNull();
        }
    }
    //관리자 예약 확정
    public void confirmReservation(Integer rsv_idx){
        Reservation reservation = reservationRepository.findById(rsv_idx).orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id" + rsv_idx));
        LocalDate now = LocalDate.now();
        reservation.setRsvConfirmDate(Date.valueOf(LocalDate.now()));
        reservation.setRsvDueDate(Date.valueOf(now.plusDays(2)));
        //reservation.confirmReservation();
        reservationRepository.save(reservation);
    }
    //관리자 예약 취소
    public void cancelReservation(Integer rsv_idx){
        Reservation reservation = reservationRepository.findById(rsv_idx)
                .orElseThrow(()-> new IllegalArgumentException("Invalid reservaion Id:"+rsv_idx));
        reservation.setRsvCclDate(Date.valueOf(LocalDate.now()));
        reservationRepository.save(reservation);
    }
    //예약 만료 확인 및 취소 작업 스케줄러
    @Scheduled(cron="0 0 0 * * ?")//매일 자정에 실행
    @Transactional
    public void checkAndCancelExpiredReservations(){
        LocalDate now = LocalDate.now();
        List<Reservation> expiredReservations = reservationRepository.findByRsvDueDateBeforeAndRsvCclDateIsNull(Date.valueOf(now));
        for(Reservation reservation : expiredReservations){
            reservation.setRsvCclDate(Date.valueOf(now));
            reservationRepository.save(reservation);
            log.info("Cancelled expired reservation with id: {}", reservation.getRsvIdx());
        }
    }
    //entity->DTO
    private ReservationDTO convertToDto(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setRsvIdx(reservation.getRsvIdx());
        dto.setRsvUserIdx(reservation.getUserEntity().getIdx());
        dto.setRsvBookIsbn(reservation.getBook().getIsbn());
        dto.setRsvDate(reservation.getRsvDate());
        //dto.setRsvConfirmDate(reservation.getRsvConfirmDate());
        dto.setRsvDueDate(reservation.getRsvDueDate());
        //dto.setRsvCclDate(reservation.getRsvCclDate());

        //Book 정보 설정
//        Book book = bookRepository.findById(reservation.getRsvBookIsbn()).orElse(null));
//        dto.setBook(book);
        return dto;
    }
}
