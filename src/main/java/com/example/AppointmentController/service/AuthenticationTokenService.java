package com.example.AppointmentController.service;

import com.elijah.doctorsappointmentbookingsystem.exception.DataNotFoundException;
import com.elijah.doctorsappointmentbookingsystem.model.token.AuthenticationToken;
import com.elijah.doctorsappointmentbookingsystem.model.users.Patient;
import com.elijah.doctorsappointmentbookingsystem.repository.AuthenticationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationTokenService {

    @Autowired
    private AuthenticationTokenRepository authenticationTokenRepository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken){
        authenticationTokenRepository.save(authenticationToken);

    }
    public AuthenticationToken getTokenByPatient(Patient patient){
        return authenticationTokenRepository.findByPatient(patient);
    }

    public Patient getPatientByToken(String token) throws DataNotFoundException {
        AuthenticationToken authenticationToken = authenticationTokenRepository.findByToken(token);
            if (Objects.isNull(authenticationToken)){
                throw new DataNotFoundException("Token Not Found");
            }
            return authenticationToken.getPatient();
        }

        public void authenticateToken(String token) throws DataNotFoundException {
        if (Objects.isNull(token)){
            throw new DataNotFoundException("Token Not Found");
        }
        if (Objects.isNull(getPatientByToken(token))){
            throw new DataNotFoundException("There is No User with Such Token");
        }
        getPatientByToken(token);
        }
    }

