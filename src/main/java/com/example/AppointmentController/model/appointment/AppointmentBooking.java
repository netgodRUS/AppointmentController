package com.example.AppointmentController.model.appointment;


import com.elijah.doctorsappointmentbookingsystem.model.users.Patient;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppointmentBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String status;
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm")
    private Date appointmentTime;
    private LocalDate appointmentDate;
    private String purpose;
    private String doctorScheduled;
    private Date createdDate;
    private String remark;
    @ManyToOne
    private Patient patient;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAppointmentTime() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String newDate = dateFormat.format(appointmentTime);
        appointmentTime = dateFormat.parse(newDate);
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDoctorScheduled() {
        return doctorScheduled;
    }

    public void setDoctorScheduled(String doctorScheduled) {
        this.doctorScheduled = doctorScheduled;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
