package com.example.AppointmentController.controller;
import com.example.AppointmentController.exception.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentBookingController {

    // Replace the following placeholders with the appropriate classes or services
    private AppointmentBookingService appointmentBookingService;
    private AppointmentTokenService appointmentTokenService;

    @PostMapping("/book")
    public ResponseEntity<ApiResponse> bookAppointment(@RequestBody AppointmentBookingDto appointmentBookingDto, @RequestParam("token") String token) throws DataNotFoundException, ParseException {
        // Replace the following line with the actual implementation
        return appointmentBookingService.bookAppointment(appointmentBookingDto, token);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateAppointment(@RequestBody AppointmentBookingDto appointmentBookingDto, @RequestParam("token") String token) throws DataNotFoundException, ParseException {
        // Replace the following line with the actual implementation
        appointmentBookingService.updateAppointment(appointmentBookingDto, token);

        return new ResponseEntity<>(new ApiResponse(true, "Appointment Updated Successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteAppointment(@RequestParam("token") String token) throws DataNotFoundException {
        // Replace the following line with the actual implementation
        appointmentBookingService.deleteAppointment(token);
        return new ResponseEntity<>(new ApiResponse(true, "Appointment Deleted Successfully"), HttpStatus.OK);
    }

    @GetMapping("/info")
    public AppointmentBooking getAppointmentInformation(@RequestParam("token") String token) throws DataNotFoundException {
        // Replace the following line with the actual implementation
        return appointmentTokenService.getAppointmentInfo(token);
    }

    @GetMapping("/list/all")
    public List<AppointmentBooking> appointmentBookingList() {
        // Replace the following line with the actual implementation
        return appointmentBookingService.getAllAppointment();
    }

    @GetMapping("/list/cancel")
    public List<AppointmentBooking> cancelList() {
        // Replace the following line with the actual implementation
        return appointmentBookingService.getAllCancelledAppointments();
    }

    @GetMapping("/list/approved")
    public List<AppointmentBooking> approvedList() {
        // Replace the following line with the actual implementation
        return appointmentBookingService.getAllApprovedAppointments();
    }

    @GetMapping("/list/pending")
    public List<AppointmentBooking> pendingList() {
        // Replace the following line with the actual implementation
        return appointmentBookingService.getAllPendingAppointments();
    }

    @GetMapping("/list/attended")
    public List<AppointmentBooking> attendedList() {
        // Replace the following line with the actual implementation
        return appointmentBookingService.getAllAttendedAppointments();
    }
}

