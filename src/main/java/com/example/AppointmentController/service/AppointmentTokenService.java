package com.example.AppointmentController.service;

import com.elijah.doctorsappointmentbookingsystem.exception.DataNotFoundException;
import com.elijah.doctorsappointmentbookingsystem.model.appointment.AppointmentBooking;
import com.elijah.doctorsappointmentbookingsystem.model.token.AppointmentToken;
import com.elijah.doctorsappointmentbookingsystem.repository.AppointmentTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AppointmentTokenService {

    @Autowired
    private AppointmentTokenRepository appointmentTokenRepository;

    public void saveAppointmentToken(AppointmentToken appointmentToken){
        appointmentTokenRepository.save(appointmentToken);
    }

    public AppointmentBooking getAppointmentInfo(String token) throws DataNotFoundException {
        AppointmentToken appointmentToken = appointmentTokenRepository.findByToken(token);
        if (Objects.isNull(appointmentToken)){
            throw new DataNotFoundException("Appointment token Not Valid");
        }if (appointmentToken.getAppointmentBooking().getId()==null){
            throw new DataNotFoundException("This Patient has not book any appointment yet");
        }
        return appointmentToken.getAppointmentBooking();
    }
}
