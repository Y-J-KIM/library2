package com.mysite.library.dto;

import lombok.*;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationDTO {
    private Integer rsvIdx;
    private Long rsvUserIdx;
    private String rsvBookIsbn;
    private Date rsvDate;
    private Date rsvCclDate;
    private Date rsvConfirmDate;
    private Date rsvDueDate;

}
