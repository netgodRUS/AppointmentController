package com.example.AppointmentController.repository;

import com.elijah.doctorsappointmentbookingsystem.model.token.AppointmentToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppointmentTokenRepository extends JpaRepository<AppointmentToken,Integer> {

    AppointmentToken findByToken(String token);
}
