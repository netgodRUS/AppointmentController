package com.example.AppointmentController.repository;

import com.elijah.doctorsappointmentbookingsystem.model.users.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer> {

    Patient findByEmail(String email);
}
