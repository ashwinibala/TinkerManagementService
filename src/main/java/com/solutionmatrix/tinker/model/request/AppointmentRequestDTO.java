package com.solutionmatrix.tinker.model.request;

import com.solutionmatrix.tinker.model.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDTO {
    private Long categoryId;

    private String date;

    private Long timeslotId;

    private Long clientId;

    private String description;

    private Customer customer;

}
