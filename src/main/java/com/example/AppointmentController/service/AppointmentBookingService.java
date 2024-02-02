package com.example.AppointmentController.service;


import com.elijah.doctorsappointmentbookingsystem.dto.AppointmentBookingDto;
import com.elijah.doctorsappointmentbookingsystem.email.EmailServiceClient;
import com.elijah.doctorsappointmentbookingsystem.exception.DataAlreadyExistException;
import com.elijah.doctorsappointmentbookingsystem.exception.DataNotFoundException;
import com.elijah.doctorsappointmentbookingsystem.model.appointment.AppointmentBooking;
import com.elijah.doctorsappointmentbookingsystem.model.token.AppointmentToken;
import com.elijah.doctorsappointmentbookingsystem.model.users.Patient;
import com.elijah.doctorsappointmentbookingsystem.repository.AppointmentBookingRepository;
import com.elijah.doctorsappointmentbookingsystem.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AppointmentBookingService {
    @Autowired
    private AuthenticationTokenService authenticationTokenService;

    @Autowired
    private AppointmentTokenService appointmentTokenService;

    @Autowired
    private EmailServiceClient emailServiceClient;
    @Autowired
    private AppointmentBookingRepository appointmentBookingRepository;

    @Transactional
    public ResponseEntity<ApiResponse> bookAppointment(AppointmentBookingDto appointmentBookingDto, String token) throws DataNotFoundException, ParseException, DataAlreadyExistException {

        AppointmentBooking appointmentBooking = new AppointmentBooking();
        appointmentBooking.setCreatedDate(new Date());
        appointmentBooking.setDoctorScheduled(appointmentBookingDto.getDoctorScheduled());
        appointmentBooking.setAppointmentDate(appointmentBookingDto.getAppointmentDate());
        appointmentBooking.setPurpose(appointmentBookingDto.getPurpose());
        appointmentBooking.setStatus(appointmentBookingDto.getStatus());
        appointmentBooking.setRemark(appointmentBookingDto.getRemark());
        appointmentBooking.setAppointmentTime(appointmentBookingDto.getAppointmentTime());
        checkForPendingOrApprovedAppointment(appointmentBooking,token);

        final AppointmentToken appointmentToken = new AppointmentToken(appointmentBooking);
        appointmentTokenService.saveAppointmentToken(appointmentToken);
        emailServiceClient.sendSimpleEmail(appointmentBooking.getPatient().getEmail(),
                "Appointment Booking Token","This is your appointment booking token," +
                        " please keep it save as it will be needed on your arrival to the hospital as well as when you want to check your appointment information." +
                        " Here is the token "+appointmentToken);

        return new ResponseEntity<>(new ApiResponse(true,"Your Appointment was booked Successfully, your appointment token is "+
                appointmentToken.getToken()), HttpStatus.CREATED);
    }

    public AppointmentBooking updateAppointment(AppointmentBookingDto appointmentBookingDto, String token) throws DataNotFoundException, ParseException {

        AppointmentBooking appointmentBooking = appointmentTokenService.getAppointmentInfo(token);
        if (Objects.isNull(appointmentBooking)){
            throw new DataNotFoundException("User with the token not found");
        }
        if (Objects.nonNull(appointmentBookingDto.getAppointmentDate()) && !"".equals(appointmentBookingDto.getAppointmentDate())){
            appointmentBooking.setAppointmentDate(appointmentBookingDto.getAppointmentDate());
        }if (Objects.nonNull(appointmentBookingDto.getAppointmentTime())){
            appointmentBooking.setAppointmentTime(appointmentBookingDto.getAppointmentTime());
        }if (Objects.nonNull(appointmentBookingDto.getDoctorScheduled())&& !"".equalsIgnoreCase(appointmentBookingDto.getDoctorScheduled())){
            appointmentBooking.setDoctorScheduled(appointmentBookingDto.getDoctorScheduled());
        }if (Objects.nonNull(appointmentBookingDto.getPurpose())&& !"".equalsIgnoreCase(appointmentBookingDto.getPurpose())){
            appointmentBooking.setPurpose(appointmentBookingDto.getPurpose());
        }if (Objects.nonNull(appointmentBookingDto.getRemark())&& !"".equalsIgnoreCase(appointmentBookingDto.getRemark())){
            appointmentBooking.setRemark(appointmentBookingDto.getRemark());
        }if (Objects.nonNull(appointmentBookingDto.getStatus())&& !"".equalsIgnoreCase(appointmentBookingDto.getStatus())){
            appointmentBooking.setStatus(appointmentBookingDto.getStatus());
        }
        return appointmentBookingRepository.save(appointmentBooking);

    }
    public void deleteAppointment(String token) throws DataNotFoundException {
        Patient patient = authenticationTokenService.getPatientByToken(token);
        AppointmentBooking appointmentBooking = appointmentBookingRepository.findByPatient(patient);
        appointmentBookingRepository.delete(appointmentBooking);
    }

    public List<AppointmentBooking> getAllAppointment(){
        return appointmentBookingRepository.findAll();
    }

    public List<AppointmentBooking> getAllCancelledAppointMen(){
        return appointmentBookingRepository.findAllByStatusBeingCancelled();
    }

    public List<AppointmentBooking> getAllApprovedAppointMen(){
        return appointmentBookingRepository.findAllByStatusBeingApproved();
    }

    public List<AppointmentBooking> getAllPendingAppointMen(){
        return appointmentBookingRepository.findAllByStatusBeingPending();
    }

    public List<AppointmentBooking> getAllAttendedAppointMen(){
        return appointmentBookingRepository.findAllByStatusBeingAttended();
    }



    public void checkForPendingOrApprovedAppointment(AppointmentBooking appointmentBooking, String token) throws DataNotFoundException, DataAlreadyExistException {

        Patient patient = authenticationTokenService.getPatientByToken(token);
        //AppointmentBooking appointment = appointmentBookingRepository.findByPatient(patient);
        List<AppointmentBooking> appointment = appointmentBookingRepository.findByPatientId(patient.getId());
        String appointmentStatus = "";

        if (Objects.nonNull(appointment)){
            for (AppointmentBooking booking : appointment) {
                appointmentStatus = booking.getStatus();
            }

                if (appointmentStatus.equalsIgnoreCase("Approved")) {
                    throw new DataAlreadyExistException("You have already placed an unattended appointment");
                } else if (appointmentStatus.equalsIgnoreCase("Pending")) {
                    throw new DataAlreadyExistException("You have already placed a pending appointment");
                } else {
                    appointmentBooking.setPatient(patient);
                    appointmentBookingRepository.save(appointmentBooking);
            }
        }
        appointmentBooking.setPatient(patient);
        appointmentBookingRepository.save(appointmentBooking);

    }
}

