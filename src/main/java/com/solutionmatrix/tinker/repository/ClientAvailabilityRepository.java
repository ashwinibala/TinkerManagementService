package com.solutionmatrix.tinker.repository;

import com.solutionmatrix.tinker.model.entity.ClientAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientAvailabilityRepository extends JpaRepository<ClientAvailability, Long> {

    Optional<ClientAvailability> findByClientIdAndDateAndTimeslotIdAndStatusId(Long clientId, String date, Long timeslotId, Long statusId);
}
