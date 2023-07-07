package com.solutionmatrix.tinker.controller;

import com.solutionmatrix.tinker.constants.ResponseCode;
import com.solutionmatrix.tinker.model.entity.Client;
import com.solutionmatrix.tinker.model.request.ClientPostRequestDTO;
import com.solutionmatrix.tinker.model.response.Response;
import com.solutionmatrix.tinker.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public Response<?> createClient(@RequestBody ClientPostRequestDTO clientPostRequestDTO) {

        try {
            return Response.builder()
                    .responseMessage(ResponseCode.SUCCESS.getMessage())
                    .responseCode(ResponseCode.SUCCESS.getCode())
                    .response(clientService.createClient(clientPostRequestDTO))
                    .build();
        } catch (RuntimeException e) {
            return Response.builder()
                    .responseMessage(e.getMessage())
                    .responseCode(ResponseCode.CONFLICT.getCode())
                    .responseMessage(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .responseMessage(e.getMessage())
                    .responseCode(ResponseCode.NOTACCEPTABLE.getCode())
                    .responseMessage(ResponseCode.NOTACCEPTABLE.getMessage())
                    .build();
        }
    }

    @GetMapping(value = "/{id}")
    public Optional<Client> getClient(@PathVariable Long id) {
        return clientService.getClient(id);
    }

    @PatchMapping
    public Client updateClient(Client client) {
        return clientService.updateClient(client);
    }
}
