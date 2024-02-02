package com.example.AppointmentController.security.repo;

import com.elijah.doctorsappointmentbookingsystem.security.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Integer> {

    AppUser findByUsername(String username);
}
