package com.solutionmatrix.tinker.mapper;

import com.solutionmatrix.tinker.model.entity.Client;
import com.solutionmatrix.tinker.model.request.ClientPostRequestDTO;
import com.solutionmatrix.tinker.model.response.ClientPostResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ClientMapper {

    ClientMapper Instance = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    Client clientPostRequestDtoToClient(ClientPostRequestDTO clientPostRequestDTO);

    ClientPostResponseDTO clientToClientPostResponseDto(Client client);
}
