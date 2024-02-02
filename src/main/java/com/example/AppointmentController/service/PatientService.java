package com.example.AppointmentController.service;

import com.elijah.doctorsappointmentbookingsystem.dto.PatientDto;
import com.elijah.doctorsappointmentbookingsystem.dto.SignInDto;
import com.elijah.doctorsappointmentbookingsystem.dto.SignUpDto;
import com.elijah.doctorsappointmentbookingsystem.email.EmailServiceClient;
import com.elijah.doctorsappointmentbookingsystem.exception.DataAlreadyExistException;
import com.elijah.doctorsappointmentbookingsystem.exception.DataNotFoundException;
import com.elijah.doctorsappointmentbookingsystem.model.token.AuthenticationToken;
import com.elijah.doctorsappointmentbookingsystem.model.users.Patient;
import com.elijah.doctorsappointmentbookingsystem.repository.PatientRepository;
import com.elijah.doctorsappointmentbookingsystem.response.ApiResponse;
import com.elijah.doctorsappointmentbookingsystem.response.SignInResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private EmailServiceClient emailServiceClient;

    @Autowired
    private AuthenticationTokenService authenticationTokenService;

    @Transactional
    public ResponseEntity<ApiResponse> signUpUser(SignUpDto signUpDto) throws DataAlreadyExistException {

        //first check if the email address has already been taken by another user
        //if true, returns an error message to choose another email else go ahead and signup the user
        //hash the password so that it can't be seen
        //create a token for each user that signUp
        if (Objects.nonNull(patientRepository.findByEmail(signUpDto.getEmail()))){
            throw new DataAlreadyExistException("This Email Address has already been taken by another User");
        }

        String encryptedPassword = signUpDto.getPassword();
        try {
            encryptedPassword = hashPassword(signUpDto.getPassword());
        }catch (Exception e){
            e.printStackTrace();
        }

       // signUpDto.setDateOfBirth(LocalDate.of(2000,OCTOBER,23));
//        signUpDto.setAge(Period.between(signUpDto.getDateOfBirth(), LocalDate.now()).getYears());


        Patient patient = new Patient();
        //users.setDateOfBirth(signUpDto.getDateOfBirth());
        patient.setAge(Period.between(signUpDto.getDateOfBirth(),LocalDate.now()).getYears());
        if (patient.getAge()<5){
            patient.setCardFee(200);
            patient.setCategory("Infant");
        }else if (patient.getAge()>5 && patient.getAge() <18){
            patient.setCardFee(500);
            patient.setCategory("Teenager");
        }else if (patient.getAge() >=18){
            patient.setCardFee(1000);
            patient.setCategory("Adults");
        }else {
            patient.setCardFee(0);
            patient.setCategory("Wrong Age Selection");
        }
        patient.setEmail(signUpDto.getEmail());
        patient.setName(signUpDto.getName());
        patient.setPassword(encryptedPassword);
        patient.setProfileImage(signUpDto.getProfileImage());
        patient.setDateOfBirth(signUpDto.getDateOfBirth());
        patient.setCreatedDate(new Date());
        patientRepository.save(patient);
        final AuthenticationToken authenticationToken = new AuthenticationToken(patient);
        authenticationTokenService.saveConfirmationToken(authenticationToken);
//        emailServiceClient.sendSimpleEmail(patient.getEmail(),
//                "Confirmation Email from the Doctors Appointment Booking System",
//                "Dear "+patient.getName()+"\nThis is to inform you that your registration" +
//                        "to the Doctors Appointment Booking application was successful\nHere is your authentication token "+authenticationToken+
//                        "\nPlease keep it private and save because you will need it in several occasion in the app especially when you want to book an appointment with a doctor\nThanks for your patronage" +
//                        "\n\n\n\nFor More Enquiries,\nCall 08167988220\nElijah Ukeme\nThe Technical Officer");

        return new ResponseEntity<>(new ApiResponse(true,"Registration Successful, you will be charged "+
                patient.getCardFee()+" Naira for card fee on arrival to the hospital by the accounting department before" +
                " issuing a patient's card to you. Thanks"), HttpStatus.CREATED);

    }

    public SignInResponse signInUser(SignInDto signInDto) throws DataNotFoundException, NoSuchAlgorithmException {
        //first find the user that tries to login with email address
        Patient patient = patientRepository.findByEmail(signInDto.getEmail());
        if (Objects.isNull(patient)){
            throw new DataNotFoundException("Incorrect Email or Password");
        }
        //check for the password enter and compared it to the hash password in the db
        if (!(patient.getPassword().equals(hashPassword(signInDto.getPassword())))){
            throw new DataNotFoundException("Incorrect Email or Password");
        }
        final AuthenticationToken token = authenticationTokenService.getTokenByPatient(patient);
        if (Objects.isNull(token)){
            throw new DataNotFoundException("Token Not Found");
        }
        return new SignInResponse("Success",token.getToken());
    }

    public PatientDto getUserInformation(SignInDto signInDto) throws DataNotFoundException, NoSuchAlgorithmException {
        //first find the user that tries to login with email address
        Patient patient = patientRepository.findByEmail(signInDto.getEmail());
        if (Objects.isNull(patient)){
            throw new DataNotFoundException("Incorrect Email or Password");
        }
        //check for the password enter and compared it to the hash password in the db
        if (!(patient.getPassword().equals(hashPassword(signInDto.getPassword())))){
            throw new DataNotFoundException("Incorrect Email or Password");
        }
        final AuthenticationToken token = authenticationTokenService.getTokenByPatient(patient);
        if (Objects.isNull(token)){
            throw new DataNotFoundException("Token Not Found");
        }

        PatientDto patientDto = new PatientDto();

        patientDto.setId(patient.getId());
        patientDto.setAge(patient.getAge());
        patientDto.setCardFee(patient.getCardFee());
        patientDto.setCategory(patient.getCategory());
        patientDto.setCreatedDate(patient.getCreatedDate());
        patientDto.setName(patient.getName());
        patientDto.setEmail(patient.getEmail());
        patientDto.setDateOfBirth(patient.getDateOfBirth());
        patientDto.setProfileImage(patient.getProfileImage());
        patientDto.setToken(authenticationTokenService.getTokenByPatient(patient).getToken());

        return patientDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        byte[] digest = messageDigest.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toLowerCase().substring(0,12);
        return hash;

    }
}
