package com.solutionmatrix.tinker.service;

import com.solutionmatrix.tinker.model.entity.Appointment;
import com.solutionmatrix.tinker.model.entity.Client;
import com.solutionmatrix.tinker.model.entity.Customer;
import com.solutionmatrix.tinker.model.request.AppointmentRequestDTO;
import com.solutionmatrix.tinker.model.response.AppointmentResponseDTO;
import com.solutionmatrix.tinker.repository.AppointmentRepository;
import com.solutionmatrix.tinker.repository.ClientRepository;
import com.solutionmatrix.tinker.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final CustomerRepository customerRepository;

    private final ClientRepository clientRepository;

    private final ClientAvailabilityService clientAvailabilityService;

    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO appointmentRequestDTO) {
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
            Optional<Client> client = clientRepository.findById(savedAppointment.getClientId());
            clientAvailabilityService.updateClientAvailability(savedAppointment.getClientId(), savedAppointment.getDate(), savedAppointment.getTimeslotId());
            return AppointmentResponseDTO.builder()
                    .date(savedAppointment.getDate())
                    .clientId(savedAppointment.getClientId())
                    .timeslotId(savedAppointment.getTimeslotId())
                    .description(savedAppointment.getDescription())
                    .customerEmail(customer.getEmail())
                    .clientEmail(client.get().getEmail())
                    .build();
        } catch(Exception e){
            throw new RuntimeException();
        }
    }
}
