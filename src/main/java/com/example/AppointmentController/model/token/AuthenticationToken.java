package com.example.AppointmentController.model.token;


import com.elijah.doctorsappointmentbookingsystem.model.users.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    private Date createdDate;

    @OneToOne(targetEntity = Patient.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "user_id")
    private Patient patient;

    public AuthenticationToken(Patient patient){
        this.patient = patient;
        this.createdDate = new Date();
        this.token = UUID.randomUUID().toString();
    }
}
