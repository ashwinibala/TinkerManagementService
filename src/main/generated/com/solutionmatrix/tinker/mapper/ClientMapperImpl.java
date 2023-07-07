package com.solutionmatrix.tinker.mapper;

import com.solutionmatrix.tinker.model.entity.Client;
import com.solutionmatrix.tinker.model.request.ClientPostRequestDTO;
import com.solutionmatrix.tinker.model.response.ClientPostResponseDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-07T16:01:06-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Amazon.com Inc.)"
)
public class ClientMapperImpl implements ClientMapper {

    @Override
    public Client clientPostRequestDtoToClient(ClientPostRequestDTO clientPostRequestDTO) {
        if ( clientPostRequestDTO == null ) {
            return null;
        }

        Client.ClientBuilder client = Client.builder();

        client.username( clientPostRequestDTO.getUsername() );
        client.password( clientPostRequestDTO.getPassword() );
        client.lastname( clientPostRequestDTO.getLastname() );
        client.firstname( clientPostRequestDTO.getFirstname() );
        client.phone( clientPostRequestDTO.getPhone() );
        client.email( clientPostRequestDTO.getEmail() );
        client.address( clientPostRequestDTO.getAddress() );
        client.categoryId( clientPostRequestDTO.getCategoryId() );

        return client.build();
    }

    @Override
    public ClientPostResponseDTO clientToClientPostResponseDto(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientPostResponseDTO.ClientPostResponseDTOBuilder clientPostResponseDTO = ClientPostResponseDTO.builder();

        clientPostResponseDTO.id( client.getId() );
        clientPostResponseDTO.username( client.getUsername() );
        clientPostResponseDTO.password( client.getPassword() );
        clientPostResponseDTO.lastname( client.getLastname() );
        clientPostResponseDTO.firstname( client.getFirstname() );
        clientPostResponseDTO.phone( client.getPhone() );
        clientPostResponseDTO.email( client.getEmail() );
        clientPostResponseDTO.address( client.getAddress() );
        clientPostResponseDTO.categoryId( client.getCategoryId() );

        return clientPostResponseDTO.build();
    }
}
