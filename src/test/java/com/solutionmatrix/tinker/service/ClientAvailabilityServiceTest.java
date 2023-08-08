package com.solutionmatrix.tinker.service;

import com.solutionmatrix.tinker.mapper.ClientMapper;
import com.solutionmatrix.tinker.model.entity.Client;
import com.solutionmatrix.tinker.model.entity.ClientAvailability;
import com.solutionmatrix.tinker.repository.ClientAvailabilityRepository;
import com.solutionmatrix.tinker.repository.ClientRepository;
import com.solutionmatrix.tinker.validator.ClientValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;

class ClientAvailabilityServiceTest {

@InjectMocks
    private ClientAvailabilityService clientAvailabilityService;

    @MockBean
    private ClientAvailabilityRepository clientAvailabilityRepository;

    @MockBean
    private  ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        clientAvailabilityRepository = mock(ClientAvailabilityRepository.class);

        clientAvailabilityService= new ClientAvailabilityService(clientAvailabilityRepository,clientRepository);
    }

    @Test
    void getClientAvailabilityTest() {

        Long categoryId = 1L;
        String date = "2023-08-10";
        Long timeslotId = 2L;

        Client client1 = new Client();
        client1.setId(1L);

        Client client2 = new Client();
        client2.setId(2L);

        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);

        ClientAvailability availability1 = new ClientAvailability();
        availability1.setClientId(1L);
        availability1.setDate(date);
        availability1.setTimeslotId(timeslotId);
        availability1.setStatusId(1L);

        when(clientRepository.findByCategoryId(categoryId)).thenReturn(clients);
        when(clientAvailabilityRepository.findByClientIdAndDateAndTimeslotIdAndStatusId(1L, date, timeslotId, 1L))
                .thenReturn(Optional.of(availability1));
        when(clientAvailabilityRepository.findByClientIdAndDateAndTimeslotIdAndStatusId(2L, date, timeslotId, 1L))
                .thenReturn(Optional.empty());

        List<Client> availableClients = clientAvailabilityService.getClientAvailability(categoryId, date, timeslotId);


        assertEquals(1, availableClients.size());
        assertEquals(1L, availableClients.get(0).getId());
    }

    @Test
    void addClientAvailabilityTest() {
        Long clientId = 1L;

        when(clientAvailabilityRepository.save(any())).thenReturn(null);

        clientAvailabilityService.addClientAvailability(clientId);

        org.mockito.Mockito.verify(clientAvailabilityRepository, Mockito.times(35)).save(any());
    }

    @Test
    void updateClientAvailabilityTest() {

        Long clientId = 1L;
        String date = "2023-08-05";
        Long timeslotId = 1L;
        ClientAvailability availability = new ClientAvailability(/* initialize client availability entity */);
        when(clientAvailabilityRepository.findByClientIdAndDateAndTimeslotIdAndStatusId(
                anyLong(), anyString(), anyLong(), anyLong()))
                .thenReturn(Optional.of(availability));
        when(clientAvailabilityRepository.save(any(ClientAvailability.class))).thenReturn(availability);


        clientAvailabilityService.updateClientAvailability(clientId, date, timeslotId);

        assertEquals(2L, availability.getStatusId());
    }
    }
