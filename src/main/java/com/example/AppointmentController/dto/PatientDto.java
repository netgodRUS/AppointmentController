package com.example.AppointmentController.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

    private Integer id;
    private String name;
    private String email;
    private String profileImage;
    private LocalDate dateOfBirth;
    private String category;
    private int cardFee;
    private Integer age;
    private Date createdDate;
    private String token;
}
