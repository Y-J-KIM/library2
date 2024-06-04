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
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rentIdx;

    private Date rentdate;

    private Date duedate;

    private Date returndate;

    @ManyToOne
    @JoinColumn(name = "rent_user_idx")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "rent_book_isbn")
    private Book book;
}
