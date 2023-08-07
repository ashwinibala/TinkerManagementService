package com.solutionmatrix.tinker.repository;

import com.solutionmatrix.tinker.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByClientId(Long clientId);

    Optional<Appointment> findByClientIdAndId(Long clientId, Long appointmentId);
}
