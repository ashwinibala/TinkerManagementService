package com.solutionmatrix.tinker.service;

import com.solutionmatrix.tinker.mapper.ClientMapper;
import com.solutionmatrix.tinker.model.entity.Client;
import com.solutionmatrix.tinker.model.request.ClientPostRequestDTO;
import com.solutionmatrix.tinker.model.response.ClientPostResponseDTO;
import com.solutionmatrix.tinker.repository.ClientRepository;
import com.solutionmatrix.tinker.validator.ClientValidator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.springframework.test.web.client.ExpectedCount.times;

class ClientServiceTest {

    private ClientService clientService;


    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private ClientMapper clientMapper;

    @MockBean
    private ClientValidator clientValidator;

    @MockBean
    private ClientAvailabilityService clientAvailabilityService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        clientValidator = mock(ClientValidator.class);
        clientMapper = mock(ClientMapper.class);
        clientAvailabilityService = mock(ClientAvailabilityService.class);

        clientService = new ClientService(clientRepository, clientValidator, clientMapper, clientAvailabilityService);
    }

    @Test
    void createClientTest() {

        ClientPostRequestDTO requestDTO = new ClientPostRequestDTO();
        Client clientRequest = new Client();
        Client clientResponse = new Client();
        ClientPostResponseDTO expectedResponse = new ClientPostResponseDTO();


        when(clientMapper.clientPostRequestDtoToClient(requestDTO)).thenReturn(clientRequest);
        doNothing().when(clientValidator).validateClientPostRequest(clientRequest);
        when(clientRepository.save(clientRequest)).thenReturn(clientResponse);
        doNothing().when(clientAvailabilityService).addClientAvailability(clientResponse.getId());
        when(clientMapper.clientToClientPostResponseDto(clientResponse)).thenReturn(expectedResponse);


        ClientPostResponseDTO actualResponse = clientService.createClient(requestDTO);

        assertEquals(expectedResponse, actualResponse);
    }
    @Test
    public void testCreateClient_ValidationFailure() {

        ClientPostRequestDTO requestDTO = new ClientPostRequestDTO(/* initialize request DTO */);
        Client clientRequest = new Client(/* initialize client entity */);


        when(clientMapper.clientPostRequestDtoToClient(requestDTO)).thenReturn(clientRequest);
        doThrow(new RuntimeException("Validation error")).when(clientValidator).validateClientPostRequest(clientRequest);


        assertThrows(RuntimeException.class, () -> clientService.createClient(requestDTO));

    }

    @Test
    public void testCreateClient_DatabaseSaveError() {

        ClientPostRequestDTO requestDTO = new ClientPostRequestDTO(/* initialize request DTO */);
        Client clientRequest = new Client(/* initialize client entity */);


        when(clientMapper.clientPostRequestDtoToClient(requestDTO)).thenReturn(clientRequest);
        doNothing().when(clientValidator).validateClientPostRequest(clientRequest);
        when(clientRepository.save(clientRequest)).thenThrow(new RuntimeException("Database error"));


        assertThrows(RuntimeException.class, () -> clientService.createClient(requestDTO));
    }
    @Test
    void loginCheckTest() {

        Client client = new Client();
        client.setPassword("correct_password");
        when(clientRepository.findByUsername("username")).thenReturn(Optional.of(client));

        int result = clientService.loginCheck("username", "correct_password");
        int result2 = clientService.loginCheck("username", "wrong_password");
        int result3 = clientService.loginCheck("nonexistent_username", "any_password");

        assertEquals(200, result);
        assertEquals(409, result2);
        assertEquals(404, result3);
    }

    @Test
    void getClient() {


    }

    @Test
    void updateClientTest() {

        Client existingClient = new Client();
        existingClient.setId(null);
        Client updatedClient = new Client();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(updatedClient)).thenReturn(updatedClient);

        Client result = clientService.updateClient(updatedClient);


        assertNotNull(result);
        assertEquals(existingClient.getId(), result.getId());
    }
}