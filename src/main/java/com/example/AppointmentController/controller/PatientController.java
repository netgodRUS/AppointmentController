package com.example.AppointmentController.controller;

import com.elijah.doctorsappointmentbookingsystem.dto.PatientDto;
import com.elijah.doctorsappointmentbookingsystem.dto.SignInDto;
import com.elijah.doctorsappointmentbookingsystem.dto.SignUpDto;
import com.elijah.doctorsappointmentbookingsystem.exception.DataAlreadyExistException;
import com.elijah.doctorsappointmentbookingsystem.exception.DataNotFoundException;
import com.elijah.doctorsappointmentbookingsystem.response.ApiResponse;
import com.elijah.doctorsappointmentbookingsystem.response.SignInResponse;
import com.elijah.doctorsappointmentbookingsystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse> signUpUser(@RequestBody SignUpDto signUpDto) throws DataAlreadyExistException {
        return patientService.signUpUser(signUpDto);
    }
    @PostMapping("/signIn")
    public SignInResponse signInUser(@RequestBody SignInDto signInDto) throws NoSuchAlgorithmException, DataNotFoundException {
        return patientService.signInUser(signInDto);
    }
    @PostMapping("/getInfo")
    public PatientDto getUserInfo(@RequestBody SignInDto signInDto) throws NoSuchAlgorithmException, DataNotFoundException {
        return patientService.getUserInformation(signInDto);
    }
}
