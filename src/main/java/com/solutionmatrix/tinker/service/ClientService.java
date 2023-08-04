package com.solutionmatrix.tinker.service;

import com.solutionmatrix.tinker.mapper.ClientMapper;
import com.solutionmatrix.tinker.model.entity.Client;
import com.solutionmatrix.tinker.model.request.ClientPostRequestDTO;
import com.solutionmatrix.tinker.model.request.LoginDTO;
import com.solutionmatrix.tinker.model.response.ClientPostResponseDTO;
import com.solutionmatrix.tinker.repository.ClientRepository;
import com.solutionmatrix.tinker.validator.ClientValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ClientService {

    private ClientRepository clientRepository;

    private ClientValidator clientValidator;

    private ClientMapper clientMapper;

    private ClientAvailabilityService clientAvailabilityService;

    public ClientPostResponseDTO createClient(ClientPostRequestDTO clientPostRequestDTO) {

        try {
            Client clientRequest = clientMapper.clientPostRequestDtoToClient(clientPostRequestDTO);
            try {
                clientValidator.validateClientPostRequest(clientRequest);
            } catch(RuntimeException e) {
                throw new RuntimeException(e.getMessage());
            }
            try {
                Client client = clientRepository.save(clientRequest);
                clientAvailabilityService.addClientAvailability(client.getId());
                return clientMapper.clientToClientPostResponseDto(client);
            } catch(Exception e) {
                throw new RuntimeException("Database Save Error");
            }
        } catch(Exception e){
            throw new RuntimeException();
        }
    }

    public int loginCheck(LoginDTO loginDTO) {

        try {
            Optional<Client> client = clientRepository.findByUsername(loginDTO.getUsername());
            if(client.isPresent()) {
                if (client.get().getPassword().equals(loginDTO.getPassword())) {
                    return 200;
                } else {
                    return 409;
                }
            } else {
                return 404;
            }
        } catch(Exception e){
            throw new RuntimeException();
        }
    }

    public Optional<Client> getClient(Long clientId) {
        return clientRepository.findById(clientId);
    }

    public Client updateClient(Client client) {
        Optional<Client> existingClient = getClient(client.getId());
        existingClient = Optional.of(client);
        return existingClient.map(value -> clientRepository.save(value)).orElse(null);
    }
}
