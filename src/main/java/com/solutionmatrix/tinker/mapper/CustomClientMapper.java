package com.solutionmatrix.tinker.mapper;

import com.solutionmatrix.tinker.model.entity.Client;
import com.solutionmatrix.tinker.model.request.ClientPostRequestDTO;
import com.solutionmatrix.tinker.model.response.ClientPostResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomClientMapper implements ClientMapper {
    @Override
    public Client clientPostRequestDtoToClient(ClientPostRequestDTO clientPostRequestDTO) {
        Client client = new Client();
        client.setUsername(clientPostRequestDTO.getUsername());
        client.setPassword(clientPostRequestDTO.getPassword());
        client.setLastname(clientPostRequestDTO.getLastname());
        client.setFirstname(clientPostRequestDTO.getFirstname());
        client.setPhone(clientPostRequestDTO.getPhone());
        client.setEmail(clientPostRequestDTO.getEmail());
        client.setAddress(clientPostRequestDTO.getAddress());
        client.setCategoryId(clientPostRequestDTO.getCategoryId());
        return client;
    }

    @Override
    public ClientPostResponseDTO clientToClientPostResponseDto(Client client) {
        ClientPostResponseDTO responseDTO = new ClientPostResponseDTO();
        responseDTO.setId(client.getId());
        responseDTO.setUsername(client.getUsername());
        responseDTO.setPassword(client.getPassword());
        responseDTO.setFirstname(client.getFirstname());
        responseDTO.setLastname(client.getLastname());
        responseDTO.setPhone(client.getPhone());
        responseDTO.setEmail(client.getEmail());
        responseDTO.setAddress(client.getAddress());
        responseDTO.setCategoryId(client.getCategoryId());
        return responseDTO;
    }
}
