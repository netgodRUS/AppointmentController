package com.example.AppointmentController.controller;

import com.example.AppointmentController.dto.AppointmentBookingDto;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link com.example.AppointmentController.security.model.AppUser}
 */
public class AppointmentBookingService implements Serializable {
    private final Integer id;
    private final String name;
    private final String username;
    private final String password;
    private final Collection<RoleDto> roles;

    public AppointmentBookingService(Integer id, String name, String username, String password, Collection<RoleDto> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Collection<RoleDto> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentBookingService entity = (AppointmentBookingService) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.username, entity.username) &&
                Objects.equals(this.password, entity.password) &&
                Objects.equals(this.roles, entity.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, password, roles);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "username = " + username + ", " +
                "password = " + password + ", " +
                "roles = " + roles + ")";
    }


    public <AppointmentBooking> List<AppointmentBooking> getAllAppointment() {
        return null;
    }

    public <AppointmentBooking> List<AppointmentBooking> getAllCancelledAppointMen() {
        return null;
    }

    public <AppointmentBooking> List<AppointmentBooking> getAllApprovedAppointMen() {
        return null;

    }

    public <AppointmentBooking> List<AppointmentBooking> getAllPendingAppointMen() {
        return null;
    }

    public <AppointmentBooking> List<AppointmentBooking> getAllAttendedAppointMen() {
        return null;
    }

    public void deleteAppointment(String token) {

    }

    public <AppointmentBookingDto> ResponseEntity<ApiResponse> bookAppointment(AppointmentBookingDto appointmentBookingDto, String token) {
        return null;
    }

    public void updateAppointment(AppointmentBookingDto appointmentBookingDto, String token) {

    }

    public List<AppointmentBooking> getAllCancelledAppointments() {
        return null;
    }

    public List<AppointmentBooking> getAllApprovedAppointments() {
        return null;
    }

    public List<AppointmentBooking> getAllPendingAppointments() {
        return null;
    }

    public List<AppointmentBooking> getAllAttendedAppointments() {

        return null;
    }

    public void updateAppointment(com.example.AppointmentController.controller.AppointmentBookingDto appointmentBookingDto, String token) {

    }
}