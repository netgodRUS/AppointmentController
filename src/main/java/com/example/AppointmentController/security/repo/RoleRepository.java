package com.example.AppointmentController.security.repo;

import com.elijah.doctorsappointmentbookingsystem.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findByName(String name);
}
