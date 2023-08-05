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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/clientavailability")
public class ClientAvailabilityController {

    private final ClientAvailabilityService clientAvailabilityService;

    @GetMapping(value = "/{categoryId}/{date}/{timeslotId}")
    public Response<?> getClient(@PathVariable("categoryId") Long categoryId,
                                 @PathVariable("date") String date,
                                 @PathVariable("timeslotId") Long timeslotId) {

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate availableDate = LocalDate.parse(date, inputFormatter);
        String formattedDate = availableDate.format(outputFormatter);
        try {
            return Response.builder()
                    .responseMessage(ResponseCode.SUCCESS.getMessage())
                    .responseCode(ResponseCode.SUCCESS.getCode())
                    .response(clientAvailabilityService.getClientAvailability(categoryId, formattedDate, timeslotId))
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
