package com.mysite.library.service;

import com.mysite.library.dto.ReservationDTO;
import com.mysite.library.entity.Book;
import com.mysite.library.entity.Reservation;
import com.mysite.library.entity.UserEntity;
import com.mysite.library.repository.BookRepository;
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
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;


    public void reserveBook(UserEntity user, String isbn) {
        Book book = bookRepository.findById(isbn).orElseThrow(() -> new IllegalArgumentException("Invalid isbn: " + isbn));
        Reservation reserve = new Reservation();
        reserve.setUserEntity(user);
        reserve.setBook(book);
        reserve.setRsvDate(Date.valueOf(LocalDate.now()));
        reserve.setRsvConfirmDate(Date.valueOf(LocalDate.now()));
        reserve.setRsvDueDate(Date.valueOf(LocalDate.now().plusDays(3)));
        reservationRepository.save(reserve);
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation saveReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setRsvDate(reservationDTO.getRsvDate());

        UserEntity user = userRepository.findById(reservationDTO.getRsvUserIdx())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        reservation.setUserEntity(user);

        Book book = bookRepository.findById(reservationDTO.getRsvBookIsbn())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        reservation.setBook(book);
        return reservationRepository.save(reservation);
    }

    public List<ReservationDTO> getRsvInfo(String name) {
        Optional<UserEntity> user = userRepository.findByName(name);
        if (user.isPresent()) {
            List<Reservation> rsvs = reservationRepository.findByUserEntityIdx(user.get().getIdx());
            return rsvs.stream().map(rsv -> {
                ReservationDTO dto = new ReservationDTO();
                dto.setRsvIdx(rsv.getRsvIdx());
                dto.setRsvUserIdx(rsv.getUserEntity().getIdx());
                /* Book book = rsv.getBook();
                if (book != null) {
                    dto.setRsvBookIsbn(book.getIsbn());
                } else {
                    dto.setRsvBookIsbn("");
                } */
                dto.setRsvBookIsbn(rsv.getBook().getIsbn());
                dto.setRsvDate(rsv.getRsvDate());
                dto.setRsvDueDate(rsv.getRsvDueDate());
                return dto;
            }).collect(Collectors.toList());
        }
        return List.of();
    }
}
