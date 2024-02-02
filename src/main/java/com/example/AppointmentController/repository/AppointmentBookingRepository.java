package com.example.AppointmentController.repository;

import com.elijah.doctorsappointmentbookingsystem.model.appointment.AppointmentBooking;
import com.elijah.doctorsappointmentbookingsystem.model.users.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AppointmentBookingRepository  extends JpaRepository<AppointmentBooking,Integer> {
    AppointmentBooking findByPatient(Patient patient);

    @Query("select a from AppointmentBooking a where a.status like 'Approved%'")
    List<AppointmentBooking> findAllByStatusBeingApproved();

    @Query("select a from AppointmentBooking a where a.status like 'Cancel%'")
    List<AppointmentBooking> findAllByStatusBeingCancelled();

    @Query("select a from AppointmentBooking a where a.status like 'Pending%'")
    List<AppointmentBooking> findAllByStatusBeingPending();

    @Query("select a from AppointmentBooking a where a.status like 'Attended%'")
    List<AppointmentBooking> findAllByStatusBeingAttended();

    @Query("select a from AppointmentBooking a where a.patient.id=:patientId")
    List<AppointmentBooking> findByPatientId(@Param("patientId") Integer patientId);
}
