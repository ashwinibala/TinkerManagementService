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

        AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO();

        Customer mockCustomer = new Customer();
        Appointment mockSavedAppointment = new Appointment();
        Client client = Client.builder()
                .id(22L)
                .categoryId(22L)
                .lastname("Bala")
                .firstname("Ashwini")
                .address("Villa 345, Pacifica Aurum")
                .email("ashwinibalaganesun@gmail.com")
                .build();
        Timeslot mockTimeslot = new Timeslot();
        Category mockCategory = new Category();


        when(customerRepository.save(any(Customer.class))).thenReturn(mockCustomer);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(mockSavedAppointment);
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(timeslotRepository.getReferenceById(anyLong())).thenReturn(mockTimeslot);
        when(categoryRepository.getReferenceById(anyLong())).thenReturn(mockCategory);


        AppointmentResponseDTO responseDTO= appointmentService.createAppointment(appointmentRequestDTO);


        verify(appointmentRepository, times(1)).save(any(Appointment.class));
        verify(emailService, times(2)).sendSimpleMail(any(EmailDetails.class));


        assertEquals(mockCustomer.getEmail(), responseDTO.getCustomerEmail());
        assertEquals(client.getEmail(), responseDTO.getClientEmail());
        assertEquals(mockSavedAppointment.getDate(), responseDTO.getDate());
        assertEquals(mockSavedAppointment.getClientId(), responseDTO.getClientId());
        assertEquals(mockSavedAppointment.getTimeslotId(), responseDTO.getTimeslotId());
        assertEquals(mockSavedAppointment.getDescription(), responseDTO.getDescription());


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