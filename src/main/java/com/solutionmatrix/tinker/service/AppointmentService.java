package com.solutionmatrix.tinker.service;

import com.solutionmatrix.tinker.model.entity.Appointment;
import com.solutionmatrix.tinker.repository.AppointmentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final ClientAvailabilityService clientAvailabilityService;

    public Appointment createAppointment(Appointment appointment) {
        try {
            Appointment savedAppointment = appointmentRepository.save(appointment);
            clientAvailabilityService.updateClientAvailability(savedAppointment.getClientId(), savedAppointment.getDate(), savedAppointment.getTimeslotId());
            return savedAppointment;
        } catch(Exception e){
            throw new RuntimeException();
        }
    }
}
