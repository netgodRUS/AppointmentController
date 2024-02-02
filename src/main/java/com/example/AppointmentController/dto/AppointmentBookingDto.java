package com.example.AppointmentController.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
public class AppointmentBookingDto {

    private String status = "Pending";
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm")
    private Date appointmentTime;
    private String remark = "No Remark yet";
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate appointmentDate ;
    private String purpose ;
    private String doctorScheduled = "No Doctor Assigned Yet";

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
