package com.solutionmatrix.tinker.controller;

import com.solutionmatrix.tinker.constants.ResponseCode;
import com.solutionmatrix.tinker.model.response.Response;
import com.solutionmatrix.tinker.service.ClientAvailabilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/clientavailability")
public class ClientAvailabilityController {

    private final ClientAvailabilityService clientAvailabilityService;

    @GetMapping(value = "/{categoryId}/{date}/{timeslotId}")
    public Response<?> getClient(@PathVariable Long categoryId, String date, Long timeslotId) {
        System.out.println(categoryId);
        System.out.println(date);
        System.out.println(timeslotId);
        try {
            return Response.builder()
                    .responseMessage(ResponseCode.SUCCESS.getMessage())
                    .responseCode(ResponseCode.SUCCESS.getCode())
                    .response(clientAvailabilityService.getClientAvailability(categoryId, "2023-07-21", 2L))
                    .build();
        } catch (RuntimeException e) {
            return Response.builder()
                    .responseMessage(e.getMessage())
                    .responseCode(ResponseCode.CONFLICT.getCode())
                    .response("")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .response("")
                    .responseCode(ResponseCode.NOTACCEPTABLE.getCode())
                    .responseMessage(ResponseCode.NOTACCEPTABLE.getMessage())
                    .build();
        }
    }
}
