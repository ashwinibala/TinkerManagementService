package com.solutionmatrix.tinker.controller;

import com.solutionmatrix.tinker.constants.ResponseCode;
import com.solutionmatrix.tinker.model.request.AppointmentRequestDTO;
import com.solutionmatrix.tinker.model.response.Response;
import com.solutionmatrix.tinker.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public Response<?> createAppointment(@RequestBody AppointmentRequestDTO appointmentRequestDTO) {

        try {
            return Response.builder()
                    .responseMessage(ResponseCode.SUCCESS.getMessage())
                    .responseCode(ResponseCode.SUCCESS.getCode())
                    .response(appointmentService.createAppointment(appointmentRequestDTO))
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

    @GetMapping(value = "/{clientId}")
    public Response<?> getAppointments(@PathVariable("clientId") Long clientId) {

        try {
            return Response.builder()
                    .responseMessage(ResponseCode.SUCCESS.getMessage())
                    .responseCode(ResponseCode.SUCCESS.getCode())
                    .response(appointmentService.getAppointments(clientId))
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
