package com.solutionmatrix.tinker.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDTO {

    private String date;

    private Long timeslotId;

    private Long clientId;

    private String description;

    private String customerEmail;

    private String clientEmail;
}
