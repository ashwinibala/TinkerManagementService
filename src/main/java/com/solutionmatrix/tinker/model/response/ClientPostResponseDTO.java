package com.solutionmatrix.tinker.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientPostResponseDTO {

    private Long id;
    
    private String username;

    private String password;

    private String lastname;

    private String firstname;

    private String phone;

    private String email;

    private String address;

    private Long categoryId;
}
