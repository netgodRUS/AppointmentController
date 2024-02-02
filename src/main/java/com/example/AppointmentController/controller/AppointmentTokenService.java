package com.example.AppointmentController.controller;

import com.example.AppointmentController.model.appointment.AppointmentBooking;
import com.example.AppointmentController.model.users.Patient;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * DTO for {@link com.example.AppointmentController.model.token.AppointmentToken}
 */
public class AppointmentTokenService implements Serializable {
    private final Integer id;
    private final Date createdDate;
    private final String token;
    private final Patient patient;
    private final AppointmentBooking appointmentBooking;

    public AppointmentTokenService(Integer id, Date createdDate, String token, Patient patient, AppointmentBooking appointmentBooking) {
        this.id = id;
        this.createdDate = createdDate;
        this.token = token;
        this.patient = patient;
        this.appointmentBooking = appointmentBooking;
    }

    public Integer getId() {
        return id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getToken() {
        return token;
    }

    public Patient getPatient() {
        return patient;
    }

    public AppointmentBooking getAppointmentBooking() {
        return appointmentBooking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentTokenService entity = (AppointmentTokenService) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.createdDate, entity.createdDate) &&
                Objects.equals(this.token, entity.token) &&
                Objects.equals(this.patient, entity.patient) &&
                Objects.equals(this.appointmentBooking, entity.appointmentBooking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate, token, patient, appointmentBooking);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "createdDate = " + createdDate + ", " +
                "token = " + token + ", " +
                "patient = " + patient + ", " +
                "appointmentBooking = " + appointmentBooking + ")";
    }

    public com.example.AppointmentController.controller.AppointmentBooking getAppointmentInfo(String token) {
        return null;
    }
}