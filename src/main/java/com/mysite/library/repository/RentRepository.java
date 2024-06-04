package com.mysite.library.repository;

import com.mysite.library.dto.RentDTO;
import com.mysite.library.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RentRepository extends JpaRepository<Rent, Integer> {
    List<Rent> findByBookIsbnAndReturndateIsNull(String isbn);
    Optional<Rent> findFirstByBookIsbnAndReturndateIsNull(String isbn);
    List<Rent> findByRentdateOrDuedateOrUserEntityIdxOrBookIsbnOrderByDuedateDesc(
            LocalDate rentdate, LocalDate duedate, Long idx, String isbn);
    List<Rent> findByUserEntityIdx(Long idx);
}
