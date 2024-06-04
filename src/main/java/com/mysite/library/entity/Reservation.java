package com.mysite.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rsvIdx;

    private Date rsvDate;

    private Date rsvCclDate;

    @ManyToOne
    @JoinColumn(name = "rsv_user_idx")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "rsv_book_isbn")
    private Book book;

    private Date rsvConfirmDate;

    @Column(name="rsv_due_date")
    private Date rsvDueDate;
}
