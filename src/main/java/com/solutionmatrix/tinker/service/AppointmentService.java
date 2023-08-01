package com.solutionmatrix.tinker.service;

import com.solutionmatrix.tinker.model.entity.Appointment;
import com.solutionmatrix.tinker.model.entity.Customer;
import com.solutionmatrix.tinker.model.request.AppointmentRequestDTO;
import com.solutionmatrix.tinker.repository.AppointmentRepository;
import com.solutionmatrix.tinker.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final CustomerRepository customerRepository;

    private final ClientAvailabilityService clientAvailabilityService;

    public Appointment createAppointment(AppointmentRequestDTO appointmentRequestDTO) {
        try {
            Customer customer = customerRepository.save(appointmentRequestDTO.getCustomer());
            Appointment appointment = Appointment.builder()
                    .date(appointmentRequestDTO.getDate())
                    .clientId(appointmentRequestDTO.getClientId())
                    .timeslotId(appointmentRequestDTO.getTimeslotId())
                    .description(appointmentRequestDTO.getDescription())
                    .statusId(2L)
                    .customer(customer)
                    .build();
            Appointment savedAppointment = appointmentRepository.save(appointment);
            clientAvailabilityService.updateClientAvailability(savedAppointment.getClientId(), savedAppointment.getDate(), savedAppointment.getTimeslotId());
            return savedAppointment;
        } catch(Exception e){
            throw new RuntimeException();
        }
    }
}
