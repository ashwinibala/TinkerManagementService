package com.solutionmatrix.tinker.model.response;

import com.solutionmatrix.tinker.model.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentsListDTO {
    private Client client;

    private List<AppointmentBookingsDTO> appointments;
}
