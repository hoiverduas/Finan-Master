package com.hd.finanMaster.dto;

import lombok.Data;

import java.time.LocalDate;


@Data
public class ClientRequestDTO {

    private String identificationType;
    private String identificationNumber;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;

}
