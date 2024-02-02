package com.example.AppointmentController.repository;

import com.elijah.doctorsappointmentbookingsystem.model.token.AuthenticationToken;
import com.elijah.doctorsappointmentbookingsystem.model.users.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationToken,Integer> {

    AuthenticationToken findByPatient(Patient patient);
    AuthenticationToken findByToken(String token);
}
