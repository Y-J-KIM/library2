package com.mysite.library.repository;

import com.mysite.library.entity.Book;
import com.mysite.library.entity.Reservation;
import com.mysite.library.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    /* boolean existsByUserEntityAndBook(UserEntity userEntity, Book book);

    Reservation findByBookIsbnAndRsvCclDateIsNullAndUserEntityIdx(String bookIsbn, Integer userIdx); */

    //관리자 페이지
    List<Reservation> findByRsvCclDateIsNull();
    List<Reservation> findByBookIsbnAndUserEntityIdxAndRsvDate(String isbn, Long idx, LocalDate rsvDate);
    List<Reservation> findByBookIsbnAndUserEntityIdx(String isbn, Long idx);
    List<Reservation> findByBookIsbnAndRsvDate(String isbn, LocalDate rsvDate);
    List<Reservation> findByUserEntityIdxAndRsvDate(Long idx, LocalDate rsvDate);
    List<Reservation> findByBookIsbn(String isbn);
    List<Reservation> findByUserEntityIdx(Long idx);
    List<Reservation> findByRsvDate(LocalDate rsvDate);
    List<Reservation> findByRsvCclDateIsNullAndBookIsbnAndUserEntityIdxAndRsvDate(String isbn, Long idx, LocalDate rsvDate);
    List<Reservation> findByRsvCclDateIsNullAndBookIsbnAndUserEntityIdx(String isbn, Long idx);
    List<Reservation> findByRsvCclDateIsNullAndBookIsbnAndRsvDate(String isbn, LocalDate rsvDate);
    List<Reservation> findByRsvCclDateIsNullAndUserEntityIdxAndRsvDate(Long idx, LocalDate rsvDate);
    List<Reservation> findByRsvCclDateIsNullAndBookIsbn(String isbn);
    List<Reservation> findByRsvCclDateIsNullAndUserEntityIdx(Long idx);
    List<Reservation> findByRsvCclDateIsNullAndRsvDate(LocalDate rsvDate);
    //만료된 예약 찾는 메서드
    List<Reservation> findByRsvDueDateBeforeAndRsvCclDateIsNull(Date now);

    List<Reservation> findByUserEntity_Idx(Long idx);

    //    List<Reservation> findByRsvCclDateIsNullOrRsvBookIsbnOrRsvUserIdxOrRsvDateOrderByRsvDateDesc(
//            String rsvBookIsbn, Integer rsvUserIdx, LocalDate rsvDate);
//    List<Reservation> findAllByOrderByRsvDateDesc();
}

