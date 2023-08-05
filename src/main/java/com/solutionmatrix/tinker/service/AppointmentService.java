package com.solutionmatrix.tinker.service;

import com.solutionmatrix.tinker.model.entity.*;
import com.solutionmatrix.tinker.model.request.AppointmentRequestDTO;
import com.solutionmatrix.tinker.model.response.AppointmentBookingsDTO;
import com.solutionmatrix.tinker.model.response.AppointmentResponseDTO;
import com.solutionmatrix.tinker.model.response.AppointmentsListDTO;
import com.solutionmatrix.tinker.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final CustomerRepository customerRepository;

    private final ClientRepository clientRepository;

    private final TimeslotRepository timeslotRepository;

    private CategoryRepository categoryRepository;

    private final ClientAvailabilityService clientAvailabilityService;

    private final EmailServiceImpl emailService;

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

            Timeslot timeslot = timeslotRepository.getReferenceById(appointment.getTimeslotId());

            Category category = categoryRepository.getReferenceById(client.get().getCategoryId());

            String subject = "Tinker Management Service | Appoint Booking confirmation for the service " + client.get().getCategory().getName() + " on " + appointment.getDate();
            String clientMessage = "Hi " + customer.getFirstname() + " " + customer.getLastname() + ", \n" +
                    "Your appointment for the service " + category.getName() + " on " + appointment.getDate() + " at " +
                    timeslot.getStartTime() + " to " + timeslot.getEndTime() + " is Confirmed!!! \n" +
                    "Your Tinker will be " + client.get().getFirstname() + " " + client.get().getLastname() + ".\n" +
                    "Contact details will be " + client.get().getPhone() + " and " + client.get().getEmail() + "\n" +
                    "Regards, \nTinker Management System";
            String tinkerMessage = "Hi " + client.get().getFirstname() + " " + client.get().getLastname() + ", \n" +
                    "You have an appointment for the service " + category.getName() + " on " + appointment.getDate() + " at " +
                    timeslot.getStartTime() + " to " + timeslot.getEndTime() + ". \n" +
                    "Your Customer will be " + customer.getFirstname() + " " + customer.getLastname() + ".\n" +
                    "Contact details will be " + customer.getPhone() + " and " + customer.getEmail() + "\n" +
                    "Address will be " + customer.getAddress() + "\n" +
                    "Regards, \nTinker Management System";

            EmailDetails clientEmailDetails = EmailDetails.builder()
                    .recipient(customer.getEmail())
                    .subject(subject)
                    .msgBody(clientMessage)
                    .build();
            EmailDetails tinkerEmailDetails = EmailDetails.builder()
                    .recipient(client.get().getEmail())
                    .subject(subject)
                    .msgBody(tinkerMessage)
                    .build();

            emailService.sendSimpleMail(clientEmailDetails);
            emailService.sendSimpleMail(tinkerEmailDetails);
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

    public AppointmentsListDTO getAppointments(Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        AppointmentsListDTO appointmentsListDTO = new AppointmentsListDTO();
        List<AppointmentBookingsDTO> appointmentBookingsDTOS = new ArrayList<>();
        if(client.isPresent()) {
            List<Appointment> appointments = appointmentRepository.findByClientId(clientId);
            if (!appointments.isEmpty()) {
                for (Appointment appointment : appointments) {
                    AppointmentBookingsDTO appointmentBookingsDTO = AppointmentBookingsDTO.builder()
                            .date(appointment.getDate())
                            .timeslotid(appointment.getTimeslotId())
                            .description(appointment.getDescription())
                            .customer(appointment.getCustomer())
                            .build();
                    appointmentBookingsDTOS.add(appointmentBookingsDTO);
                }
            }
            appointmentsListDTO.setClient(client.get());
            appointmentsListDTO.setAppointments(appointmentBookingsDTOS);
        }
        return appointmentsListDTO;
    }
}
