package com.mysite.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentDTO {

    private Integer rentIdx;
    private Long rentUserIdx;
    private String rentBookIsbn;
    private Date rentdate;
    private Date duedate;
    private Date returndate;
}
