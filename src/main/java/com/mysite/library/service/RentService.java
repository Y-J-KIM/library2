/*
package com.mysite.library.service;

import com.mysite.library.entity.Book;
import com.mysite.library.entity.Rent;
import com.mysite.library.entity.Reservation;
import com.mysite.library.entity.UserEntity;
import com.mysite.library.repository.BookRepository;
import com.mysite.library.repository.RentRepository;
import com.mysite.library.repository.ReservationRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentService {
    private final RentRepository rentRepo;
    private final ReservationRepository rsvRepo;
    private final BookRepository bookRepo;

    java.util.Date utilDate = new java.util.Date();
    long currentMilliSeconds = utilDate.getTime();
    Date sqlDate = new Date(currentMilliSeconds);

    public void rentBook(UserEntity user, String isbn) {
        Book book = bookRepo.findById(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ISBN: " + isbn));
        Rent rent = new Rent();
        rent.setUserEntity(user);
        rent.setBook(book);
        // rent.setRentdate(sqlDate);
        rent.setRentdate(Date.valueOf(LocalDate.now()));
        rent.setDuedate(Date.valueOf(LocalDate.now().plusWeeks(2)));
        rentRepo.save(rent);

        book.setRentAvailable(false);
        book.setRsvAvailable(true);
        bookRepo.save(book);
    }

    public void reserveBook(UserEntity user, String isbn) {
        Book book = bookRepo.findById(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + isbn));

        // 중복 예약 확인
        boolean alreadyReserved = rsvRepo.existsByUserEntityAndBook(user,book);
        if (alreadyReserved) {
            throw new IllegalArgumentException("You have already reserved this book.");
        }
        Reservation reservation = new Reservation();
        // reservation.setRsvDate(sqlDate);
        reservation.setUserEntity(user);
        reservation.setBook(book);
        reservation.setRsvDate(Date.valueOf(LocalDate.now()));
        rsvRepo.save(reservation);

        bookRepo.save(book);
    }

    public Rent getRentByBookIsbn(String isbn) {
        List<Rent> results = rentRepo.findByBookIsbnAndReturndateIsNull(isbn);
        if (!results.isEmpty()) {
            return results.get(0); // 첫 번째 결과 반환
        } else {
            // 결과가 없을 때의 처리 로직
            throw new NoResultException("No rent found for book ID: " + isbn);
        }
    }
}*/
package com.mysite.library.service;

import com.mysite.library.dto.RentDTO;
import com.mysite.library.entity.Book;
import com.mysite.library.entity.Rent;
import com.mysite.library.entity.UserEntity;
import com.mysite.library.repository.BookRepository;
import com.mysite.library.repository.RentRepository;
import com.mysite.library.repository.ReservationRepository;
import com.mysite.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentService {
    private final RentRepository rentRepo;
    private final BookRepository bookRepo;
    private final UserRepository userRepo;
    private final ReservationRepository reservationRepo;

    public void rentBook(UserEntity user, String isbn) {
        Book book = bookRepo.findById(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Invalid isbn: " + isbn));
        Rent rent = new Rent();
        rent.setUserEntity(user);
        rent.setBook(book);
        rent.setRentdate(Date.valueOf(LocalDate.now()));
        rent.setDuedate(Date.valueOf(LocalDate.now().plusWeeks(2)));
        rentRepo.save(rent);

        book.setRentAvailable(false);
        book.setRsvAvailable(true);
        bookRepo.save(book);
    }

    public List<RentDTO> getRentInfo(String name) {
        Optional<UserEntity> user = userRepo.findByName(name);
        if (user.isPresent()) {
            List<Rent> rents = rentRepo.findByUserEntityIdx(user.get().getIdx());
            return rents.stream().map(rent -> {
                RentDTO rentDTO = new RentDTO();
                rentDTO.setRentIdx(rent.getRentIdx());
                rentDTO.setRentUserIdx(rent.getUserEntity().getIdx());
                rentDTO.setRentBookIsbn(rent.getBook().getIsbn());
                rentDTO.setRentdate(rent.getRentdate());
                rentDTO.setDuedate(rent.getDuedate());
                rentDTO.setReturndate(rent.getReturndate());
                return rentDTO;
            }).collect(Collectors.toList());
        }
        return List.of();
    }

    public List<Rent> findAllRents() {
        return rentRepo.findAll();
    }

    public List<RentDTO> getAllRents() {
        List<Rent> rents = rentRepo.findAll();
        return rents.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<Rent> searchRents(LocalDate rentdate, LocalDate duedate, Long idx, String isbn) {
        if (rentdate != null || duedate != null || idx != null || isbn != null) {
            return rentRepo.findByRentdateOrDuedateOrUserEntityIdxOrBookIsbnOrderByDuedateDesc(
                    rentdate, duedate, idx, isbn);
        } else {
            return null;
        }
    }

    private RentDTO convertToDto(Rent rent) {
        return new RentDTO(
                rent.getRentIdx(),
                rent.getUserEntity().getIdx(),
                rent.getBook().getIsbn(),
                rent.getRentdate(),
                rent.getDuedate(),
                rent.getReturndate()
        );
    }
}
