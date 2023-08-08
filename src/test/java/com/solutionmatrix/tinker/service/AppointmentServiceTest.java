package com.solutionmatrix.tinker.service;

import com.solutionmatrix.tinker.model.entity.*;
import com.solutionmatrix.tinker.model.request.AppointmentRequestDTO;
import com.solutionmatrix.tinker.model.response.AppointmentBookingsDTO;
import com.solutionmatrix.tinker.model.response.AppointmentResponseDTO;
import com.solutionmatrix.tinker.model.response.AppointmentsListDTO;
import com.solutionmatrix.tinker.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.powermock.api.mockito.PowerMockito.*;

class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;
    @MockBean
    private AppointmentRepository appointmentRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private TimeslotRepository timeslotRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ClientAvailabilityService clientAvailabilityService;

    @MockBean
    private EmailServiceImpl emailService;



    @BeforeEach
    void setUp() {

        appointmentRepository  = mock(AppointmentRepository .class);
        customerRepository = mock(CustomerRepository.class);
        clientRepository = mock(ClientRepository.class);
        timeslotRepository  = mock(TimeslotRepository .class);
        categoryRepository = mock(CategoryRepository.class);
        clientAvailabilityService  = mock(ClientAvailabilityService .class);
        emailService = mock(EmailServiceImpl.class);

        appointmentService= new AppointmentService( appointmentRepository,customerRepository,clientRepository,timeslotRepository,
                categoryRepository ,clientAvailabilityService ,emailService);

    }

    @Test
    void createAppointmentTest() {


    }
    @Test
    void getAppointments() {
        Long clientId = 1L;

        Client client = new Client();
        client.setId(clientId);


        List<Appointment> appointments = new ArrayList<>();
        Appointment appointment = new Appointment();
        appointment.setDate(String.valueOf(LocalDate.now()));
        appointment.setTimeslotId(1L);
        appointment.setDescription("Test appointment");
        appointment.setCustomer(null); // You need to set a valid customer here
        appointments.add(appointment);


        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));


        when(appointmentRepository.findByClientId(clientId)).thenReturn(appointments);


        AppointmentsListDTO result = appointmentService.getAppointments(clientId);
        assertNotNull(result);
        assertEquals(client, result.getClient());

        List<AppointmentBookingsDTO> appointmentBookingsDTOS = result.getAppointments();
        assertNotNull(appointmentBookingsDTOS);
        assertEquals(1, appointmentBookingsDTOS.size());

        AppointmentBookingsDTO appointmentBookingsDTO = appointmentBookingsDTOS.get(0);
        assertEquals(appointment.getDate(), appointmentBookingsDTO.getDate());
        assertEquals(appointment.getTimeslotId(), appointmentBookingsDTO.getTimeslotid());
        assertEquals(appointment.getDescription(), appointmentBookingsDTO.getDescription());
        assertEquals(appointment.getCustomer(), appointmentBookingsDTO.getCustomer());
    }
}