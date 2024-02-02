package com.example.AppointmentController.model.token;

import com.elijah.doctorsappointmentbookingsystem.model.appointment.AppointmentBooking;
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
public class AppointmentToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date createdDate;
    private String token;
    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne(targetEntity = AppointmentBooking.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id")
    private AppointmentBooking appointmentBooking;

    public AppointmentToken(AppointmentBooking appointmentBooking){
        this.appointmentBooking = appointmentBooking;
        this.createdDate = new Date();
        this.patient = appointmentBooking.getPatient();
        this.token = "elidrbooking"+ UUID.randomUUID().toString();
    }
}
