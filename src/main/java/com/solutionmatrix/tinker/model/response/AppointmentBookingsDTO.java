package com.solutionmatrix.tinker.model.response;

import com.solutionmatrix.tinker.model.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentBookingsDTO {
    private String date;

    private Long timeslotid;

    private String description;

    private Customer customer;
}
